import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */

//TODO Dela in satser i input, game logic och output. Snygga till programmet.

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
		Player temp;

		// Hard coded 2 players, replace *last* of all with ...
		players = getPlayers();

		actual = players[0];

		welcomeMsg(winPts);
		statusMsg(players);


		while (!aborted) {

			String input = getPlayerChoice(actual);

			action = determineAction(input);
			temp = performAction(action, actual, players, winPts, aborted);
			if (checkWin(actual, winPts)) {
				gameOverMsg(actual, aborted);
				aborted = true;
			}
			actual = temp;
		}


	}


	// ---- Game logic methods --------------
	
	/**
	 *  Calculates if <code>player</code> have the same or more than <code>winPts</code>
	 * @return <code>true</code>/ <code>false</code>
	 */
	boolean checkWin(Player player, int winPts) {
		if (player.totalPts + player.roundPts >= winPts) {
			return true;
		} else {
			return false;
		}
	}


	//Finaste metoden hittills.
	/**
	 * Adds the <code>player</code>'s round points to <code>player</code>'s total points.<br><br>
	 * -Vinci "<i>Finaste metoden hittills.</i>"
	 * 
	 */
	void addPoint(Player player) {
		player.totalPts += player.roundPts;
		player.roundPts = 0;
	}


	/**
	 *  
	 * @return The index the next player.
	 */
	int changePlayer(Player[] players, Player actual) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == actual) {
				return (i+1) % players.length;
			}
		}
		return 0;
	}


	/**
	 * @return The next player based on <code>input</code>.
	 * @param action {1,2,3}
	 */
	Player performAction(int action, Player actual, Player[] players, int winPts, boolean aborted) {
		//Roll die
		if (action == 1) {
			int diceRoll = rollDie();
			if (isOne(diceRoll)) {
				actual.roundPts = 0;
				roundMsg(diceRoll, actual);

				//Låt actual peka på nästa index som tas fram via changePlayer
				return players[changePlayer(players, actual)];

			} else {
				actual.roundPts += diceRoll;
				roundMsg(diceRoll, actual);
			}
		} else if (action == 2) {
			addPoint(actual);
			return players[changePlayer(players, actual)];

		} else if (action == 3) {
			aborted = true;
			gameOverMsg(actual, aborted);
		}
		return actual;
	}

	
	/**
	 * @author Mattias
	 * @return A generated int from 1 to 6.
	 */
	int rollDie() {
		return rand.nextInt(6)+1;
	}

	/**
	 * @return A boolean if <code>input</code> is 1.
	 */
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
		out.print("How many players? > ");

		int amount = setAmountOfPlayers();
		Player[] players = new Player[amount];
		//Fills players-array
		for (int i = 0; i < amount; i++){
			out.print("Name for player " + (i + 1) + " > ");
			players[i] = new Player(sc.nextLine());

		}

		return players;
	}

	/**
	 * @author Mattias
	 * @return Inputed int of players.
	 */
	int setAmountOfPlayers() {
		//TODO PLEASE LOOK HERE, WERY NICE!!
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			out.println("Please enter a Integer!");
			return setAmountOfPlayers();
		}
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



