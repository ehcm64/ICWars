package ch.epfl.cs107.play.game.icwars.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class ICWarsActor extends MovableAreaEntity{
    
    private Faction faction;
    protected static Orientation orientation = Orientation.UP;

    public enum Faction{
        ALLY,
        ENNEMY;
 
    }
    
    public ICWarsActor(Area area, DiscreteCoordinates position, Faction faction){
        super(area, orientation, position);
        this.faction = faction;

    }

    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }

    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
	}

}
