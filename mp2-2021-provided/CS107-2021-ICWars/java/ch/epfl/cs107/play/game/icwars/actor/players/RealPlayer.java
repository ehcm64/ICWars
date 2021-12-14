package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class RealPlayer extends ICWarsPlayer {
    private ICWarsPlayerGUI gui = new ICWarsPlayerGUI(ICWars.CAMERA_SCALE_FACTOR, this);
    private final static int MOVE_DURATION = 8;
    private final ICWarsPlayerInteractionHandler handler;

    public RealPlayer(Area area, DiscreteCoordinates position, Faction faction, ArrayList<Unit> units) {
        super(area, position, faction, units);
        this.handler = new ICWarsPlayerInteractionHandler();
        if (faction.equals(Faction.ALLY))
            this.name = "icwars/allyCursor";
        else
            this.name = "icwars/enemyCursor";
        sprite = new Sprite(this.name, 1.f, 1.f, this);

    }

    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        switch (this.currentState) {
            case IDLE:
                break;
            case NORMAL:
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    this.currentState = State.SELECT_CELL;
                } else if (keyboard.get(Keyboard.TAB).isReleased()) {
                    this.currentState = State.IDLE;
                } else {
                    moveIfPressed();
                }
                break;
            case SELECT_CELL:
                for (Unit unit : this.units) {
                    if (unit.getPosition().equals(this.getPosition())) {
                        this.interactWith(unit);
                    }
                }
                if (this.selectedUnit != null) {
                    this.currentState = State.MOVE_UNIT;
                } else {
                    moveIfPressed();
                    this.onLeaving(this.getCurrentCells());
                }
                break;
            case MOVE_UNIT:
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    DiscreteCoordinates playerCoords = new DiscreteCoordinates((int) this.getPosition().getX(),
                            (int) this.getPosition().getY());
                    if (!this.selectedUnit.getMoveState() && !this.selectedUnit.getPosition().toDiscreteCoordinates()
                            .equals(this.getPosition().toDiscreteCoordinates())) {
                        this.selectedUnit.changePosition(playerCoords);
                        this.selectedUnit.setMoveState(true);
                    }
                    this.selectedUnit = null;
                    this.currentState = State.NORMAL;
                }
                moveIfPressed();
                break;
            case ACTION_SELECTION:
                break;
            case ACTION:
                break;
            default:
                break;
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.selectedUnit != null && this.currentState == State.MOVE_UNIT) {
            this.gui.setPlayerSelectedUnit(selectedUnit);
            this.gui.draw(canvas);
        }
    }

    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates) {
        if (this.currentState == State.SELECT_CELL)
            this.currentState = State.NORMAL;
    }

    /**
     * Orientate and Move this player in the given orientation if the given button
     * is down
     * 
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation,
     *                    not null
     */
    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    private void moveIfPressed() {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        // TODO Auto-generated method stub
        return false;
    }

    private class ICWarsPlayerInteractionHandler implements ICWarsInteractionVisitor {

        @Override
        public void interactWith(Unit other) {
            if (currentState == State.SELECT_CELL && other.getFaction() == faction) {
                selectedUnit = other;
                gui.setPlayerSelectedUnit(selectedUnit);
            }
        }

        @Override
        public void interactWith(RealPlayer other) {
            System.out.print("oui");
        }
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }
}