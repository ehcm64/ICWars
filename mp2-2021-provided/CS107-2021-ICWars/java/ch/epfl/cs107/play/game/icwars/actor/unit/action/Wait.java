package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.State;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Wait extends Action {

    public Wait(Unit unit, Area area) {
        super(unit, area);
        this.name = "(W)ait";
        this.key = Keyboard.W;
       
    }

    @Override
    public void draw(Canvas canvas) { 
    }

    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
        this.unit.setAttackState(true);
        player.setPlayerState(State.NORMAL);
    }
}

    
