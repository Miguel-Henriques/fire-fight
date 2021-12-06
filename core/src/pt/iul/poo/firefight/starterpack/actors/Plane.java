package pt.iul.poo.firefight.starterpack.actors;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.GameEngine;
import pt.iul.poo.firefight.starterpack.interfaces.AbstractControllableActor;
import pt.iul.poo.firefight.starterpack.interfaces.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.interfaces.IInteractable;
import pt.iul.poo.firefight.starterpack.interfaces.IUpdatable;
import pt.iul.poo.firefight.starterpack.utils.CacheOperation;

public class Plane extends AbstractControllableActor implements IUpdatable {

    private static final Direction MOVEMENT_PATTERN = Direction.UP;
    private static final int MOVEMENTS_PER_TURN = 2;

    public Plane(Point2D position) {
        super(position);
        setCanMove(false); 
    }

    @Override
    public int getLayer() {
        return 4;
    }

    @Override
    public void update() {        
        for (int movements = 0; movements < MOVEMENTS_PER_TURN; movements++){
            if(canMove())
                move(MOVEMENT_PATTERN);
            else
                break;
        }
        setCanMove(true);
    }

    @Override
    public void move(Direction direction) {
        setLastInput(direction);
        Point2D newPosition = getPosition().plus(direction.asVector());

        if (isWithinBounds(newPosition)) {
            AbstractGameElement objectAt = GameEngine.getInstance().getUpperMostElement(newPosition);
            if (objectAt instanceof IInteractable)
                ((IInteractable) objectAt).interact(this);
        } else {
            GameEngine.getInstance().addToCache(this, CacheOperation.DELETE);
            setCanMove(false);
        }
        setPosition(newPosition);
    }
}
