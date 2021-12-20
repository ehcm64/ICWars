package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
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
        this.name = "Soldier";
        if (faction.equals(Faction.ALLY)) {
            this.spriteName = "icwars/friendlySoldier";
        } else {
            this.spriteName = "icwars/enemySoldier";
        }
            
        sprite = new Sprite(this.spriteName, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        Action attack = new Attack(this, area);
        Action wait = new Wait(this, area);
        this.actions.add(attack);
        this.actions.add(wait);
    }

    @Override
    public int getRadius() {
        return 2;
    }

    @Override
    public int getDamage() {
        return 2;
    }
}