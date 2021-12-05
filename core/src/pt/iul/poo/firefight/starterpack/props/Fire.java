package pt.iul.poo.firefight.starterpack.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractBurnableGameElement;
import pt.iul.poo.firefight.starterpack.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.BurningState;
import pt.iul.poo.firefight.starterpack.GameEngine;
import pt.iul.poo.firefight.starterpack.actors.IActor;
import pt.iul.poo.firefight.starterpack.behaviours.IBurnable;
import pt.iul.poo.firefight.starterpack.behaviours.IInteractable;
import pt.iul.poo.firefight.starterpack.behaviours.IUpdatable;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends AbstractGameElement implements IUpdatable, IInteractable {

	private int burningFor;

	public Fire(Point2D position) {
		super(position);
	}

	public int getBurningFor() {
		return burningFor;
	}

	public void setBurningFor(int burningFor) {
		this.burningFor = burningFor;
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public void update() {
		if (--burningFor == 0) {
			// RemoveFromGame()
			// InteractWithBurntTerrain
		} else
			spread(this.getPosition());
	}

	public void spread(Point2D position) {
		// fetch neighbours, interact with each one of them if they are burnables
	}

	public void putFireOutWithBurntTrail(boolean leaveBurntTrail) {
		GameEngine.getInstance().removeGameElement(this);
		AbstractGameElement burntVegetation = GameEngine.getInstance().getObjectsAt(this.getPosition()).get(0);
		if (burntVegetation instanceof AbstractBurnableGameElement) {
			if (leaveBurntTrail)
				((IBurnable) burntVegetation).putFireOut(BurningState.BURNT);
			else
				((IBurnable) burntVegetation).putFireOut(BurningState.NORMAL);
		}

	}

	@Override
	public void interact(AbstractGameElement element) {
		if (element instanceof IActor)
			putFireOutWithBurntTrail(false);
	}
}
