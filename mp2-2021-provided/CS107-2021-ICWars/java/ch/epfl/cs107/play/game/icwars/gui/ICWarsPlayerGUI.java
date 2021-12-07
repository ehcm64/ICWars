package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;

public class ICWarsPlayerGUI implements Graphics {
    private ICWarsPlayer player;

    public ICWarsPlayerGUI (float cameraScaleFactor, ICWarsPlayer player) {
        this.player = player;
    }
    
    @Override
    public void draw(Canvas canvas) {
        int x = (int)this.player.getPosition().getX();
        int y = (int)this.player.getPosition().getY();
        DiscreteCoordinates coords = new DiscreteCoordinates(x, y);
        if (((RealPlayer)this.player).getSelectedUnit() != null) {
            ((RealPlayer)this.player).getSelectedUnit().drawRangeAndPathTo(coords, canvas);
        }
    }
}
