package pt.iul.poo.firefight.starterpack.actors;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractGameElement;

public class Plane extends AbstractGameElement implements IActor{

    public Plane(Point2D position) {
        super(position);
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
