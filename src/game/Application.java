package game;

import edu.monash.fit2099.engine.*;
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

		List<String> map = Arrays.asList(
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
		GameMap gameMap = new GameMap(groundFactory, map);
		world.addGameMap(gameMap);

		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(9, 4));

		// Place a pair of stegosaurs (of opposite sex) in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur("Male"));
		gameMap.at(32, 12).addActor(new Stegosaur("Female"));

		world.run();
	}
}
