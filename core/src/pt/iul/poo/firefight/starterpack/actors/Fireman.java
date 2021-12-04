package pt.iul.poo.firefight.starterpack.actors;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.GameEngine;
import pt.iul.poo.firefight.starterpack.behaviours.IMovable;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Fireman extends AbstractGameElement implements IMovable {

	public Fireman(Point2D position) {
		super(position);
	}

	@Override
	public void move(Direction direction) {
		Point2D newPosition = getPosition().plus(direction.asVector());

		if (canMoveTo(newPosition)) {
			setPosition(newPosition);
		}
	}

	// Move numa direcao aleatoria
	public void randomMove() {

		boolean hasMoved = false;

		while (hasMoved == false) {

			Direction randDir = Direction.random();
			Point2D newPosition = getPosition().plus(randDir.asVector());

			if (canMoveTo(newPosition)) {
				setPosition(newPosition);
				hasMoved = true;
			}
		}
	}

	public boolean canMoveTo(Point2D p) {
		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;
		return true;
	}

	@Override
	public int getLayer() {
		return 3;
	}
}
