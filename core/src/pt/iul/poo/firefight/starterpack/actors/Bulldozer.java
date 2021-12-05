package pt.iul.poo.firefight.starterpack.actors;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractGameElement;

public class Bulldozer extends AbstractGameElement implements IActor {

    public Bulldozer(Point2D position) {
        super(position);
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public void move(Direction direction) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean canMoveTo(Point2D p) {
        // TODO Auto-generated method stub
        return false;
    }
}
