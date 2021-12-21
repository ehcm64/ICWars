package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class AIPlayer extends ICWarsPlayer {
    int unitIndex;
    float dt;

    public AIPlayer(Area area, DiscreteCoordinates position, Faction faction, ArrayList<Unit> units) {
        super(area, position, faction, units);
        this.name = "icwars/enemyCursor";
        this.sprite = new Sprite(this.name, 1.f, 1.f, this, null, new Vector(0f, 0f));
        this.act = null;
    }

    @Override
    public void update(float deltaTime) {
        if (this.selectedUnit != null) {
            this.changePosition(this.selectedUnit.getPosition().toDiscreteCoordinates());
            this.centerCamera();
        }
        switch(this.currentState) {
            case IDLE:
                this.unitIndex = -1;
                if (this.turnStarting) {
                    this.currentState = State.NORMAL;
                } 
                break;
            case NORMAL:
                this.dt = 0;
                this.turnStarting = false;
                this.unitIndex++;
                if (this.unitIndex < this.units.size()) {
                    this.selectedUnit = this.units.get(unitIndex);
                    this.currentState = State.MOVE_UNIT;
                } else {
                    this.currentState = State.IDLE;
                }
                break;
            case MOVE_UNIT:
                this.dt += 0.1f;
                if (waitFor(3, this.dt)) {
                    this.selectedUnit.moveToClosestEnemyUnit();
                    if (this.selectedUnit != null) {
                        this.act = this.selectedUnit.getActions().get(0);                            
                        this.currentState = State.ACTION;
                        this.dt = 0;
                    }
                }        
                break;
            case ACTION:
                this.dt += 0.1f;
                this.act.doAutoAction(deltaTime, this, true);
                if (waitFor(4, this.dt)) {
                    this.act.doAutoAction(deltaTime, this, false);
                    if (!this.selectedUnit.getActionState()) {
                        this.act = this.selectedUnit.getActions().get(1);
                        this.act.doAutoAction(deltaTime, this, false);
                        this.dt = 0;
                    }
                    this.currentState = State.NORMAL;
                    this.selectedUnit = null;
                }
                break;
            default:
                break;
        }
        super.update(deltaTime);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return new ArrayList<DiscreteCoordinates>();
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {
    }

    @Override
    public void draw(Canvas canvas) {
        if  (this.currentState != State.IDLE) {
            super.draw(canvas);
        }
        if  (this.currentState == State.ACTION) {
            this.act.draw(canvas);
        }
    }

    /**
     * Ensures that value time elapsed before returning true
     * @param dt    elapsed time
     * @param value waiting time (in seconds)
     * @return true if value seconds has elapsed , false otherwise
     */
    private boolean waitFor(float value, float dt) {
        boolean counting = true;
        float counter = 0f;
        if (counting) {
            counter += dt;
            if (counter > value) {
                counting = false;
                return true;
            }
        } else {
            counter = 0f;
            counting = true;
        }
        return false;
    }
}
