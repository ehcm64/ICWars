package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class Action implements Graphics {
    protected String name;
    protected int key;
    protected Unit unit;
    protected ICWarsArea area;
    
    public Action (Unit unit, Area area) {
        this.unit = unit;
        this.area = (ICWarsArea) area;
    }

    /** Get the keyboard key that activates the action.
     * 
     * @return (int): the key.
     */
    public int getKey() {
        return this.key;
    }

    /** Execute the action of the RealPlayer.
     * 
     * @param dt (float): Time to elapse.
     * @param player (RealPlayer): the player doing the action.
     * @param keyboard (Keyboard): the player's keyboard for input.
     */
    public abstract void doAction(float dt, RealPlayer player, Keyboard keyboard);

    /** Execute automatically the action of the AIPlayer.
     * 
     * @param dt (float): Time to elapse.
     * @param player (AIPlayer): the AI doing the action.
     * @param wait (boolean): whether to wait or not before acting so the action sprite can be displayed.
     */
    public abstract void doAutoAction(float dt, AIPlayer player, boolean wait);

    /** Get the action's name.
     * 
     * @return (String): the action's name.
     */
    public String getName() {
        return this.name;
    }
}
