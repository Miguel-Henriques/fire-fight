package pt.iul.poo.firefight.starterpack.gameObjects.interfaces;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.engine.GameEngine;
import pt.iul.poo.firefight.starterpack.utils.GameElementsUtils;

public abstract class AbstractControllableActor extends AbstractGameElement implements IMovable {

	private Direction lastInput;
	private boolean canMove;

	public AbstractControllableActor(Point2D position) {
		super(position);
	}

	public boolean isWithinBounds(Point2D p) {
		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;
		return true;
	}

	@Override
	public Direction getLastInput() {
		return lastInput;
	}

	@Override
	public void setLastInput(Direction lastInput) {
		this.lastInput = lastInput;
	}

	@Override
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	@Override
	public boolean canMove() {
		return canMove;
	}

	@Override
	public void move(Direction direction) {
		setLastInput(direction);
		Point2D newPosition = getPosition().plus(direction.asVector());

		if (isWithinBounds(newPosition)) {
			setCanMove(true);
			AbstractGameElement objectAt = GameElementsUtils.getUpperMostElement(GameEngine.getInstance().getGameElements(), newPosition);
			if (objectAt instanceof IInteractable)
				((IInteractable) objectAt).interact(this);
			if (canMove())
				setPosition(newPosition);
		}
	}
}
