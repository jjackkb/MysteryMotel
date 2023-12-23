//JACK AND CAM
package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MysteryMotel {
    protected static final Scanner scanner = new Scanner(System.in);
    protected static final boolean escaped = false;
    protected static boolean diedInvestigating = false;
    protected static boolean accused = false;
    protected static boolean entered = false;
    protected static Room currentRoom;
    protected static int yIndex;
    public static String commandOutput = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        intro();
        initializeGame();

        while (true) {
            printRoom();
            String command = getUserInput();
            String[] cmdArray = command.split(" ");
            Commands.processCommand(cmdArray);

            if (gameOver()) {
                System.out.println("Game Over");
                printEndMessage();
                break;
            }
        }
    }

    public static void initializeGame() {
        new Map();
        new Room(1, "entrance", new Item("Key", "An old rusty key"), false, 3, 0);
        new Room(2, "motel lobby", new Item("Bloody Knife", "A bloody kitchen knife"), true,  3, 1);
        new Room(3, "Room #23", new Item("Book", "A book with strange symbols"), false, 2, 0);
        new Room(4, "Room #103", new Item("Computer", "A Work Laptop"), false, 1, 0);

        new Character("Winston", 1);
        new Character("Linus", 2);
        new Character("Victor", 3);

        Commands.enterRoom(3);
    }

    private static void intro() throws IOException, InterruptedException {
        asciiMessage();

    	Thread.sleep(1125);
    	for(int y = 0; y < 20; y++) {
    		System.out.println();
    		Thread.sleep(100);
    	}
    }

    private static void asciiMessage() throws IOException {
        Path fileName = Path.of(System.getProperty("user.dir") + "/main/.asciiMessage.txt");

        String str = Files.readString(fileName);

        System.out.println(str);
    }

    private static void printRoom() {
        String CommandOut = Commands.getCommandOutput();

        if (!CommandOut.isEmpty())
            CommandOut = "* " + CommandOut;

        System.out.println(CommandOut);
    }

    public static String getUserInput() {
        System.out.print(": ");
        return scanner.nextLine().toLowerCase();
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }

    private static boolean gameOver() {
        return accused || diedInvestigating || escaped;
    }

    protected static void printEndMessage() {
    }
}