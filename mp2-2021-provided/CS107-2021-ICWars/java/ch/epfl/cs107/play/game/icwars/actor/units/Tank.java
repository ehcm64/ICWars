package ch.epfl.cs107.play.game.icwars.actor.units;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;

public class Tank extends Unit {
    private float maxHp = 10;


    public Tank(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
        this.moveRadius = 4;
        this.damage = 7;
        this.maxHp = 10;
        this.hp = this.maxHp;
        if (faction.equals(Faction.ALLY)) this.name = "icwars/friendlyTank";
        else this.name = "icwars/enemyTank";
        sprite = new Sprite(this.name, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        //TODO Auto-generated constructor stub
    }

    @Override
    public int getDamage() {
        // TODO Auto-generated method stub
        return 0;
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
