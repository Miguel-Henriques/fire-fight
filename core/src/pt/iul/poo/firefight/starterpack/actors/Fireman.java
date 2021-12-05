package pt.iul.poo.firefight.starterpack.actors;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.interfaces.AbstractControllableActor;

public class Fireman extends AbstractControllableActor {

	public Fireman(Point2D position) {
		super(position);
	}

	@Override
	public int getLayer() {
		return 3;
	}
}
