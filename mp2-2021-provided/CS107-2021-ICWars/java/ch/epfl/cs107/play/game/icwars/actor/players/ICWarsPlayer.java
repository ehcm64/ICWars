package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICWarsPlayer extends ICWarsActor{
    //TODO 2.2.2
    protected State currentState;
    protected ArrayList<Unit> units = new ArrayList<Unit>();
    protected Sprite sprite;
    protected String name;
    protected Unit selectedUnit;

    public enum State {
        IDLE,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECTION,           
        ACTION;
    }

    public ICWarsPlayer(Area area, DiscreteCoordinates position, Faction faction, ArrayList<Unit> units) {
        super(area, position, faction);
        this.selectedUnit = null;
        this.currentState = State.IDLE;
        for (Unit unit : units) {
            this.units.add(unit);
        }
    }

    protected void updateState() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        switch (this.currentState) {
            case IDLE:
            break;
            case NORMAL:
            if (keyboard.get(Keyboard.ENTER).isReleased()) this.currentState = State.SELECT_CELL;
            else if (keyboard.get(Keyboard.TAB).isReleased()) this.currentState = State.IDLE;
            break;
            case SELECT_CELL:
            if (this.selectedUnit != null) this.currentState = State.MOVE_UNIT;
            // si jouer quitte une cellule --
            break;
            case MOVE_UNIT:
            if (keyboard.get(Keyboard.ENTER).isReleased()) {
                DiscreteCoordinates playerCoords = new DiscreteCoordinates((int)this.getPosition().getX(), (int)this.getPosition().getY());
                if (!this.selectedUnit.getMoveState()) {
                    this.selectedUnit.changePosition(playerCoords);
                    this.selectedUnit = null;
                }
                this.currentState = State.NORMAL;
            }
            break;
            case ACTION_SELECTION:
            break;
            case ACTION:
            break;
            default:
                break;
        }
    }

    public void startTurn() {
        this.currentState = State.NORMAL;
        centerCamera();
        for (Unit unit : this.units) {
            unit.setMoveState(false);
            unit.setAttackState(false);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        ArrayList<Unit> unitsToRemove = new ArrayList<Unit>();
        for (Unit unit : this.units) {
            if (unit.getHp() == 0) unitsToRemove.add(unit);
        }
        if (unitsToRemove.size() != 0) {
            for (Unit unit : unitsToRemove) {
                this.units.remove(unit);
            }
        }
        updateState();
        super.update(deltaTime);
    }

    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
        for (Unit unit : this.units) {
            area.registerActor(unit);
        }
	    area.registerActor(this);
        setOwnerArea(area);
	    setCurrentPosition(position.toVector());
        centerCamera();
        resetMotion();
	}

    @Override
	public void leaveArea(){
        for (Unit unit: units) {
            getOwnerArea().unregisterActor(unit);
        }
	    getOwnerArea().unregisterActor(this);
	}

    @Override
    public void onLeaving (List<DiscreteCoordinates> coordinates) {
        this.currentState = State.NORMAL;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    public boolean isDefeated() {
        if (this.units.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        // TODO Auto-generated method stub
        return false;
    }
}
