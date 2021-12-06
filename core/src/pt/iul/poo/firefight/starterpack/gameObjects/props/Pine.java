package pt.iul.poo.firefight.starterpack.gameObjects.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractBurnableGameElement;


public class Pine extends AbstractBurnableGameElement {

	public Pine(Point2D position) {
		super(position);
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public int getDefaultBurningFor() {
		return 10;
	}

	@Override
	public int getChanceOfCatchingFire() {
		return 5;
	}

	@Override
	public int extinguishedFirePoints() {
		return 1;
	}
}
