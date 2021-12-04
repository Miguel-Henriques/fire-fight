package pt.iul.poo.firefight.starterpack.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractGameElement;

public class Bulldozer extends AbstractGameElement {

    public Bulldozer(Point2D position) {
        super(position);
    }

    @Override
    public int getLayer() {
        return 3;
    }
}
