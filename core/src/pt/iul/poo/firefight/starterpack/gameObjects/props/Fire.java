package pt.iul.poo.firefight.starterpack.gameObjects.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.engine.GameEngine;
import pt.iul.poo.firefight.starterpack.gameObjects.actors.Bulldozer;
import pt.iul.poo.firefight.starterpack.gameObjects.actors.Fireman;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractBurnableGameElement;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractControllableActor;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.IBurnable;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.IInteractable;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.IUpdatable;
import pt.iul.poo.firefight.starterpack.utils.BurningState;
import pt.iul.poo.firefight.starterpack.utils.CacheOperation;
import pt.iul.poo.firefight.starterpack.utils.GameElementsUtils;

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
		GameElementsUtils.getNeighbours(GameEngine.getInstance().getGameElements(), this.getPosition()).forEach(neighbour -> {
			if (neighbour instanceof AbstractBurnableGameElement)
				((AbstractBurnableGameElement) neighbour).interact(this);
		});
	}

	public void putFireOutWithBurntTrail(boolean leaveBurntTrail) {
		GameEngine.getInstance().addToCache(this, CacheOperation.DELETE);
		AbstractGameElement burntVegetation = GameElementsUtils.getBottomMostElement(GameEngine.getInstance().getGameElements(), this.getPosition());
		if (burntVegetation instanceof AbstractBurnableGameElement) {
			if (leaveBurntTrail)
				((IBurnable) burntVegetation).putFireOut(BurningState.BURNT);
			else
				((IBurnable) burntVegetation).putFireOut(BurningState.NORMAL);
		}
	}

	@Override
	public void interact(AbstractGameElement element) {
		if (element instanceof AbstractControllableActor) {
			putFireOutWithBurntTrail(false);
			if (element instanceof Fireman) {
				GameEngine.getInstance().renderVFX(new Water(this.getPosition(), ((Fireman) element).getLastInput()));
				((Fireman) element).setCanMove(false);
			}
			if (element instanceof Bulldozer)
				((IInteractable) GameElementsUtils.getBottomMostElement(GameEngine.getInstance().getGameElements(),this.getPosition())).interact(element);
		}
	}
}
