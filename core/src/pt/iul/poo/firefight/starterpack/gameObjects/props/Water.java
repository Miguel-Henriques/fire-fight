package pt.iul.poo.firefight.starterpack.gameObjects.props;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractGameElement;

public class Water extends AbstractGameElement {

    Direction orientation;

    public Water(Point2D position, Direction orientation) {
        super(position);
        this.orientation = orientation;
    }

    @Override
	public int getLayer() {
		return 3;
	}

    @Override
    public String getName() {
        return super.getName() + "_" + orientation.toString().toLowerCase();
    }
}
