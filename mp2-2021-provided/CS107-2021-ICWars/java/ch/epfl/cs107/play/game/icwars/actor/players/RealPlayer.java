package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class RealPlayer extends ICWarsPlayer {
    private ICWarsPlayerGUI gui = new ICWarsPlayerGUI(ICWars.CAMERA_SCALE_FACTOR, this);
    private final static int MOVE_DURATION = 8;

    public RealPlayer(Area area, DiscreteCoordinates position, Faction faction, ArrayList<Unit> units) {
        super(area, position, faction, units);
        if (faction.equals(Faction.ALLY)) this.name = "icwars/allyCursor";
        else this.name = "icwars/enemyCursor";
        sprite = new Sprite(this.name, 1.f, 1.f, this);
    }

    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.gui.setPlayerSelectedUnit(this.selectedUnit);
        this.gui.draw(canvas);
    }

    public void selectUnit(int index) {
        if (index < this.units.size()) {
            this.selectedUnit = this.units.get(index);
        }
    }

/**
 * Orientate and Move this player in the given orientation if the given button is down
 * @param orientation (Orientation): given orientation, not null
 * @param b (Button): button corresponding to the given orientation, not null
 */
	private void moveIfPressed(Orientation orientation, Button b){
	    if(b.isDown()) {
	        if (!isDisplacementOccurs()) {
	            orientate(orientation);
	            move(MOVE_DURATION);
            }
	    }
	}
}
