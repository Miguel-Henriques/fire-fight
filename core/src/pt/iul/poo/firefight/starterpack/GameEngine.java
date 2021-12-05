package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.actors.Fireman;
import pt.iul.poo.firefight.starterpack.behaviours.IBurnable;
import pt.iul.poo.firefight.starterpack.behaviours.IUpdatable;
import pt.iul.poo.firefight.starterpack.props.Fire;
import pt.iul.poo.firefight.starterpack.props.Land;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	public static final String LEVELS_DIRECTORY = "core/levels"; // UPDATE THIS
	public static final int INITIAL_LEVEL = 0;

	private static GameEngine gameEngine = new GameEngine();
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<AbstractGameElement> gameElements; // Lista de objetos
	private Fireman fireman; // Referencia para o bombeiro
	private Map<AbstractGameElement, CacheOperation> cachedChanges;

	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador
	// e' feito no construtor
	// Tambem poderia ser feito no main - estes passos tem sempre que ser feitos!
	private GameEngine() {

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		gameElements = new ArrayList<>();
		cachedChanges = new HashMap<>();
	}

	public static GameEngine getInstance() {
		return gameEngine;
	}

	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado
	// (neste caso seria a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada

		if (Direction.isDirection(key))
			fireman.move(Direction.directionFor(key));

		updateGameElements();
		gui.update(); // redesenha as imagens na GUI, tendo em conta as novas posicoes
	}

	// Criacao dos objetos e envio das imagens para GUI
	public void start() {
		try {
			loadScene(INITIAL_LEVEL);
			sendImagesToGUI(); // enviar as imagens para a GUI
			gui.update();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadScene(int level) throws IOException {
		File scene = new File(LEVELS_DIRECTORY).listFiles()[level];
		Scanner scanner = new Scanner(scene);
		int y_coord = 0;
		while (scanner.hasNextLine()) {
			String[] line;
			if (y_coord < 10) {
				line = scanner.nextLine().split("");
				for (int x_coord = 0; x_coord < line.length; x_coord++) {
					addTerrain(new Point2D(x_coord, y_coord));
					initGameElement(line[x_coord], new Point2D(x_coord, y_coord));
				}
			} else {
				line = scanner.nextLine().split(" ");
				initGameElement(line[0], new Point2D(Integer.parseInt(line[1]), Integer.parseInt(line[2])));
			}
			y_coord++;
		}
		scanner.close();
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		gui.addImages(gameElements.stream().map(object -> (ImageTile) object).collect(Collectors.toList()));
	}

	private void initGameElement(String objectCode, Point2D position) {
		if (!objectCode.equals("_")) {
			AbstractGameElement element;
			try {
				element = AbstractGameElement.interpretGameElement(objectCode, position);
				if (element instanceof Fireman)
					this.fireman = (Fireman) element;
				if (element instanceof Fire){
					AbstractGameElement vegetation = getUpperMostElement(position);
					if (vegetation instanceof IBurnable)
						((IBurnable) vegetation).setOnFire((Fire)element);
				}
				gameElements.add(element);

			} catch (UnknownGameElementException e) {
				e.printStackTrace();
			}
		}
	}

	private void addTerrain(Point2D position) {
		gui.addImage(new Land(position));
	}

	public List<AbstractGameElement> getObjectsAt(Point2D position) {
		return gameElements.stream().filter(element -> element.getPosition().equals(position))
				.collect(Collectors.toList());
	}

	public AbstractGameElement getUpperMostElement(Point2D position) {
		return GameEngine.getInstance().getObjectsAt(position).stream().sorted().findFirst().orElse(new Land(position));
	}

	public AbstractGameElement getBottomMostElement(Point2D position) {
		return GameEngine.getInstance().getObjectsAt(position).stream().sorted(Collections.reverseOrder()).findFirst().orElse(new Land(position));
	}

	public void removeGameElement(AbstractGameElement element) {
		gameElements.remove(element);
		gui.removeImage(element);
	}

	public void addGameElement(AbstractGameElement element) {
		gameElements.add(element);
		gui.addImage(element);
	}

	public List<AbstractGameElement> getNeighbours(Point2D origin) {
		List<Point2D> referencePoints = new ArrayList<>();

		referencePoints.add(origin.plus(Direction.LEFT.asVector()));
		referencePoints.add(origin.plus(Direction.RIGHT.asVector()));
		referencePoints.add(origin.plus(Direction.UP.asVector()));
		referencePoints.add(origin.plus(Direction.DOWN.asVector()));

		return gameElements.stream().filter(element -> referencePoints.contains(element.getPosition()))
				.collect(Collectors.toList());
	}

	public void updateGameElements() {
		gameElements.forEach(element -> {
			if (element instanceof IUpdatable)
				((IUpdatable) element).update();
		});
		releaseCacheChanges();
	}

	public void releaseCacheChanges() {
		cachedChanges.keySet().forEach(object -> {
			if (cachedChanges.get(object).equals(CacheOperation.ADD))
				addGameElement(object);
			else
				removeGameElement(object);
		});
		cachedChanges.clear();
	}

	public void addToCache(AbstractGameElement element, CacheOperation action) {
		cachedChanges.put(element, action);
	}
}