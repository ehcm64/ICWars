package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Unit extends ICWarsActor {
    //TODO 2.2.1
    protected float hp;
    protected float maxHp;
    protected String name;
    protected String spriteName;
    protected float damage;
    protected int moveRadius;
    protected Sprite sprite;
    private DiscreteCoordinates coordinates;

    public Unit(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
        this.coordinates = position;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public String getName() {
        return name;
    }

    public float getHp() {
        return hp;
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

    public abstract int getDamage();

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    public DiscreteCoordinates getCoordinates() {
        return coordinates;
    }
}
