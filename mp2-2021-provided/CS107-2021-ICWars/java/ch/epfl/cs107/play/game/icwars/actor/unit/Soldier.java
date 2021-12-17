package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;

public class Soldier extends Unit {

    public Soldier(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
        this.radius = 2;
        this.damage = 2;
        this.maxHp = 5;
        this.hp = this.maxHp;
        if (faction.equals(Faction.ALLY))
            this.name = "icwars/friendlySoldier";
        else
            this.name = "icwars/enemySoldier";
        sprite = new Sprite(this.name, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        this.actions.add(new Attack(this, area));
        this.actions.add(new Wait(this, area));
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getRadius() {
        return 2;
    }

    @Override
    public int getDamage() {
        // TODO Auto-generated method stub
        return 0;
    }
}