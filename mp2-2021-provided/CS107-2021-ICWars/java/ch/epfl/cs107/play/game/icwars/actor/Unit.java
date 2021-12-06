package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Unit extends ICWarsActor {
    //TODO 2.2.1
    protected float hp;
    protected float maxHp;
    protected String name;
    protected float damage;
    protected int moveRadius;
    protected Sprite sprite;

    public Unit(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
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
