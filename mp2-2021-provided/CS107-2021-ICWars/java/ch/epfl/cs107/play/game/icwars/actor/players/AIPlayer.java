package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class AIPlayer extends ICWarsPlayer {

    public AIPlayer(Area area, DiscreteCoordinates position, Faction faction, ArrayList<Unit> units) {
        super(area, position, faction, units);
        //TODO Auto-generated constructor stub
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

    @Override
    public void interactWith(Interactable other) {
        // TODO Auto-generated method stub
        
    }
    
}
