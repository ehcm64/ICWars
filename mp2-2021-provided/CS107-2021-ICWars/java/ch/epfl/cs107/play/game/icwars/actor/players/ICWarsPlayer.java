package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayer extends ICWarsActor{
    //TODO 2.2.2
    protected ArrayList<Unit> units = new ArrayList<Unit>();
    protected Sprite sprite;
    protected String name;

    public ICWarsPlayer(Area area, DiscreteCoordinates position, Faction faction, ArrayList<Unit> units) {
        super(area, position, faction);
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
        ArrayList<Unit> unitsToRemove = new ArrayList<Unit>();
        for (Unit unit : this.units) {
            if (unit.getHp() == 0) unitsToRemove.add(unit);
        }
        if (unitsToRemove.size() != 0) {
            for (Unit unit : unitsToRemove) {
                this.units.remove(unit);
            }
        }
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
