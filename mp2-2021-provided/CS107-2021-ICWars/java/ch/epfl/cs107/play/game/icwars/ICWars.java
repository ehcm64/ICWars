package ch.epfl.cs107.play.game.icwars;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.unit.Tank;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICWars extends AreaGame {
	public final static float CAMERA_SCALE_FACTOR = 10.f;

	private ArrayList<ICWarsPlayer> playerList = new ArrayList<ICWarsPlayer>();
	private ArrayList<Unit> allyUnits = new ArrayList<Unit>();
	private ArrayList<Unit> enemyUnits = new ArrayList<Unit>();
	private ArrayList<ICWarsPlayer> waitingForTurn;
	private ArrayList<ICWarsPlayer> waitingForNextTurn;
	private ICWarsPlayer currentPlayer;
	private GameState currentGameState;
	private RealPlayer allyPlayer;
	private RealPlayer enemyPlayer;
	private AIPlayer computer;
	private final String[] areas = { "icwars/Level0", "icwars/Level1" };
	private int areaIndex;

	public enum GameState {
		INIT,
		CHOOSE_PLAYER,
		START_PLAYER_TURN,
		PLAYER_TURN,
		END_PLAYER_TURN,
		END_TURN,
		END;
	}

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
			this.currentGameState = GameState.INIT;
			return true;
		}
		return false;
	}

	private void createUnits() {
		removeUnitsFromTempList();
		Soldier allySoldier = new Soldier(getCurrentArea(), new DiscreteCoordinates(3, 5), Faction.ALLY);
		Tank allyTank = new Tank(getCurrentArea(), new DiscreteCoordinates(2, 5), Faction.ALLY);
		this.allyUnits.add(allySoldier);
		this.allyUnits.add(allyTank);

		Soldier enemySoldier = new Soldier(getCurrentArea(), new DiscreteCoordinates(9, 5), Faction.ENEMY);
		Tank enemyTank = new Tank(getCurrentArea(), new DiscreteCoordinates(8, 5), Faction.ENEMY);
		this.enemyUnits.add(enemySoldier);
		this.enemyUnits.add(enemyTank);
	}

	private void removeUnitsFromTempList() {
		this.allyUnits = new ArrayList<Unit>();
		this.enemyUnits = new ArrayList<Unit>();
	}

	private void resetPlayersAndUnits() {
		ArrayList<ICWarsPlayer> playersToRemove = new ArrayList<ICWarsPlayer>();
		for (ICWarsPlayer player : this.playerList) {
			playersToRemove.add(player);
		}
		if (playersToRemove.size() != 0) {
			for (ICWarsPlayer player : playersToRemove) {
				this.playerList.remove(player);
			}
		}
		createUnits();
		this.computer = new AIPlayer(this.getCurrentArea(), ((ICWarsArea) getCurrentArea()).getEnemySpawnPosition(),
				Faction.ENEMY, this.enemyUnits);
		this.allyPlayer = new RealPlayer(this.getCurrentArea(),
				((ICWarsArea) getCurrentArea()).getPlayerSpawnPosition(), Faction.ALLY, this.allyUnits);
		this.playerList.add(this.allyPlayer);
		this.playerList.add(this.computer);
		removeUnitsFromTempList();
	}

	private void initArea(String areaKey) {
		ICWarsArea area = (ICWarsArea) setCurrentArea(areaKey, true);
		resetPlayersAndUnits();
		this.computer.enterArea(area, ((ICWarsArea) getCurrentArea()).getEnemySpawnPosition());
		this.allyPlayer.enterArea(area, ((ICWarsArea) getCurrentArea()).getPlayerSpawnPosition());
	}

	@Override
	public void update(float deltaTime) {

		Keyboard keyboard = getCurrentArea().getKeyboard();
		switch (this.currentGameState) {
			case INIT:
				this.waitingForTurn = new ArrayList<ICWarsPlayer>();
				this.waitingForNextTurn = new ArrayList<ICWarsPlayer>();
				for (ICWarsPlayer player : this.playerList) {
					this.waitingForTurn.add(player);
				}
				this.currentGameState = GameState.CHOOSE_PLAYER;
				break;
			case CHOOSE_PLAYER:
				if (this.waitingForTurn.size() == 0) {
					this.currentGameState = GameState.END_TURN;
				} else {
					this.currentPlayer = this.waitingForTurn.get(0);
					this.waitingForTurn.remove(0);
					this.currentGameState = GameState.START_PLAYER_TURN;
				}
				break;
			case START_PLAYER_TURN:
				this.currentPlayer.startTurn();
				this.currentGameState = GameState.PLAYER_TURN;
				break;
			case PLAYER_TURN:
				if (this.currentPlayer.getPlayerState() == ICWarsPlayer.State.IDLE) {
					this.currentGameState = GameState.END_PLAYER_TURN;
				}
				break;
			case END_PLAYER_TURN:
				if (this.currentPlayer.isDefeated()) {
					this.currentPlayer.leaveArea();
				} else {
					this.waitingForNextTurn.add(this.currentPlayer);
					this.currentGameState = GameState.CHOOSE_PLAYER;
				}
				break;
			case END_TURN:
				ArrayList<ICWarsPlayer> tempList = new ArrayList<ICWarsPlayer>();
				for (ICWarsPlayer player : this.playerList) {
					if (player.isDefeated()) {
						this.waitingForNextTurn.remove(player);
						tempList.add(player);
					}
				}
				for (ICWarsPlayer player : tempList) {
					this.playerList.remove(player);
				}
				if (this.waitingForNextTurn.size() == 1) {
					this.currentGameState = GameState.END;
				} else {
					for (ICWarsPlayer player : this.waitingForNextTurn) {
						this.waitingForTurn.add(player);
					}
					this.waitingForNextTurn = new ArrayList<ICWarsPlayer>();
					this.currentGameState = GameState.CHOOSE_PLAYER;
				}
				break;
			case END:
				nextLevel();
				break;
			default:
				break;
		}
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
		this.computer.leaveArea();
		areaIndex = 0;
		initArea(areas[areaIndex]);
		this.currentGameState = GameState.INIT;
		this.allyPlayer.startTurn();
	}

	@Override
	public void end() {
		// TO DO NSM LE GAMEOVER
		removeUnitsFromTempList();
		this.allyPlayer.removeAllUnits();
		this.computer.removeAllUnits();
		System.out.println("GAME OVER");
		ArrayList<Unit> units = new ArrayList<Unit>();
		ICWarsArea area = (ICWarsArea) getCurrentArea();
		DiscreteCoordinates position = this.allyPlayer.getPosition().toDiscreteCoordinates();

		this.allyPlayer.leaveArea();
		this.computer.leaveArea();
		this.playerList = new ArrayList<ICWarsPlayer>();
		RealPlayer gameEnd = new RealPlayer(area, position, Faction.GAMEOVER, units);
		this.playerList.add(gameEnd);
		gameEnd.enterArea(area, position);
		gameEnd.startTurn();
	}

	@Override
	public String getTitle() {
		return "ICWars";
	}

	protected void nextLevel() {
		areaIndex += 1;
		if (areaIndex < areas.length) {
			this.allyPlayer.leaveArea();
			this.computer.leaveArea();
			ICWarsArea currentArea = (ICWarsArea) setCurrentArea(areas[areaIndex], true);
			resetPlayersAndUnits();
			this.computer.enterArea(currentArea, currentArea.getEnemySpawnPosition());
			this.allyPlayer.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
			this.currentGameState = GameState.INIT;
		} else if (areaIndex == areas.length) {
			end();
		}
	}
}