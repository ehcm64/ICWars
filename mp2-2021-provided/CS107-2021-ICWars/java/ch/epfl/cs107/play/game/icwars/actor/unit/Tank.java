package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;

public class Tank extends Unit {

    public Tank(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, faction);
        this.radius = 4;
        this.damage = 7;
        this.maxHp = 10;
        this.hp = this.maxHp;
        if (faction.equals(Faction.ALLY)) {
            this.spriteName = "icwars/friendlyTank";
            this.name = "Ally Tank";
        } else {
            this.spriteName = "icwars/enemyTank";
            this.name = "Enemy Tank";
        }
        sprite = new Sprite(this.spriteName, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        this.actions.add(new Attack(this, area));
        this.actions.add(new Wait(this, area));
        
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getRadius() {
        return 4;
    }

    @Override
    public int getDamage() {
        return 7;
    }
}
