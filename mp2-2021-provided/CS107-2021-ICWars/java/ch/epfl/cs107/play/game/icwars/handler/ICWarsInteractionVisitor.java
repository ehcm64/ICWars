package ch.epfl.cs107.play.game.icwars.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;

public interface ICWarsInteractionVisitor extends AreaInteractionVisitor {

    /** Execute the interaction with a RealPlayer.
     * 
     * @param other (RealPlayer): the RealPlayer we interact with.
     */
    default void interactWith(RealPlayer other) {
    }

    /** Execute the interaction with a Unit.
     * 
     * @param other (Unit): the Unit we interact with.
     */
    default void interactWith(Unit other) {
    }
}
