package game;

import edu.monash.fit2099.engine.*;
import game.actors.Allosaur;
import game.actors.Player;
import game.actors.Stegosaur;
import game.ground.*;
import game.worlds.WorldFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		Scanner inputReader = new Scanner(System.in);

		boolean stillRunMenu = true;
		while (stillRunMenu) {
			System.out.println("Welcome to Designosaurs! Rawr xD");
			System.out.println("Select which number of game mode to play");
			System.out.println("1: Challenge mode");
			System.out.println("2: Sandbox mode");
			System.out.println("3: Quit");

			String gameNumber = inputReader.nextLine();
			String worldName;

			if (gameNumber.equals("1")) {
				worldName = "Challenge";
			}
			else if (gameNumber.equals("2")) {
				worldName = "Sandbox";
			}
			else if (gameNumber.equals("3")) {
				stillRunMenu = false;
				break;
			}
			else {
				System.out.println("Command not found.");
				continue;
			}

			World world = WorldFactory.getWorld(worldName, new Display());
			playWorld(world);
		}
		System.out.println("Game has quit.");
	}

	/**
	 * Create World and play for the user.
	 * @param world - World type depending on game mode selected by user
	 */
	public static void playWorld(World world) {
		FancyGroundFactory groundFactory = new FancyGroundFactory(
				new Dirt(),
				new Wall(),
				new Floor(),
				new Tree(),
				new Water(),
				new VendingMachine()
		);

		List<String> map2 = Arrays.asList(
				"................................................................................",
				"......................................~~........................................",
				".....++++............~~~.............~~~~.......................................",
				"....++++++.............~........................................................",
				"......++++++....................................................................",
				".....++++++.....................................................................",
				"................................................................................",
				"......................................+++.......................................",
				"..................~~...................++++.................~~~.................",
				".................~~~...............+++++.....................~~.................",
				".....................................++++++.....................................",
				"......................................+++.......................................",
				".....................................+++........................................",
				"................................................................................",
				"............+++.................................................................",
				".............+++++...............~~~~~~.........................................",
				"...............++..............~~~~~~~~~~~...............+++++..................",
				".............+++..............~~~~~~~~~~............++++++++....................",
				"............+++..................~~~~~~~~.............+++.......................",
				"...............................~~~~.............................................",
				".........................................................................++.....",
				"........................................................................++.++...",
				".........................................................................++++...",
				".........................................................~~~..............++....",
				"................................................................................");

		List<String> map1 = Arrays.asList(
				"................................................................................",
				"......................................~~........................................",
				".....#######.........................~~~~.......................................",
				".....#Q____#....................................................................",
				".....#_____#....................................................................",
				".....###.###....................................................................",
				"................................................................................",
				"......................................+++.......................................",
				"..................~~...................++++.................~~~.................",
				".................~~~...............+++++.....................~~.................",
				".....................................++++++.....................................",
				"......................................+++.......................................",
				".....................................+++........................................",
				"................................................................................",
				"............+++.................................................................",
				".............+++++...............~~~~~~.........................................",
				"...............++..............~~~~~~~~~~~...............+++++..................",
				".............+++..............~~~~~~~~~~............++++++++....................",
				"............+++..................~~~~~~~~.............+++.......................",
				"...............................~~~~.............................................",
				".........................................................................++.....",
				"........................................................................++.++...",
				".........................................................................++++...",
				".........................................................~~~..............++....",
				"................................................................................");

		GameMap gameMap1 = new GameMap(groundFactory, map1);
		GameMap gameMap2 = new GameMap(groundFactory, map2);

		world.addGameMap(gameMap1);
		world.addGameMap(gameMap2);

		addMapNorth(gameMap1, gameMap2);

		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap1.at(9, 4));

		// Place a pair of stegosaurs (of opposite sex) in the middle of the map
		gameMap1.at(30, 12).addActor(new Stegosaur("Male"));
		gameMap1.at(32, 12).addActor(new Stegosaur("Female"));

		world.run();
	}

	/**
	 * Extends GameMap existingMap, by "placing" another GameMap mapToAdd to the north
	 * of existingMap. Thus, Player can move between game maps (system only displays the GameMap
	 * Player is in). Activity in all game maps continues as normal.
	 *
	 * @param existingMap - GameMap currently existing in the game
	 * @param mapToAdd - GameMap to add to the north of existingMap
	 */
	private static void addMapNorth(GameMap existingMap, GameMap mapToAdd) {
		// Iterate over top row of existingMap and add exits
		for (int i: existingMap.getXRange()) {
			int yToLeave = existingMap.getYRange().min();
			int yToEnter = mapToAdd.getYRange().max();
			int xRightCorner = mapToAdd.getXRange().max();
			int xLeftCorner = mapToAdd.getXRange().min();

			Location here = existingMap.at(i, yToLeave);
			here.addExit(new Exit("North", mapToAdd.at(i, yToEnter), "8"));

			// Ensures that system does not try and access coordinates out of bounds
			if (i != xRightCorner) {
				here.addExit(new Exit("North-East", mapToAdd.at(i + 1, yToEnter), "9"));
			}
			if (i != xLeftCorner) {
				here.addExit(new Exit("North-West", mapToAdd.at(i - 1, yToEnter), "7"));
			}
		}
		// Iterate over bottom row of mapToAdd and add exits
		for (int i: mapToAdd.getXRange()) {
			int yToLeave = mapToAdd.getYRange().max();
			int yToEnter = existingMap.getYRange().min();
			int xRightCorner = existingMap.getXRange().max();
			int xLeftCorner = existingMap.getXRange().min();

			Location here = mapToAdd.at(i, yToLeave);
			here.addExit(new Exit("South", existingMap.at(i, yToEnter), "2"));

			// Ensures that system does not try and access coordinates out of bounds
			if (i != xRightCorner) {
				here.addExit(new Exit("South-East", existingMap.at(i + 1, yToEnter), "3"));
			}
			if (i != xLeftCorner) {
				here.addExit(new Exit("South-West", existingMap.at(i - 1, yToEnter), "1"));
			}
		}
	}
}
