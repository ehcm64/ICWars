package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class ICWarsPlayer extends ICWarsActor implements Interactor {
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
        addAllUnits(units);
    }

    public void startTurn() {
        this.currentState = State.NORMAL;
        centerCamera();
        for (Unit unit : this.units) {
            unit.setMoveState(false);
            unit.setActionState(false);
        }
    }

    public State getPlayerState() {
        return this.currentState;
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public void addAllUnits(ArrayList<Unit> units) {
        for (Unit unit : units) {
            this.units.add(unit);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        removeDeadUnits();
        super.update(deltaTime);
    }

    public void setPlayerState(State state) {
        this.currentState = state;
    }

    private void removeDeadUnits() {
        ArrayList<Unit> unitsToRemove = new ArrayList<Unit>();
        for (Unit unit : this.units) {
            if (unit.getHp() == 0)
                unitsToRemove.add(unit);
        }
        if (unitsToRemove.size() != 0) {
            for (Unit unit : unitsToRemove) {
                this.units.remove(unit);
                this.getOwnerArea().unregisterActor(unit);
            }
        }
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
        for (Unit unit : this.units) {
            area.registerActor(unit);
        }
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());

    }

    @Override
    public void leaveArea() {
        for (Unit unit : units) {
            getOwnerArea().unregisterActor(unit);
        }
        getOwnerArea().unregisterActor(this);
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
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ICWarsInteractionVisitor) v).interactWith(this);
    }
}
