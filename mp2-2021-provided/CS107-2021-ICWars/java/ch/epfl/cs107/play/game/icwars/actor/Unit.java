package ch.epfl.cs107.play.game.icwars.actor;

import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Unit extends ICWarsActor {
    //TODO 2.2.1
    protected float hp;
    protected float maxHp;
    protected String name;
    protected float damage;
    protected int radius;
    protected Sprite sprite;
    protected ICWarsRange range;

    public Unit(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
        range = new ICWarsRange();
        addAllNodes();
    }

    private void addAllNodes() {
        int fromX = (int)getPosition().getX();
        int fromY = (int)getPosition().getY();
        this.radius = getRadius();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x + fromX >= 0 && x + fromX <= getOwnerArea().getWidth() && y + fromY >= 0 && y + fromY <= getOwnerArea().getWidth()){
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

    public String getName() {
        return name;
    }

    public float getHp() {
        return hp;
    }

    /**
     * Draw the unit's range and a path from the unit position todestination
     * @param destination path destination
     * @param canvas canvas
     */
    public void drawRangeAndPathTo(DiscreteCoordinates destination , Canvas canvas) {
        range.draw(canvas);
        Queue <Orientation > path = range.shortestPath(getCurrentMainCellCoordinates(), destination);
        //Draw path only if it exists (destination inside the range)
        if (path != null){
            new Path(getCurrentMainCellCoordinates().toVector(), path).draw(canvas);
        }
    }

    public float takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
        return hp;
    }

    public float repair(int heal) {
        hp += heal;
        if (hp > maxHp) hp = maxHp;
        return hp;
    }

    public abstract float getDamage();

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
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
