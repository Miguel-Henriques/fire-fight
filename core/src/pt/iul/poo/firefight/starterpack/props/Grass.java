package pt.iul.poo.firefight.starterpack.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractBurnableGameElement;

public class Grass extends AbstractBurnableGameElement {

    public Grass(Point2D position) {
        super(position);
    }

    @Override
	public int getLayer() {
		return 1;
	}

    @Override
    public int getDefaultBurningFor() {
        return 3;
    }

    @Override
    public int getChanceOfCatchingFire() {
        return 15;
    }
}
