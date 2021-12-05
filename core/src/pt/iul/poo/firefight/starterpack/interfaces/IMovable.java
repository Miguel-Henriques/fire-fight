package pt.iul.poo.firefight.starterpack.interfaces;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public interface IMovable {
    
    void move(Direction direction);

    public Direction getLastInput();

    boolean isWithinBounds(Point2D p);

    void setCanMove(boolean canMove);

    boolean canMove();
}