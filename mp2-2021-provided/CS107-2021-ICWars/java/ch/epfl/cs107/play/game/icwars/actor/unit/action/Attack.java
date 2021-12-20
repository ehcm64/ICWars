package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.State;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Attack extends Action {
    Unit unitToAttack;
    int unitIndex;
    ImageGraphics cursor;

    public Attack(Unit unit, Area area) {
        super(unit, area);
        this.name = "(A)ttack";
        this.key = Keyboard.A;
        this.unitIndex = 0;
        this.unitToAttack = null;
    }

    @Override
    public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
        Faction unitFaction = this.unit.getFaction();
        ArrayList<Unit> unitsInAttackRange = this.area.findCloseUnits(this.unit.getPosition().toDiscreteCoordinates(), 1 );
        ArrayList<Unit> enemyUnitsinAttackRange = new ArrayList<Unit>();
        for (Unit unit : unitsInAttackRange) {
            if (unit.getFaction() != unitFaction) {
                enemyUnitsinAttackRange.add(unit);
            }
        }

        if (enemyUnitsinAttackRange.size() == 0 || keyboard.get(Keyboard.TAB).isReleased()) {
            player.centerCamera();
            player.setPlayerState(State.ACTION_SELECTION);
        } else {
            this.unitToAttack = enemyUnitsinAttackRange.get(this.unitIndex);
            if (keyboard.get(Keyboard.LEFT).isReleased()) {
                if (this.unitIndex == 0) {
                    this.unitIndex = enemyUnitsinAttackRange.size() - 1;
                } else {
                    this.unitIndex -= 1;
                }
            } else if (keyboard.get(Keyboard.RIGHT).isReleased()) {
                if (this.unitIndex == enemyUnitsinAttackRange.size() - 1) {
                    this.unitIndex = 0;
                } else {
                    this.unitIndex += 1;
                }
            }
            this.unitToAttack = enemyUnitsinAttackRange.get(this.unitIndex);

            if (keyboard.get(Keyboard.ENTER).isReleased()) {
                this.unitToAttack.takeDamage(this.unit.getDamage());
                this.unit.setActionState(true);
                this.unitToAttack = null;
                this.unitIndex = 0;
                player.centerCamera();
                player.setPlayerState(State.NORMAL);
            }
        }
    }

    @Override
    public void doAutoAction(float dt, ICWarsPlayer player, boolean wait) {
        Faction unitFaction = this.unit.getFaction();
        ArrayList<Unit> unitsInAttackRange = this.area.findCloseUnits(this.unit.getPosition().toDiscreteCoordinates(), 1 );
        ArrayList<Unit> enemyUnitsinAttackRange = new ArrayList<Unit>();
        for (Unit unit : unitsInAttackRange) {
            if (unit.getFaction() != unitFaction) {
                enemyUnitsinAttackRange.add(unit);
            }
        }
        if (enemyUnitsinAttackRange.size() != 0) {
            this.unitToAttack = this.unit.getClosestEnemyUnit();
            if (Vector.getDistance(this.unit.getPosition(), this.unitToAttack.getPosition()) <= this.unit.getRadius() && !wait) {
                this.unitToAttack.takeDamage(this.unit.getDamage());
                this.unit.setActionState(true);
                this.unitToAttack = null;
                player.setPlayerState(State.NORMAL);
            } 
        }
    }

    @Override
    public void draw(Canvas canvas) {
         if (this.unitToAttack != null ) {
            this.cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1.f, 1.f,
                new RegionOfInterest(4 * 18, 26 * 18, 16, 16));
            this.unitToAttack.centerCamera();
            this.cursor.setAnchor(this.unitToAttack.getPosition().add(1, 0));
            this.cursor.draw(canvas);
        }
    }
}
