package ch.epfl.cs107.play.game.icwars.area;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICWarsArea extends Area {

    private ICWarsBehavior behavior;

    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected abstract void createArea();

    /// EnigmeArea extends Area

    @Override
    public final float getCameraScaleFactor() {
        return ICWars.CAMERA_SCALE_FACTOR;
    }

    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    public abstract DiscreteCoordinates getEnemySpawnPosition();

    /// Demo2Area implements Playable

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICWarsBehavior(window, getTitle());
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }

    public ArrayList<Unit> findCloseUnits(DiscreteCoordinates coords, int range) {
        ArrayList<Unit> unitsInRange = new ArrayList<Unit>();
        int xPos = coords.x;
        int yPos = coords.y;
        for (Actor actor : this.getActors()) {
            if (actor instanceof Unit) {
                int x = (int) actor.getPosition().getX();
                int y = (int) actor.getPosition().getY();
                if (Math.abs(xPos -x) <= range && Math.abs(yPos - y) <= range) {
                    unitsInRange.add((Unit)actor);
                }
            }
        }
        return unitsInRange;
    }
}
