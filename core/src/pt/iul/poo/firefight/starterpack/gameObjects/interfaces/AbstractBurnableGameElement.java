package pt.iul.poo.firefight.starterpack.gameObjects.interfaces;

import java.util.SplittableRandom;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.engine.GameEngine;
import pt.iul.poo.firefight.starterpack.gameObjects.actors.Bulldozer;
import pt.iul.poo.firefight.starterpack.gameObjects.props.Fire;
import pt.iul.poo.firefight.starterpack.utils.BurningState;
import pt.iul.poo.firefight.starterpack.utils.CacheOperation;
import pt.iul.poo.firefight.starterpack.utils.GameElementsUtils;

public abstract class AbstractBurnableGameElement extends AbstractGameElement
        implements IBurnable, IInteractable, IScore {

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
        if ( state.equals(BurningState.NORMAL) && random <= getChanceOfCatchingFire() && GameElementsUtils.isUpperMostElement(GameEngine.getInstance().getGameElements(), this)) {
            fire = new Fire(this.getPosition());
            fire.setBurningFor(getDefaultBurningFor());
            state = BurningState.BURNING;
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
        if (state.equals(BurningState.BURNT))
            GameEngine.getInstance().addToScore(burntPenaltyPoints());
        else
            GameEngine.getInstance().addToScore(extinguishedFirePoints());
    }

    @Override
    public int getCurrentBurningFor() {
        return fire.getBurningFor();
    }

    @Override
    public void interact(AbstractGameElement element) {
        if (element instanceof Fire)
            trySetOnFire();
        if (element instanceof Bulldozer)
            GameEngine.getInstance().addToCache(this, CacheOperation.DELETE);
    }

    @Override
    public String getName() {
        return state.getState() + super.getName();
    }

    @Override
    public int burntPenaltyPoints() {
        return -1;
    }
}