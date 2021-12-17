package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class Action extends GraphicsEntity {
    protected String name;
    protected int key;
    protected Unit unit;
    protected ICWarsArea area;
    
    public Action (Unit unit, Area area) {
        super(unit.getPosition(), unit.getSprite());
        this.unit = unit;
        this.area = (ICWarsArea) area;
    }

    public int getKey() {
        return this.key;
    }

    public abstract void doAction(float dt, ICWarsPlayer player, Keyboard keyboard);

    public String getName() {
        return this.name;
    }
}
