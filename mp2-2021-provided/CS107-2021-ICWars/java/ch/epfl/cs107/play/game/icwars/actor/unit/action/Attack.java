package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;

public class Attack extends Action {

    public Attack(Unit unit, Area area) {
        super(unit, area);
        this.name = "(A)ttack";
        this.key = Keyboard.A;
    }

    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
        Faction unitFaction = this.unit.getFaction();
        ArrayList<Unit> unitsInAttackRange = new ArrayList<Unit>();   
        ArrayList<DiscreteCoordinates> neighboursCoords = (ArrayList<DiscreteCoordinates>) unit.getPosition().toDiscreteCoordinates().getNeighbours();
        
    }
}
