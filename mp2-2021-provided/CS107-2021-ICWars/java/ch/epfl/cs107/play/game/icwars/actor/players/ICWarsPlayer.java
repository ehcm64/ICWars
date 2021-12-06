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
    ArrayList<Unit> units;
    Sprite sprite;
    String spriteName;

    public ICWarsPlayer(Area area, DiscreteCoordinates position, Faction faction, ArrayList<Unit> units) {
        super(area, position, faction);
        this.units = units;
        
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
	    area.registerActor(this);
        setOwnerArea(area);
	    setCurrentPosition(position.toVector());
	
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
        if (units.size() == 0) {
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
