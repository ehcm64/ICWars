package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics {
    private ICWarsPlayer player;
    private Unit playerSelectedUnit;

    public ICWarsPlayerGUI (float cameraScaleFactor, ICWarsPlayer player) {
        this.player = player;
    }

    public void setPlayerSelectedUnit (Unit selectedUnit) {
        this.playerSelectedUnit = selectedUnit;
    }
    
    @Override
    public void draw(Canvas canvas) {
        int x = (int)this.player.getPosition().getX();
        int y = (int)this.player.getPosition().getY();
        DiscreteCoordinates coords = new DiscreteCoordinates(x, y);
        this.playerSelectedUnit.drawRangeAndPathTo(coords, canvas);
    }
}
