package pt.iul.poo.firefight.starterpack.props;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.AbstractGameElement;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Eucaliptus extends AbstractGameElement {

	public Eucaliptus(Point2D position) {
		super(position);
	}

	@Override
	public int getLayer() {
		return 1;
	}
}
