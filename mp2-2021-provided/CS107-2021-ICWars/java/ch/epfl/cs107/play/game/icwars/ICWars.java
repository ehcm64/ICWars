package ch.epfl.cs107.play.game.icwars;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.units.Soldat;
import ch.epfl.cs107.play.game.icwars.actor.units.Tank;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICWars extends AreaGame {
	
	public final static float CAMERA_SCALE_FACTOR = 10.f;

	private RealPlayer player;
	private ArrayList<Unit> playerUnits = new ArrayList<Unit>();
	private final String[] areas = {"icwars/Level0", "icwars/Level1"};
	
	private int areaIndex;
	/**
	 * Add all the areas
	 */
	private void createAreas(){
		addArea(new Level0());
		addArea(new Level1());
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {

		if (super.begin(window, fileSystem)) {
			createAreas();
			areaIndex = 0;
			initArea(areas[areaIndex]);
			return true;
		}
		return false;
	}
	
	private void initArea(String areaKey) {
		 
		ICWarsArea area = (ICWarsArea)setCurrentArea(areaKey, true);
		
		DiscreteCoordinates coords = area.getPlayerSpawnPosition();
		
		
		player = new RealPlayer(area, coords, Faction.ALLY, playerUnits);
		player.enterArea(area, coords);
		Soldat soldat = new Soldat(getCurrentArea(), new DiscreteCoordinates(3, 5), Faction.ALLY);
		soldat.enterArea(area, new DiscreteCoordinates(3, 5));
		
	    Tank tank = new Tank(getCurrentArea(), new DiscreteCoordinates(2, 5), Faction.ALLY);
		tank.enterArea(area, new DiscreteCoordinates(2, 5));
	}

	@Override
	public void update(float deltaTime) {
		Keyboard keyboard = getCurrentArea().getKeyboard();
		if (keyboard.get(Keyboard.N).isReleased()) {
			nextArea();
		} else if (keyboard.get(Keyboard.R).isReleased()) {
			reset();
		}
		super.update(deltaTime);
	}

	private void reset() {
		//TODO
	}

	private Area getOwnerArea() {
		return null;
	}

	@Override
	public void end() {
	}

	@Override
	public String getTitle() {
		return "ICWars";
	}

	protected void nextArea() {
		player.leaveArea();
		areaIndex = (areaIndex==0) ? 1 : 0;
		ICWarsArea currentArea = (ICWarsArea)setCurrentArea(areas[areaIndex], false);
		player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
	}
}