package ch.epfl.cs107.play.game.icwars;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.units.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.units.Tank;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICWars extends AreaGame {
	// TODO REORGANISE UNIT AND PLAYER MANAGEMENT WHEN CHANGING LEVEL
	public final static float CAMERA_SCALE_FACTOR = 10.f;

	private ArrayList<ICWarsPlayer> playerList = new ArrayList<ICWarsPlayer>();
	private ArrayList<Unit> allyUnits = new ArrayList<Unit>();
	private ArrayList<Unit> enemyUnits = new ArrayList<Unit>();
	private RealPlayer allyPlayer;
	private RealPlayer enemyPlayer;
	private final String[] areas = { "icwars/Level0", "icwars/Level1" };

	private int areaIndex;

	/**
	 * Add all the areas
	 */
	private void createAreas() {
		addArea(new Level0());
		addArea(new Level1());
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {

		if (super.begin(window, fileSystem)) {
			createAreas();
			areaIndex = 0;
			initArea(areas[areaIndex]);
			this.allyPlayer.startTurn();
			return true;
		}
		return false;
	}

	private void initArea(String areaKey) {

		ICWarsArea area = (ICWarsArea) setCurrentArea(areaKey, true);
		DiscreteCoordinates allyCoords = area.getPlayerSpawnPosition();
		DiscreteCoordinates enemyCoords = area.getEnemySpawnPosition();

		Soldier allySoldier = new Soldier(getCurrentArea(), new DiscreteCoordinates(3, 5), Faction.ALLY);
		Tank allyTank = new Tank(getCurrentArea(), new DiscreteCoordinates(2, 5), Faction.ALLY);
		this.allyUnits.add(allySoldier);
		this.allyUnits.add(allyTank);

		Soldier enemySoldier = new Soldier(getCurrentArea(), new DiscreteCoordinates(9, 5), Faction.ENEMY);
		Tank enemyTank = new Tank(getCurrentArea(), new DiscreteCoordinates(8, 5), Faction.ENEMY);
		this.enemyUnits.add(enemySoldier);
		this.enemyUnits.add(enemyTank);

		this.enemyPlayer = new RealPlayer(area, enemyCoords, Faction.ENEMY, enemyUnits);
		this.allyPlayer = new RealPlayer(area, allyCoords, Faction.ALLY, allyUnits);
		this.playerList.add(allyPlayer);
		this.playerList.add(enemyPlayer);
		this.allyUnits = new ArrayList<Unit>();
		this.enemyUnits = new ArrayList<Unit>();

		this.enemyPlayer.enterArea(area, enemyCoords);
		this.allyPlayer.enterArea(area, allyCoords);
	}

	@Override
	public void update(float deltaTime) {
		Keyboard keyboard = getCurrentArea().getKeyboard();
		if (keyboard.get(Keyboard.N).isReleased()) {
			nextLevel();
		} else if (keyboard.get(Keyboard.R).isReleased()) {
			reset();
		}
		super.update(deltaTime);
	}

	private void reset() {
		// BUG : garde une ancienne unité aprés qu'elle ait bougé
		this.allyPlayer.leaveArea();
		areaIndex = 0;
		initArea(areas[areaIndex]);
		this.allyPlayer.startTurn();
	}

	@Override
	public void end() {
		System.out.println("GAME OVER");
	}

	@Override
	public String getTitle() {
		return "ICWars";
	}

	protected void nextLevel() {
		int maxAreaIndex = areas.length - 1;
		if (areaIndex != maxAreaIndex) {
			areaIndex += 1;
			allyPlayer.leaveArea();
			ICWarsArea currentArea = (ICWarsArea) setCurrentArea(areas[areaIndex], false);
			allyPlayer.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
		} else {
			end();
		}

	}

}