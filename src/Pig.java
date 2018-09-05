import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */

//TODO Dela in satser i input, game logic och output. SNygga till programmet.

public class Pig {

    public static void main(String[] args) {
        new Pig().program();
    }

    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();

    void program() {
        //test();                 // <-------------- Uncomment to run tests!

        final int winPts = 20;    // Points to win
        Player[] players;         // The players (array of Player objects)
        Player actual;            // Actual player for round (must use)
        boolean aborted = false;  // Game aborted by player?
        int action;

        // Hard coded 2 players, replace *last* of all with ...
        players = new Player[]{new Player("Olle"), new Player("Fia"), new Player("Matret")};
        //players = getPlayers();  // ... this (method to read in all players)

        actual = players[0];

        welcomeMsg(winPts);
        statusMsg(players);

        // TODO Game logic, using small step, functional decomposition
        while (!aborted) {

            String input = getPlayerChoice(actual);

            action = determineAction(input);
            performAction(action, actual, players, winPts, aborted);

        }


    }
    //gameOverMsg(actual, aborted);

    // ---- Game logic methods --------------

    // TODO

    boolean checkWin(Player p, int win) {
        if (p.totalPts + p.roundPts >= win) {
            return true;
        } else {
            return false;
        }
    }

    //Finaste metoden hittills.

    void addPoint(Player p) {
        p.totalPts += p.roundPts;
        p.roundPts = 0;
    }

    int changePlayer(Player[] p, Player a) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == a) {
                if (i >= p.length - 1) {
                    statusMsg(p);
                    return 0;
                } else {
                    statusMsg(p);
                    return i + 1;
                }
            }
        }
        return 0;
    }

    void performAction(int action, Player actual, Player[] players, int winPts, boolean aborted) {
        //Roll die
        if (action == 1) {
            int diceRoll = rand.nextInt(6) + 1;
            if (isOne(diceRoll)) {
                actual.roundPts = 0;
                roundMsg(diceRoll, actual);


                //Låt actual peka på nästa index som tas fram via changePlayer
                //TODO lös problem med call by value
                actual = players[changePlayer(players, actual)];

            } else {
                actual.roundPts += diceRoll;
                roundMsg(diceRoll, actual);

                if (checkWin(actual, winPts)) {
                    gameOverMsg(actual, aborted);
                    //TODO lös problem med call by value.
                    aborted = true;
                }
            }
        } else if (action == 2) {
            addPoint(actual);
            //TODO lös problem, kolla upp interface, eller byt till c++
            actual = players[changePlayer(players, actual)];

        } else if (action == 3) {
            aborted = true;
            gameOverMsg(actual, aborted);
        }

    }

    boolean isOne(int input) {
        if (input == 1) {
            return true;
        }
        return false;
    }


    // ---- IO methods ------------------

    void welcomeMsg(int winPoints) {
        out.println("Welcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        out.println("Commands are: r = roll , n = next, q = quit");
        out.println();
    }

    void statusMsg(Player[] players) {
        out.print("Points: ");
        for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + " = " + players[i].totalPts + " ");
        }
        out.println();
    }

    void roundMsg(int result, Player actual) {
        if (result > 1) {
            out.println("Got " + result + " running total are " + actual.roundPts);
        } else {
            out.println("Got 1 lost it all!");
        }
    }

    void gameOverMsg(Player player, boolean aborted) {
        if (aborted) {
            out.println("Aborted");
        } else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts + player.roundPts) + " points");
        }
    }

    String getPlayerChoice(Player player) {
        out.print("Player is " + player.name + " > ");
        return sc.nextLine();
    }

    //1 = roll, 2 = next, 3 = quit.
    int determineAction(String input) {
        if (input.equals("r")) {
            return 1;
        } else if (input.equals("n")) {
            return 2;
        } else if (input.equals("q")) {
            return 3;
        }
        return 0;
    }

    Player[] getPlayers() {
        // TODO
        return null;
    }

    // ---------- Class -------------
    // Class representing the concept of a player
    // Use the class to create (instantiate) Player objects
    class Player {
        String name;     // Default null
        int totalPts;    // Total points for all rounds, default 0
        int roundPts;    // Points for a single round, default 0

        Player(String name) {
            this.name = name;
        }
    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data, an array of Players
        Player[] players = {new Player("a"), new Player("b"), new Player("c")};


        exit(0);   // End program
    }
}



