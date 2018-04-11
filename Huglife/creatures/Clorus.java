package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {

    public static final String NAME = "clorus";
    /**
     * Creates a creature with the name N. The intention is that this
     * name should be shared between all creatures of the same type.
     *
     *
     */
    public Clorus(double e) {
        super("clorus");
        this.energy = e;
    }

    /**
     * Called when this creature moves.
     */
    @Override
    public void move() {
        energy -= 0.03;
    }

    /**
     * Called when this creature attacks C.
     *
     * @param c
     */
    @Override
    public void attack(Creature c) {
        this.energy += c.energy();
    }

    /**
     * Called when this creature chooses replicate.
     * Must return a creature of the same type.
     */
    @Override
    public Clorus replicate() {
        this.energy/= 2.0;
        return new Clorus(this.energy);
    }

    /**
     * Called when this creature chooses stay.
     */
    @Override
    public void stay() {
        energy -= 0.01;
    }

    /**
     * Returns an action. The creature is provided information about its
     * immediate NEIGHBORS with which to make a decision.
     *
     * @param neighbors
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        //1. If no empty adjacent spaces, STAY.
        if (empties.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        //2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
        List<Direction> plips = getNeighborsOfType(neighbors, Plip.NAME);
        if (!plips.isEmpty()) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plips));
        }

        //3. Otherwise, if the Clorus has energy greater than or equal to one,
        // it will REPLICATE to a random empty square.
        if (this.energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(empties));
        }

        //4. Otherwise, if nothing else, MOVE
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(empties));
    }

    /**
     * Required method that returns a color.
     */
    @Override
    public Color color() {
        return new Color(34, 0, 231);
    }
}
