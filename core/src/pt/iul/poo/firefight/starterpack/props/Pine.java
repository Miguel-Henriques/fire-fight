package pt.iul.poo.firefight.starterpack.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractBurnableGameElement;


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
	public double getChanceOfCatchingFire() {
		return 0.05;
	}
}
