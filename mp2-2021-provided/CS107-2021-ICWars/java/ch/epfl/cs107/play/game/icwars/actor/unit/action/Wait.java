package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.State;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
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
    public void doAction(float dt, RealPlayer player, Keyboard keyboard) {
        this.unit.repair(1);
        this.unit.setActionState(true);
        player.setPlayerState(State.NORMAL);
    }

    @Override
    public void doAutoAction(float dt, AIPlayer player, boolean wait) {
        this.unit.repair(1);
        this.unit.setActionState(true);
        player.setPlayerState(State.NORMAL);
    }
}