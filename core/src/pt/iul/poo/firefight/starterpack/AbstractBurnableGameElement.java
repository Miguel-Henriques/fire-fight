package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.actors.Fireman;
import pt.iul.poo.firefight.starterpack.behaviours.IActiveElement;
import pt.iul.poo.firefight.starterpack.behaviours.IBurnable;
import pt.iul.poo.firefight.starterpack.behaviours.IUpdatable;
import pt.iul.poo.firefight.starterpack.props.Plane;

public abstract class AbstractBurnableGameElement extends AbstractGameElement
        implements IBurnable, IActiveElement, IUpdatable {

    private boolean isBurning;
    private int burningFor;

    public AbstractBurnableGameElement(Point2D position) {
        super(position);
        isBurning = false;
    }

    public AbstractBurnableGameElement(Point2D position, boolean isBurning) {
        super(position);
        this.isBurning = isBurning;
    }

    @Override
    public boolean isBurning() {
        return isBurning;
    }

    @Override
    public void setOnFire() {
        isBurning = true;
        burningFor = getDefaultBurningFor();
    }

    @Override
    public int getCurrentBurningFor() {
        return burningFor;
    }

    @Override
    public void interact(AbstractGameElement actor) {
        if (actor instanceof Fireman || actor instanceof Plane) {
            isBurning = false;
            burningFor = 0;
        }
        // if fire -> try catch fire
        // if bulldozer -> transform into land
    }

    @Override
    public void update() {
        if (isBurning()) {
            if (--burningFor == 0)
                isBurning = false;
            else
                spread(this.getPosition());
        }
    }

    @Override
    public void spread(Point2D position) {
        //fetch neighbours, interact with each one of them if they are burnables   
    }
}
