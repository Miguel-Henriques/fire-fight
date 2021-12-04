package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.actors.Fireman;
import pt.iul.poo.firefight.starterpack.props.Bulldozer;
import pt.iul.poo.firefight.starterpack.props.Eucaliptus;
import pt.iul.poo.firefight.starterpack.props.Fire;
import pt.iul.poo.firefight.starterpack.props.Grass;
import pt.iul.poo.firefight.starterpack.props.Pine;

public abstract class AbstractGameElement implements ImageTile {

    private Point2D position;
    private int layer;

    public AbstractGameElement(Point2D position) {
        this.position = position;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public static AbstractGameElement interpretGameElement(String code, Point2D position)
            throws UnknownGameElementException {
        switch (code) {
            case "p":
                return new Pine(position);
            case "m":
                return new Grass(position);
            case "e":
                return new Eucaliptus(position);
            case "Bulldozer":
                return new Bulldozer(position);
            case "Fireman":
                return new Fireman(position);
            case "Fire":
                return new Fire(position);
            default:
                throw new UnknownGameElementException();
        }
    }
}