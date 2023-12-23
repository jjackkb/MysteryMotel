package main;

import java.util.List;

class Commands extends MysteryMotel {
	static boolean admin = false;

	public static void processCommand(String[] command) {
		for (int i = 0; i < 50; ++i) System.out.println();

		switch (command[0]) {
			case "north":
			case "east":
			case "south":
			case "west":
				move(command[0]);
				break;

			case "help":
				setCommandOutput("Help - Commands\n\n* Movement\nnorth - Move north\nsouth - Move south\neast  - Move east\nwest  - Move west\n\n* General\nmap - View map\nsearch - Search room for items\ninventory - See your inventory\nget <item> - Add item to inventory\nuse <item> - Use item in inventory");
				System.out.println(getCommandOutput());
				break;

			case "room":
				setCommandOutput("You are in " + currentRoom.getName());
				break;

			case "search":
				search();
				break;

			case "get":
				getItem(command[1]);
				break;

			case "use":
				useItem(command[1]);
				break;

			case "map":
				Map.displayMap();
				break;

			case "inventory":
				setCommandOutput("Your inventory:\n" + getInventory());
				break;

			case "admin", "a":
				admin = true;
				while (admin) {
					String c = getUserInput();
					adminCommand(c);

				}
				break;
			default:
				setCommandOutput("Invalid command. Try again.");
		}
	}

	private static void adminCommand(String command) {
		switch (command) {
			case "quit", "q":
				admin = false;
				break;
			case "open r":
				move("north");
				move("south");
				move("west");
				Map.displayMap();
				break;

		}
	}

	protected static int id(int x, int y) {
		return (y * 3) + x;
	}

	protected static void setCommandOutput(String CommandOut) {
		MysteryMotel.commandOutput = CommandOut;
	}

	protected static String getCommandOutput() {
		String co = MysteryMotel.commandOutput;
		setCommandOutput("");
		return co;
	}

	protected static void move(String direction) {
		Room room = currentRoom;
		int roomID = room.getID();

		switch (direction) {
			case "north":
				try {
					enterRoom(room.getID() + 3);
				} catch (Exception e) {
					setCommandOutput("Room not found!");
					currentRoom = room;
				}
				break;
			case "south":
				try {
					enterRoom(room.getID() - 3);
				} catch (Exception e) {
					setCommandOutput("Room not found!");
					currentRoom = room;
				}
				break;
			case "east":
				try {
					enterRoom(room.getID() + 1);
				} catch (Exception e) {
					setCommandOutput("Room not found!");
					currentRoom = room;
				}
				break;
			case "west":
				try {
					enterRoom(room.getID() - 1);
				} catch (Exception e) {
					setCommandOutput("Room not found!");
					currentRoom = room;
				}
				break;
		}
	}

	protected static void enterRoom(int rNum) {
		Room room = Room.getRoom(rNum);
		assert room != null;
		if (room.getLocked()) {
			if (currentRoom == null || Character.getInventory().contains(Item.getItem("key"))) {
				useItem("Key");
				room.setLocked();
			}
			else {
				setCommandOutput("Room is locked! Find the key.");
				return;
			}
		}
		List<Map> mapRooms = Map.getMapRooms();
		currentRoom = room;
		int num = room.getID();
		int x = room.getX();
		int y = room.getY();

		for (Room r : Room.getRooms()) {
			if (room == r) {
				for (Map m : mapRooms) {
					if (m != null) {
						if (m.getObjRoom() == r) {
							setCommandOutput(String.format("You entered %s", r.getName()) + ".");
							entered = true;
							return;
						}
					}
				}
				dialogueNewRoom(r);
				System.out.println(Commands.getCommandOutput());

				new Map(num, currentRoom, x, y);
				setCommandOutput(String.format("You entered %s", r.getName()) + ".");
				entered = true;
				return;
			}
		}

		dialogueNewRoom(room);
		System.out.println(Commands.getCommandOutput());

		new Map(num, currentRoom, x, y);
		setCommandOutput(String.format("You entered %s", room.getName()) + ".");
		entered = true;
	}
	public static void dialogueNewRoom(Room room) {
		String name = room.getCharacter(room).getName();
		StringBuilder output = new StringBuilder();
		int[] dialogue = Dialogue.getSequence(name.toLowerCase(), 0);
		assert dialogue != null;
		for (int x = dialogue[0]; x <= dialogue[dialogue.length-1]; x++) {
			String quote = Dialogue.getQuote(name, x);
			output.append(quote).append("\n");
		}
		setCommandOutput(output.toString());
	}

	protected static void search() {
		setCommandOutput("You searched the room and found: \n" + getItems());
		MysteryMotel.currentRoom.setSearched();
	}
	protected static void useItem(String i) {
		Item item = Character.getInventory().getFirst();
		for (Item a : Character.getInventory()) {
			if (a.getName().equals(i)) {
				item = a;
			}
		}

		if (!Character.getInventory().isEmpty()) {
			Character.removeInventory(item);
			setCommandOutput("You used the " + item.name + ".");
		} else {
			setCommandOutput("Your inventory is empty.");
		}
	}
	protected static void getItem(String name) {
		if (!currentRoom.getItems().isEmpty()) {
			for (Item i : currentRoom.getItems()) {
				if (i.getName().toLowerCase().equals(name)) {
					Character.addInventory(i);
					currentRoom.removeItem(i);
					setCommandOutput("You picked up a " + i.getName());
					return;
				}
			}
			setCommandOutput(name + " not found");
		} else {
			setCommandOutput("No items to pick up in this room");
		}
	}
	public static String getItems() {
		StringBuilder items = new StringBuilder();
		for (Item i : MysteryMotel.getCurrentRoom().getItems()) {
			String description = String.format(" (%s)", i.getDescription());
			items.append(i.getName()).append(description).append(", ");
		}
		if (!items.isEmpty()) {
			items.delete(items.length() - 2, items.length() - 1);
		}
		else {
			items.append("Room is Empty");
		}
		return items.toString();
	}
	public static String getInventory() {
		StringBuilder items = new StringBuilder();
		for (Item i : Character.getInventory()) {
			items.append(i.getName()).append(", ");
		}
		if (!items.isEmpty()) {
			items.delete(items.length() - 2, items.length() - 1);
		}
		else {
			items.append("empty");
		}
		return items.toString();
	}
}