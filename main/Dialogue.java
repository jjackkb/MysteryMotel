package main;

public class Dialogue {
    enum Dialogues {
        WINSTON(new String[]{
                "Hello there, my names Winston I'm the doorman here at the Mystery Motel.",
                "Head north toward the motel lobby to meet the owner.",
                "Type help at anytime to see the full list of commands."
        }),
        LINUS(new String[]{
                "Hello detective, My name's Linus; I'm the owner of this here motel.",
                "I don't doubt you're here to investigate the murder that took place last night?",
                "",
                "Awful thing that has happened isn't it? Ms. Alice was a wonderful person.",
                "See, this here motel is so far from the nearest town you must find the murderer before they kill again!",
                "I trust you will find who ever is responsible for this."
        }),
        VICTOR(new String[]{
                "Hello detective, is there something that I can help you with?",
                "If you are here to question me about that murder then don't  bother.",
                "I have done nothing wrong and have no reason to hide anything.",
                "",
                "In fact, you should go bother that sketchy skater kid in room #103 down past the entrance",
                ""
        });

        private final String[] dialogue;

        Dialogues(String[] dialogue) {
            this.dialogue = dialogue;
        }
    }

    enum DialogueSequence {
        WINSTON(new int[][]{{0,1,2}}),
        LINUS(new int[][]{{0,1,2,3,4,5}}),
        VICTOR(new int[][]{{0,1,2,3,4,5}});

        private final int[][] dialogueSequence;

        DialogueSequence(int[][] dialogueSequence) {
            this.dialogueSequence = dialogueSequence;
        }
    }

    public static String getQuote(String name, int num) {
        StringBuilder quote = new StringBuilder();
        for(Dialogues d : Dialogues.values()) {
            if (d.name().equalsIgnoreCase(name)) {
                quote = new StringBuilder(d.dialogue[num]);
                int max = 58-(quote.length()/2);
                for (int x = 1; x < max; x++) {
                    quote.insert(0, ' ');
                }
            }
        }
        return quote.toString();
    }

    public static int[] getSequence(String name, int num) {
        for(DialogueSequence d : DialogueSequence.values()) {
            if (d.name().equalsIgnoreCase(name)) {
                return d.dialogueSequence[num];
            }
        }
        return null;
    }
}