package pt.iul.poo.firefight.starterpack.gameObjects.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractGameElement;

public class Land extends AbstractGameElement{

    public Land(Point2D position) {
        super(position);
    }

    @Override
    public int getLayer() {
        return 0;
    }
}
