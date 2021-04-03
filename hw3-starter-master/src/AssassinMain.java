// EGR221 Homework 3 Assasin  
// Instructor-provided testing program.

import java.io.*;
import java.util.*;

public class AssassinMain {
    public static void main(String[] args) throws FileNotFoundException {
        /*
        List<String> names = new ArrayList<String>();
        names.add("Billy");
        names.add("Bob");
        names.add("Joe");
        names.add("Larry");
        names.add("Moe");
        names.add("Curly");
        AssassinManager chain = new AssassinManager(names);
        chain.printKillingRing();
        chain.kill("Billy");
        chain.printGraveyard();
        chain.printKillingRing();

        /*
        List<String> names2 = new ArrayList<String>();
        names2.add("Zac");
        AssassinManager chain2 = new AssassinManager(names2);
        System.out.println(chain2.isGameOver());
        System.out.println(chain2.winner());

         */
        /*
        List<String> alive = new ArrayList<String>();
        List<String> dead = new ArrayList<String>();

        alive.add("Billy");
        alive.add("Bob");
        alive.add("Joe");
        dead.add("Curly");
        dead.add("Moe");
        dead.add("Larry");

        AssassinManager testing = new AssassinManager(alive, dead);
        System.out.println(testing.graveyardContains("Squid"));
        testing.printGraveyard();
         */
        // prompt for file name

        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the EGR227 Assassin Manager");
        System.out.println();
        System.out.print("What name file do you want to use this time? ");
        String fileName = console.nextLine();

        // read names into a list, using a Set to avoid duplicates
        Scanner input = new Scanner(new File(fileName));
        Set<String> names = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        List<String> names2 = new ArrayList<String>();
        while (input.hasNextLine()) {
            String name = input.nextLine().trim();
            if (name.length() > 0 && !names.contains(name)) {
                names.add(name);
                names2.add(name);
            }
        }

        // Shuffle if desired
        if (yesTo(console, "Do you want the names shuffled?")) {
            Collections.shuffle(names2);
        }
        // Make an immutable version and use it to build an AssassinManager
        List<String> names3 = Collections.unmodifiableList(names2);
        AssassinManager manager = new AssassinManager(names3);
        System.out.println();

        // Prompt the user for victims until the game is over
        while (!manager.isGameOver())
            oneKill(console, manager);

        // Report who won
        System.out.println("Game was won by " + manager.winner());
        System.out.println("Final graveyard is as follows:");
        manager.printGraveyard();
    }

    // Handles the details of recording one victim.  Shows the current kill
    // ring and graveyard to the user, prompts for a name and records the
    // kill if the name is legal.
    public static void oneKill(Scanner console, AssassinManager manager) {
        System.out.println("Current kill ring:");
        manager.printKillRing();
        System.out.println("Current graveyard:");
        manager.printGraveyard();
        System.out.println();
        System.out.print("next victim? ");
        String name = console.nextLine().trim();
        if (manager.graveyardContains(name)) {
            System.out.println(name + " is already dead.");
        } else if (!manager.killRingContains(name)) {
            System.out.println("Unknown person.");
        } else {
            manager.kill(name);
        }
        System.out.println();
    }

    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public static boolean yesTo(Scanner console, String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");

    }
}
