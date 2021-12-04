package pt.iul.poo.firefight.starterpack.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractGameElement;

public class Grass extends AbstractGameElement {

    public Grass(Point2D position) {
        super(position);
    }

    @Override
	public int getLayer() {
		return 1;
	}
}
