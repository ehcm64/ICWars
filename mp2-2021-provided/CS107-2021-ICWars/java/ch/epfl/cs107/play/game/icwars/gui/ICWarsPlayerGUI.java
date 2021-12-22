package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.State;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior.ICWarsCellType;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics {
    public static final float FONT_SIZE = 21.5f;
    private ICWarsPlayer player;
    private Unit playerSelectedUnit;
    private Unit viewedUnit;
    private ICWarsCellType cellType;
    private ICWarsActionsPanel actionPanel;
    private ICWarsInfoPanel infoPanel;

    public ICWarsPlayerGUI(float cameraScaleFactor, ICWarsPlayer player) {
        this.player = player;
        this.actionPanel = new ICWarsActionsPanel(cameraScaleFactor);
        this.infoPanel = new ICWarsInfoPanel(cameraScaleFactor);
    }

    /** Set (in the gui) the selected unit by the player to be drawn.
     * 
     * @param selectedUnit (Unit): the unit selected by the player.
     */
    public void setPlayerSelectedUnit(Unit selectedUnit) {
        this.playerSelectedUnit = selectedUnit;
    }

    /** Set (in the gui) the unit which is being viewed by the player.
     * 
     * @param viewedUnit (Unit): the unit being viewed by the player.
     */
    public void setViewedUnit(Unit viewedUnit) {
        this.viewedUnit = viewedUnit;
    }

    /** Set (in the gui) the type of the cell being viewed by the player.
     * 
     * @param type (ICWarsCellType): the type of the cell.
     */
    public void setCellType(ICWarsCellType type) {
        this.cellType = type;
    }

    @Override
    public void draw(Canvas canvas) {
        if (player.getPlayerState() == State.MOVE_UNIT && this.playerSelectedUnit != null
                && !this.playerSelectedUnit.getMoveState()) {

            DiscreteCoordinates coords = this.player.getPosition().toDiscreteCoordinates();
            this.playerSelectedUnit.drawRangeAndPathTo(coords, canvas);

        } else if (player.getPlayerState() == State.ACTION_SELECTION && this.playerSelectedUnit != null) {

            this.actionPanel.setActions(this.playerSelectedUnit.getActions());
            this.actionPanel.draw(canvas);

        } else if (player.getPlayerState() == State.NORMAL || player.getPlayerState() == State.SELECT_CELL) {

            if (this.viewedUnit != null) {

                this.infoPanel.setUnit(this.viewedUnit);

            } else {
                this.infoPanel.setUnit(null);
            }
            this.infoPanel.setCurrentCell(this.cellType);
            this.infoPanel.draw(canvas);
            this.infoPanel.setUnit(null);
            this.infoPanel.setCurrentCell(null);
        }
    }
}
