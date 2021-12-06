package ch.epfl.cs107.play.game.icwars.actor;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Unit extends ICWarsActor {
    //TODO 2.2.1
    ArrayList<Unit> units;
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
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public void enterArea(Area area, DiscreteCoordinates position) {
	    area.registerActor(this);
        setOwnerArea(area);
	    setCurrentPosition(position.toVector());
	   
	}

    @Override
	public void leaveArea(){
        for (Unit unit: units) {
            getOwnerArea().unregisterActor(unit);
        }
	    getOwnerArea().unregisterActor(this);
	}

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    public boolean isDefeated() {
        if (units.size() == 0) {
            return true;
        }
        return false;
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

    public DiscreteCoordinates getCoordinates() {
        return coordinates;
    }
    
}
