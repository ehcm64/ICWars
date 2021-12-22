package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
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
        float spriteSize = 1.f;
        Vector anchor = Vector.ZERO;
        if (faction.equals(Faction.ALLY)) {
            this.name = "icwars/allyCursor";
        } else if (faction.equals(Faction.GAMEOVER)) {
            spriteSize = 6.f;
            anchor = new Vector(-2.5f, -2.5f);
            this.name = "icwars/GameOver";
        } else if (faction.equals(Faction.ENEMY)) {
            this.name = "icwars/enemyCursor";
        }
        this.sprite = new Sprite(this.name, spriteSize, spriteSize, this, null, anchor);
        this.act = null;
    }

    @Override
    public void update(float deltaTime) {
        
        Keyboard keyboard = getOwnerArea().getKeyboard();
        switch (this.currentState) {
            case IDLE:
                if (this.turnStarting) {
                    this.currentState = State.NORMAL;
                }
                break;
            case NORMAL:
                this.centerCamera();
                this.gui.setViewedUnit(null);
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    this.currentState = State.SELECT_CELL;
                } else if (keyboard.get(Keyboard.TAB).isReleased()) {
                    this.turnStarting = false;
                    this.currentState = State.IDLE;
                } else {
                    ArrayList<Unit> allUnits = ((ICWarsArea) getOwnerArea()).getAllUnits();
                    for (Unit unit : allUnits) {
                        if (unit.getPosition().equals(this.getPosition())) {
                            this.gui.setViewedUnit(unit);
                        }
                    }
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
                if (!this.selectedUnit.getMoveState() && keyboard.get(Keyboard.ENTER).isReleased()) {
                    DiscreteCoordinates playerCoords = new DiscreteCoordinates((int) this.getPosition().getX(),
                            (int) this.getPosition().getY());
                    if (!this.selectedUnit.getPosition().toDiscreteCoordinates()
                            .equals(this.getPosition().toDiscreteCoordinates())) {
                        this.selectedUnit.changePosition(playerCoords);
                    }
                    this.selectedUnit = null;
                    this.currentState = State.NORMAL;
                } else if (this.selectedUnit.getMoveState() && !this.selectedUnit.getActionState()) {
                    this.currentState = State.ACTION_SELECTION;
                } else if (this.selectedUnit.getMoveState() && this.selectedUnit.getActionState()) {
                    this.currentState = State.NORMAL;
                } else {
                    moveIfPressed();
                }
                break;
            case ACTION_SELECTION:
                ArrayList<Action> selectedUnitActions = this.selectedUnit.getActions();
                for (Action action : selectedUnitActions) {
                    if (keyboard.get(action.getKey()).isReleased()) {
                        this.currentState = State.ACTION;
                        this.act = action;
                    }
                }
                break;
            case ACTION:
                this.act.doAction(deltaTime, this, keyboard);
                break;
            default:
                break;
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.currentState != State.IDLE) {
            this.gui.setPlayerSelectedUnit(selectedUnit);
            this.gui.setCellType(
                    ((ICWarsArea) this.getOwnerArea()).currentCellType(this.getPosition().toDiscreteCoordinates()));
            this.gui.draw(canvas);
            super.draw(canvas);
        }
        if (this.currentState == State.ACTION) {
            this.act.draw(canvas);
        }
    }

    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates) {
        if (this.currentState == State.SELECT_CELL)
            this.currentState = State.NORMAL;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return new ArrayList<DiscreteCoordinates>();
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    private class ICWarsPlayerInteractionHandler implements ICWarsInteractionVisitor {

        @Override
        public void interactWith(Unit other) {
            if (currentState == State.SELECT_CELL && other.getFaction() == faction && !isDisplacementOccurs()) {
                selectedUnit = other;
                gui.setPlayerSelectedUnit(selectedUnit);
            }
        }

        @Override
        public void interactWith(RealPlayer other) {
        }
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
    /**
     * Check if any movement key is pressed and move (or not) accordingly.
     */
    private void moveIfPressed() {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
    }
}