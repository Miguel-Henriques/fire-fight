package pt.iul.poo.firefight.starterpack;

import java.util.SplittableRandom;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.behaviours.IInteractable;
import pt.iul.poo.firefight.starterpack.behaviours.IBurnable;
import pt.iul.poo.firefight.starterpack.props.Fire;

public abstract class AbstractBurnableGameElement extends AbstractGameElement
        implements IBurnable, IInteractable {

    private BurningState state;
    private Fire fire;

    public AbstractBurnableGameElement(Point2D position) {
        super(position);
        fire = null;
        state = BurningState.NORMAL;
    }

    @Override
    public boolean isBurning() {
        return state.equals(BurningState.BURNING);
    }

    @Override
    public void trySetOnFire() {
        int random = new SplittableRandom().nextInt(0,100);
        if (random <= getChanceOfCatchingFire() && state.equals(BurningState.NORMAL)) {
            fire = new Fire(this.getPosition());
            fire.setBurningFor(getDefaultBurningFor());
            state = BurningState.BURNING;
            //GameEngine.getInstance().addGameElement(fire);
            GameEngine.getInstance().addToCache(fire, CacheOperation.ADD);
        }
    }

    @Override
    public void setOnFire(Fire fire) {
        state = BurningState.BURNING;
        this.fire = fire;
        fire.setBurningFor(getDefaultBurningFor());
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
            trySetOnFire();
        // if bulldozer -> transform into land
    }

    @Override
    public String getName() {
        return state.getState() + super.getName();
    }
}