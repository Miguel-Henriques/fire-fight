package pt.iul.poo.firefight.starterpack.interfaces;

import pt.iul.ista.poo.utils.Direction;

public interface IMovable {
    
    void move(Direction direction);

    public Direction getLastInput();

    public void setLastInput(Direction lastInput);

    void setCanMove(boolean canMove);

    boolean canMove();
}