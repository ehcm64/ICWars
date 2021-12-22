package ch.epfl.cs107.play.game.icwars.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class ICWarsActor extends MovableAreaEntity {

    protected Faction faction;
    protected static Orientation orientation = Orientation.UP;

    public enum Faction {
        ALLY,
        ENEMY,
        GAMEOVER;
    }

    public ICWarsActor(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, orientation, position);
        this.faction = faction;

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
    }

    /** Center the camera on the actor.
     * 
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    /** Allow the actor to enter the current area.
     * 
     * @param area (Area): area in which to register the actor.
     * @param position (DiscreteCoordinates): the actor's position.
     */
    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }

    /** Allow the actor to leave the current area.
     * 
     */
    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }

    /** Get the actor's faction.
     * 
     * @return (Faction): the actor's faction.
     */
    public Faction getFaction() {
        return this.faction;
    }
}
