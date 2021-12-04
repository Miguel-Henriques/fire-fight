package pt.iul.poo.firefight.starterpack.behaviours;

import pt.iul.ista.poo.utils.Point2D;

public interface IMovable {
    
    void move();

    boolean canMoveTo(Point2D p);
}