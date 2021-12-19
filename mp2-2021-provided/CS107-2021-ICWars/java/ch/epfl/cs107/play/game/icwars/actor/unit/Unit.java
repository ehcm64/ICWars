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
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Unit extends ICWarsActor {
    protected int hp;
    protected int maxHp;
    protected String name;
    protected int damage;
    protected int radius;
    protected Sprite sprite;
    protected ICWarsRange range;
    protected boolean hasAttacked;
    protected boolean hasMoved;
    protected int cellDefStars;
    protected ArrayList<Action> actions;

    public Unit(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
        this.range = new ICWarsRange();
        addAllNodes();
        this.actions = new ArrayList<Action>();
    }

    private void addAllNodes() {
        int fromX = (int) getPosition().getX();
        int fromY = (int) getPosition().getY();
        this.radius = getRadius();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x + fromX >= 0 && x + fromX < getOwnerArea().getWidth() && y + fromY >= 0
                        && y + fromY < getOwnerArea().getWidth()) {
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

    public abstract int getRadius();

    public Sprite getSprite() {
        return this.sprite;
    }

    public String getName() {
        return name;
    }

    public float getHp() {
        return hp;
    }

    public void setMoveState(boolean state) {
        this.hasMoved = state;
    }

    public boolean getMoveState() {
        return this.hasMoved;
    }

    public void setAttackState(boolean state) {
        this.hasAttacked = state;
    }

    public boolean getAttackState() {
        return this.hasAttacked;
    }

    public ArrayList<Action> getActions() {
        return this.actions;
    }

    /**
     * Draw the unit's range and a path from the unit position todestination
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

    public float takeDamage(int damage) {
        hp = hp - damage + this.cellDefStars;
        if (hp < 0)
            hp = 0;
        return hp;
    }

    public float repair(int heal) {
        hp += heal;
        if (hp > maxHp)
            hp = maxHp;
        return hp;
    }

    public abstract int getDamage();

    @Override
    public void draw(Canvas canvas) {
        if (this.hasMoved) {
            sprite.setAlpha(0.5f);
        } else {
            sprite.setAlpha(1.f);
        }
        sprite.draw(canvas);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
        // TODO Change Position INCOMPLETE ?
        if (this.range.nodeExists(newPosition) && super.changePosition(newPosition)) {
            this.hasMoved = true;
            this.range = new ICWarsRange();
            this.addAllNodes();
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
}