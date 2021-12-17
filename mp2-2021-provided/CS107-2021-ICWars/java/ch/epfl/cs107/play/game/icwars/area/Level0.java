package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.unit.Tank;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

/**
 * Specific area
 */
public class Level0 extends ICWarsArea {

	@Override
	public String getTitle() {
		return "icwars/Level0";
	}

	@Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return new DiscreteCoordinates(0, 0);
	}

	@Override
	public DiscreteCoordinates getEnemySpawnPosition() {
		return new DiscreteCoordinates(7, 4);
	}

	protected void createArea() {
		// Base
		registerActor(new Background(this));
	}

}