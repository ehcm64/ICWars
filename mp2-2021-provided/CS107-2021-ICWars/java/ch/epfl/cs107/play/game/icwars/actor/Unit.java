package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Unit extends ICWarsActor {
    private int hp;
    private int maxHp;
    private String name;
    private String spriteName;
    private int damage;
    private int moveRadius;
    private Sprite sprite;

    public Unit(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
        sprite = new Sprite(spriteName, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        //TODO Auto-generated constructor stub
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        } 
        return hp;
    }

    public int repair(int heal) {
        hp += heal;
        if (hp > maxHp) {
            hp = maxHp;
        }
        return hp;
    }

    public abstract int getDamage();

    @Override
    public boolean takeCellSpace() {
        return true;
    }
    
}
