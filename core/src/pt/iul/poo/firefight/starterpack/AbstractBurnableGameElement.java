package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.actors.Fireman;
import pt.iul.poo.firefight.starterpack.actors.Plane;
import pt.iul.poo.firefight.starterpack.behaviours.IInteractable;
import pt.iul.poo.firefight.starterpack.behaviours.IBurnable;
import pt.iul.poo.firefight.starterpack.behaviours.IUpdatable;
import pt.iul.poo.firefight.starterpack.props.Fire;

public abstract class AbstractBurnableGameElement extends AbstractGameElement
        implements IBurnable, IInteractable {

    private BurningState state;
    private Fire fire;

    public AbstractBurnableGameElement(Point2D position) {
        super(position);
    }

    @Override
    public boolean isBurning() {
        return state.equals(BurningState.BURNING);
    }

    @Override
    public void setOnFire() {
        fire = new Fire(this.getPosition());
        fire.setBurningFor(getDefaultBurningFor());
        state = BurningState.BURNING;
        GameEngine.getInstance().addGameElement(fire);
    }

    @Override
    public void putFireOut(BurningState state) {
        fire = null;
        this.state = state;
    }

    @Override
    public int getCurrentBurningFor() {
        return fire.getBurningFor();
    }

    @Override
    public void interact(AbstractGameElement element) {
        if (element instanceof Fire)
            setOnFire();
        // if fire -> try catch fire
        // if bulldozer -> transform into land
    }
}