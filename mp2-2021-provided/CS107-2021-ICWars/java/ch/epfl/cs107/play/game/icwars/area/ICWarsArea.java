package ch.epfl.cs107.play.game.icwars.area;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior.ICWarsCellType;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICWarsArea extends Area {

    private ICWarsBehavior behavior;

    @Override
    public final float getCameraScaleFactor() {
        return ICWars.CAMERA_SCALE_FACTOR;
    }

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

    /** Get the spawn position of the AllyPlayer on the area.
     * 
     * @return (DiscreteCoordinates): the spawn position.
     */
    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    /** Get the spawn position of the EnemyPlayer on the area.
     * 
     * @return (DiscreteCoordinates): the spawn position.
     */
    public abstract DiscreteCoordinates getEnemySpawnPosition();

    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected abstract void createArea();

    /** Get the list of all close units in a certain range no matter which faction.
     * 
     */
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

    /** Get the list of all units registered in the area no matter which faction.
     * 
     * @return (ArrayList<Unit>): the list of units.
     */
    public ArrayList<Unit> getAllUnits() {
        ArrayList<Unit> allUnits = new ArrayList<Unit>();
        for (Actor actor : this.getActors()) {
            if (actor instanceof Unit) {
                allUnits.add((Unit)actor);
            }
        }
        return allUnits;
    }

    /** Get the type of a cell by knowing its coordinates.
     * 
     * @param coords (DiscreteCoordinates): the coordinates of the cell.
     * @return (ICWarsCellType): the type of the cell.
     */
    public ICWarsCellType currentCellType(DiscreteCoordinates coords) {
        int x = coords.x;
        int y = coords.y;
        
        ICWarsCellType type = this.behavior.getCellType(x, y);
        return type;
    }

    /** Get the defense stars of a cell by knowing its coordinates.
     * 
     * @param coords (DiscreteCoordinates): the coordinates of the cell.
     * @return (int): the defense stars of the cell.
     */
    public int currentCellDefStars(DiscreteCoordinates coords) {
        int x = coords.x;
        int y = coords.y;

        ICWarsCellType type = this.behavior.getCellType(x, y);
        return type.getDefenseStar();
    }
}
