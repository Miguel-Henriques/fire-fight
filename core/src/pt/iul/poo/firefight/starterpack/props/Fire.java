package pt.iul.poo.firefight.starterpack.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractBurnableGameElement;
import pt.iul.poo.firefight.starterpack.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.BurningState;
import pt.iul.poo.firefight.starterpack.CacheOperation;
import pt.iul.poo.firefight.starterpack.GameEngine;
import pt.iul.poo.firefight.starterpack.actors.IActor;
import pt.iul.poo.firefight.starterpack.behaviours.IBurnable;
import pt.iul.poo.firefight.starterpack.behaviours.IInteractable;
import pt.iul.poo.firefight.starterpack.behaviours.IUpdatable;

public class Fire extends AbstractGameElement implements IUpdatable, IInteractable {

	private int burningFor;

	public Fire(Point2D position) {
		super(position);
	}

	public int getBurningFor() {
		return burningFor;
	}

	public void setBurningFor(int burningFor) {
		this.burningFor = burningFor;
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public void update() {
		if (--burningFor == 0)
			putFireOutWithBurntTrail(true);
		else
			spread();
	}

	public void spread() {
		GameEngine.getInstance().getNeighbours(this.getPosition()).forEach(neighbour -> {
			if (neighbour instanceof AbstractBurnableGameElement)
				((AbstractBurnableGameElement) neighbour).interact(this);
		});
	}

	public void putFireOutWithBurntTrail(boolean leaveBurntTrail) {
		//GameEngine.getInstance().removeGameElement(this);
		GameEngine.getInstance().addToCache(this, CacheOperation.DELETE);
		AbstractGameElement burntVegetation = GameEngine.getInstance().getBottomMostElement(this.getPosition());
		if (burntVegetation instanceof AbstractBurnableGameElement) {
			if (leaveBurntTrail)
				((IBurnable) burntVegetation).putFireOut(BurningState.BURNT);
			else
				((IBurnable) burntVegetation).putFireOut(BurningState.NORMAL);
		}
	}

	@Override
	public void interact(AbstractGameElement element) {
		if (element instanceof IActor)
			putFireOutWithBurntTrail(false);
	}
}
