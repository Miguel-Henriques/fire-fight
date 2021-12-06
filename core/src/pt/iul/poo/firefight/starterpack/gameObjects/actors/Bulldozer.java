package pt.iul.poo.firefight.starterpack.gameObjects.actors;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.engine.GameEngine;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractControllableActor;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.IInteractable;
import pt.iul.poo.firefight.starterpack.utils.CacheOperation;

public class Bulldozer extends AbstractControllableActor implements IInteractable {

    public Bulldozer(Point2D position) {
        super(position);
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public void interact(AbstractGameElement element) {
        if (element instanceof Fireman) {
            GameEngine.getInstance().setActiveActor(this);
            GameEngine.getInstance().addToCache(element, CacheOperation.DELETE);
        }
    }

    @Override
    public String getName() {
        if (getLastInput() != null)
            return super.getName() + "_" + getLastInput().toString().toLowerCase();
        return super.getName();
    }
}
