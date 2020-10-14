package game;

import edu.monash.fit2099.engine.*;
import game.actors.Player;
import game.actors.Stegosaur;
import game.ground.*;

import java.util.Arrays;
import java.util.List;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(
				new Dirt(),
				new Wall(),
				new Floor(),
				new Tree(),
				new VendingMachine()
		);
		
		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		".....#######....................................................................",
		".....#Q____#....................................................................",
		".....#_____#....................................................................",
		".....###.###....................................................................",
		"................................................................................",
		"......................................+++.......................................",
		".......................................++++.....................................",
		"...................................+++++........................................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		".....................................+++........................................",
		"................................................................................",
		"............+++.................................................................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);
		
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(29, 12)); // TODO: change back to (9, 4) when testing done
		
		// Place a pair of stegosaurs (of opposite sex) in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur("Male"));
		gameMap.at(32, 12).addActor(new Stegosaur("Female"));

		world.run();
	}
}
