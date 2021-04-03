import java.awt.*;
import java.util.*;
import java.util.List;

public class AssassinManager {
    // YOUR CODE GOES HERE
    private AssassinNode firstKiller;
    private AssassinNode firstDead;
    public AssassinManager(List<String> names) {
        firstKiller = new AssassinNode(names.get(0), getNextKiller(names, 1));
    }

    /*
    //For testing purposes
    public AssassinManager(List<String> alive, List<String> dead) {
        firstKiller = new AssassinNode(alive.get(0), getNextKiller(alive, 1));
        firstDead = new AssassinNode(dead.get(0), getNextKiller(dead, 1));
    }
     */


    private AssassinNode getNextKiller(List<String> names, int index) {
        if (names.size() == index) {
            return null;
        }

        return new AssassinNode(names.get(index), getNextKiller(names, index + 1));

    }

    public void printKillRing() {
        System.out.println(firstKiller.name + " is stalking " + firstKiller.next.name + "\n" + printKillRing(firstKiller.next));
    }

    private String printKillRing(AssassinNode next) {
        if (next == null) {
            return "";
        }

        if (next.next != null) {
            return next.name + " is stalking " + next.next.name + "\n" + printKillRing(next.next);
        }
        else {
            return next.name + " is stalking " + firstKiller.name;
        }
    }

    public void printGraveyard() {
        if (firstDead == null) {
            System.out.println();
        }
        else
            System.out.println(firstDead.name + " was killed by " + firstDead.killer + "\n" + printGraveyard(firstDead.next));
    }

    private String printGraveyard(AssassinNode next) {
        if (next == null) {
            return "";
        }

        return next.name + " was killed by " + next.killer + "\n" + printGraveyard(next.next);
    }

    public boolean killRingContains(String name) {
        if (firstKiller.name.equalsIgnoreCase(name)) {
            return true;
        }

        else {
            return containsNext(name, firstKiller.next);
        }
    }

    private boolean containsNext(String name, AssassinNode next) {
        if (next == null) {
            return false;
        }
        else if (next.name.equalsIgnoreCase(name)) {
            return true;
        }
        else {
            return containsNext(name, next.next);
        }
    }

    public boolean graveyardContains(String name) {
        if(firstDead == null) {
            return false;
        }

        if (firstDead.name.equalsIgnoreCase(name)) {
            return true;
        }
        else {
            return containsNext(name, firstDead.next);
        }
    }

    public boolean isGameOver() {
        return firstKiller.next == null;
    }

    public String winner() {
        if (!isGameOver()) {
            return null;
        }
        else {
            return firstKiller.name;
        }
    }

    public void kill(String name) {
        if (isGameOver()) {
            throw new IllegalStateException("The game is over!");
        }
        else if(!killRingContains(name)) {
            throw new IllegalArgumentException("That name cannot be found!");
        }

        if (firstKiller.name.equalsIgnoreCase(name)) {
            firstKiller.killer = findLastKiller().name;
            addGraveyard(firstKiller);
            firstKiller = firstKiller.next;
        }
        else {
            AssassinNode killer = findKiller(name);
            killer.next.killer = killer.name;
            AssassinNode newNext = killer.next.next;
            killer.next.next = null;
            addGraveyard(killer.next);
            killer.next = newNext;
        }
    }

    private AssassinNode findKiller(String name) {
        if (firstKiller.next.name.equalsIgnoreCase(name)) {
            return firstKiller;
        }
        else {
            return findKiller(name, firstKiller.next);
        }
    }

    private AssassinNode findKiller(String name, AssassinNode next) {
        if (next.next.name.equalsIgnoreCase(name)) {
            return next;
        }
        else {
            return findKiller(name, next.next);
        }
    }

    private void addGraveyard(AssassinNode dead) {
        if (firstDead == null) {
            firstDead = new AssassinNode(dead.name, null);
            firstDead.killer = dead.killer;
        }
        else if(firstDead.next == null) {
            firstDead.next = dead;
        }

        else {
            addGraveyard(dead, firstDead.next);
        }
    }

    private void addGraveyard(AssassinNode dead, AssassinNode next) {
        if (next.next == null) {
            next.next = dead;
        }
        else {
            addGraveyard(dead, next.next);
        }
    }


    private AssassinNode findLastKiller() {
        if (firstKiller.next == null)
            return firstKiller;
        else
            return findLastKiller(firstKiller.next);

    }

    private AssassinNode findLastKiller(AssassinNode next) {
        if (next.next == null)
            return next;
        else
            return findLastKiller(next.next);
    }


    //////// DO NOT MODIFY AssassinNode.  You will lose points if you do. ////////
    /**
     * Each AssassinNode object represents a single node in a linked list
     * for a game of Assassin.
     */
    private static class AssassinNode {
        public final String name;  // this person's name
        public String killer;      // name of who killed this person (null if alive)
        public AssassinNode next;  // next node in the list (null if none)
        
        /**
         * Constructs a new node to store the given name and no next node.
         */
        public AssassinNode(String name) {
            this(name, null);
        }

        /**
         * Constructs a new node to store the given name and a reference
         * to the given next node.
         */
        public AssassinNode(String name, AssassinNode next) {
            this.name = name;
            this.killer = null;
            this.next = next;
        }
    }
}
