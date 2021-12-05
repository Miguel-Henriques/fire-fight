package pt.iul.poo.firefight.starterpack.actors;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.interfaces.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.interfaces.IUpdatable;

public class Plane extends AbstractGameElement implements IUpdatable{

    public Plane(Point2D position) {
        super(position);
    }

    @Override
    public int getLayer() {
        return 4;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }
}
