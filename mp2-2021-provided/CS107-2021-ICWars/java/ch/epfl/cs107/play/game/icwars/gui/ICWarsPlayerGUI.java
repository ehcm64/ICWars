package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.State;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior.ICWarsCellType;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics {
    public static final float FONT_SIZE = 10.5f;
    private ICWarsPlayer player;
    private Unit playerSelectedUnit;
    private ICWarsActionsPanel actionPanel;
    private ICWarsInfoPanel infoPanel;

    public ICWarsPlayerGUI (float cameraScaleFactor, ICWarsPlayer player) {
        this.player = player;
        this.actionPanel = new ICWarsActionsPanel(cameraScaleFactor);
        this.infoPanel = new ICWarsInfoPanel(cameraScaleFactor);
    }

    public void setPlayerSelectedUnit (Unit selectedUnit) {
        this.playerSelectedUnit = selectedUnit;
    }
    
    @Override
    public void draw(Canvas canvas) {
        if (player.getPlayerState() == State.MOVE_UNIT && this.playerSelectedUnit != null && !this.playerSelectedUnit.getMoveState()) {
            DiscreteCoordinates coords = this.player.getPosition().toDiscreteCoordinates();
            this.playerSelectedUnit.drawRangeAndPathTo(coords, canvas);
        } else if (player.getPlayerState() == State.ACTION_SELECTION && this.playerSelectedUnit != null) {
            this.actionPanel.setActions(this.playerSelectedUnit.getActions());
            this.actionPanel.draw(canvas);
        } else if (player.getPlayerState() == State.NORMAL || player.getPlayerState() == State.SELECT_CELL) {
            // TODO TEMPORARY TEST
            this.infoPanel.setUnit(this.playerSelectedUnit);
            //TODO TEMPORARY TEST
            this.infoPanel.setCurrentCell(ICWarsCellType.ROAD );
            this.infoPanel.draw(canvas);
        }
    }
}
