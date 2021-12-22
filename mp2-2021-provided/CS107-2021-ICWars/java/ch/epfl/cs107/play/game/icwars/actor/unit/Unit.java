package ch.epfl.cs107.play.game.icwars.actor.unit;

import java.util.ArrayList;
import java.util.Queue;

import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Unit extends ICWarsActor {
    protected int hp;
    protected int maxHp;
    protected String name;
    protected String spriteName;
    protected int damage;
    protected int radius;
    protected Sprite sprite;
    protected ICWarsRange range;
    protected boolean hasActed;
    protected boolean hasMoved;
    protected int cellDefStars;
    protected ArrayList<Action> actions;

    public Unit(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
        this.range = new ICWarsRange();
        addAllNodes();
        this.actions = new ArrayList<Action>();
        this.cellDefStars = ((ICWarsArea) this.getOwnerArea())
                .currentCellDefStars(this.getPosition().toDiscreteCoordinates());
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.hasMoved && !this.hasActed) {
            this.sprite.setAlpha(0.6f);
        } else if (this.hasActed && this.hasMoved) {
            this.sprite.setAlpha(0.3f);
        } else {
            this.sprite.setAlpha(1.f);
        }
        this.sprite.draw(canvas);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
        if (this.range.nodeExists(newPosition) && super.changePosition(newPosition)) {
            this.hasMoved = true;
            this.range = new ICWarsRange();
            this.addAllNodes();
            this.cellDefStars = ((ICWarsArea) this.getOwnerArea())
                    .currentCellDefStars(this.getPosition().toDiscreteCoordinates());
            return true;
        } else {
            this.hasMoved = false;
            return false;
        }
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ICWarsInteractionVisitor) v).interactWith(this);
    }

    /** Add all nodes in the unit's range of movement.
     * 
     */
    private void addAllNodes() {
        int fromX = (int) getPosition().getX();
        int fromY = (int) getPosition().getY();
        this.radius = getRadius();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x + fromX >= 0 && x + fromX < getOwnerArea().getWidth() && y + fromY >= 0
                        && y + fromY < getOwnerArea().getHeight()) {
                    DiscreteCoordinates coords = new DiscreteCoordinates(x + fromX, y + fromY);
                    boolean hasLeftEdge = x > -radius && x + fromX > 0;
                    boolean hasRightEdge = x < radius && x + fromX < getOwnerArea().getWidth();
                    boolean hasUpEdge = y < radius && y + fromY < getOwnerArea().getHeight();
                    boolean hasDownEdge = y > -radius && y + fromY > 0;
                    range.addNode(coords, hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
                }
            }
        }
    }

    /** Get the unit's movement radius.
     * 
     * @return (int): the radius.
     */
    public abstract int getRadius();

    /** Get the unit's name.
     * 
     * @return (String): the name.
     */
    public String getName() {
        return name;
    }

    /** Get the unit's hp.
     * 
     * @return (float): the unit's hp.
     */
    public int getHp() {
        return hp;
    }

    /** Set whether the unit has moved or not already.
     * 
     * @param state (boolean): the movement state in which to put the unit.
     */
    public void setMoveState(boolean state) {
        this.hasMoved = state;
    }

    /** Know whether the unit has moved yet or not.
     * 
     * @return (boolean): the state of movement of the unit.
     */
    public boolean getMoveState() {
        return this.hasMoved;
    }

    /** Set whether the unit has acted or not already.
     * 
     * @param state (boolean): the  action state in which to put the unit.
     */
    public void setActionState(boolean state) {
        this.hasActed = state;
    }

    /** Know whether the unit has acted yet or not.
     * 
     * @return (boolean): the state of action of the unit.
     */
    public boolean getActionState() {
        return this.hasActed;
    }

    /** Get the list of actions the unit can execute.
     * 
     * @return (ArrayList<Action>): the list of actions.
     */
    public ArrayList<Action> getActions() {
        return this.actions;
    }

    /** Draw the unit's range and a path from the unit position todestination
     * 
     * @param destination path destination
     * @param canvas      canvas
     */
    public void drawRangeAndPathTo(DiscreteCoordinates destination, Canvas canvas) {
        range.draw(canvas);
        Queue<Orientation> path = range.shortestPath(getCurrentMainCellCoordinates(), destination);
        // Draw path only if it exists (destination inside the range)
        if (path != null) {
            new Path(getCurrentMainCellCoordinates().toVector(), path).draw(canvas);
        }
    }

    /** Decrease the unit's hp by a certain amount.
     * 
     * @param damage (int): Amount by which to hurt the unit.
     */
    public void takeDamage(int damage) {
        if (damage > this.cellDefStars) {
            this.hp = this.hp - damage + this.cellDefStars;
        }
        if (this.hp < 0)
            this.hp = 0;
    }

    /** Increase the unit's hp by a certain amount.
     * 
     * @param heal (int): Amount by which to heal the unit.
     */
    public void repair(int heal) {
        this.hp += heal;
        if (this.hp > this.maxHp)
            this.hp = this.maxHp;
    }

    /** Get the amount of damage this unit deals to enemies.
     * 
     * @return (int): the amount of damage.
     */
    public abstract int getDamage();

    /** Get the closest enemy unit.
     * 
     * @return (Unit): the closest enemy unit.
     */
    public Unit getClosestEnemyUnit() {
        Unit closestEnemyUnit = null;
        int range = 0;
        if (this.getOwnerArea().getHeight() > this.getOwnerArea().getWidth()) {
            range = this.getOwnerArea().getHeight();
        } else {
            range = this.getOwnerArea().getWidth();
        }
        ArrayList<Unit> closeUnits = ((ICWarsArea) getOwnerArea())
                .findCloseUnits(this.getPosition().toDiscreteCoordinates(), range);
        ArrayList<Unit> closeEnemyUnits = new ArrayList<Unit>();
        for (Unit unit : closeUnits) {
            if (unit.getFaction() != this.getFaction()) {
                closeEnemyUnits.add(unit);
            }
        }
        if (closeEnemyUnits.size() != 0) {
            closestEnemyUnit = closeEnemyUnits.get(0);
            float distance = (float) Math.sqrt(900);
            for (Unit enemyUnit : closeEnemyUnits) {
                float unitsDistance = Vector.getDistance(this.getPosition(), enemyUnit.getPosition());
                if (unitsDistance < distance
                        || (unitsDistance == distance && enemyUnit.getHp() < closestEnemyUnit.getHp())) {
                    closestEnemyUnit = enemyUnit;
                    distance = unitsDistance;
                }
            }
        }
        return closestEnemyUnit;
    }

    /** Allow the unit to move automatically towards the closest enemy unit.
     * 
     */
    public void moveToClosestEnemyUnit() {
        Unit closestEnemyUnit = this.getClosestEnemyUnit();
        boolean inRange = true;
        if (closestEnemyUnit != null) {
            Vector enemyPos = closestEnemyUnit.getPosition();
            float xDelta = enemyPos.getX() - this.getPosition().getX();
            float yDelta = enemyPos.getY() - this.getPosition().getY();
            if (xDelta > this.getRadius()) {
                xDelta = this.getRadius();
                inRange = false;
            } else if (xDelta < -this.getRadius()) {
                xDelta = -this.getRadius();
                inRange = false;
            }
            if (yDelta > this.getRadius()) {
                yDelta = this.getRadius();
                inRange = false;
            } else if (yDelta < -this.getRadius()) {
                yDelta = -this.getRadius();
                inRange = false;
            }

            if (inRange) {
                Vector toEnemy = new Vector(xDelta, yDelta);
                Vector newPos = this.getPosition().add(toEnemy);
                DiscreteCoordinates newPosition = newPos.toDiscreteCoordinates();
                ArrayList<DiscreteCoordinates> neighboursCoords = (ArrayList<DiscreteCoordinates>) newPosition
                        .getNeighbours();
                for (DiscreteCoordinates neighbour : neighboursCoords) {
                    if (this.changePosition(neighbour)) {
                        break;
                    }
                }
            } else {
                Vector toEnemy = new Vector(xDelta, yDelta);
                Vector newPos = this.getPosition().add(toEnemy);
                DiscreteCoordinates newPosition = newPos.toDiscreteCoordinates();
                this.changePosition(newPosition);
            }
        }
    }
}