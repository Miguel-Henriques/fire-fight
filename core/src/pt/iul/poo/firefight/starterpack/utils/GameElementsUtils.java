package pt.iul.poo.firefight.starterpack.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.gameObjects.props.Fire;
import pt.iul.poo.firefight.starterpack.gameObjects.props.Land;

public final class GameElementsUtils {
    
    public static List<AbstractGameElement> getNeighbours(List<AbstractGameElement> gameElementsList, Point2D origin) {
		List<Point2D> referencePoints = new ArrayList<>();

		referencePoints.add(origin.plus(Direction.LEFT.asVector()));
		referencePoints.add(origin.plus(Direction.RIGHT.asVector()));
		referencePoints.add(origin.plus(Direction.UP.asVector()));
		referencePoints.add(origin.plus(Direction.DOWN.asVector()));

		return gameElementsList.stream().filter(element -> referencePoints.contains(element.getPosition()))
				.collect(Collectors.toList());
	}

	public static int activeFires(List<AbstractGameElement> gameElementsList) {
		return (int) gameElementsList.stream().filter(element -> element instanceof Fire).count();
	}

	public static int mostFireActiveColumn(List<AbstractGameElement> gameElementsList) {
		int mostActiveColumn = 0;
		int mostActiveColumnFireCount = 0;
		List<AbstractGameElement> listOfFires = gameElementsList.stream().filter(element -> element instanceof Fire).collect(Collectors.toList());
		for (int column = 0; column < 10; column++) {
			final int currentColumn = column; //Workaround for lambda enclosed non final local variables
			int columnFireCount = (int) listOfFires.stream().filter(element -> element.getPosition().getX() == currentColumn).count();
			if (columnFireCount > mostActiveColumnFireCount){
				mostActiveColumn = column;
				mostActiveColumnFireCount = columnFireCount;
			}
		}
		return mostActiveColumn;
	}

	public static List<AbstractGameElement> getObjectsAt(List<AbstractGameElement> gameElementsList, Point2D position) {
		return gameElementsList.stream().filter(element -> element.getPosition().equals(position))
				.collect(Collectors.toList());
	}

	public static AbstractGameElement getUpperMostElement(List<AbstractGameElement> gameElementsList, Point2D position) {
		return getObjectsAt(gameElementsList, position).stream().sorted().findFirst().orElse(new Land(position));
	}

	public static boolean isUpperMostElement(List<AbstractGameElement> gameElementsList, AbstractGameElement element) {
		return getUpperMostElement(gameElementsList, element.getPosition()).equals(element);
	}

	public static AbstractGameElement getBottomMostElement(List<AbstractGameElement> gameElementsList, Point2D position) {
		return getObjectsAt(gameElementsList, position).stream().sorted(Collections.reverseOrder()).findFirst().orElse(new Land(position));
	}
}
