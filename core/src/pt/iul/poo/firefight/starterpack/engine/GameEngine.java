package pt.iul.poo.firefight.starterpack.engine;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import pt.iul.poo.firefight.starterpack.gameObjects.actors.Bulldozer;
import pt.iul.poo.firefight.starterpack.gameObjects.actors.Fireman;
import pt.iul.poo.firefight.starterpack.gameObjects.actors.Plane;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractControllableActor;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.IBurnable;
import pt.iul.poo.firefight.starterpack.gameObjects.interfaces.IUpdatable;
import pt.iul.poo.firefight.starterpack.gameObjects.props.Fire;
import pt.iul.poo.firefight.starterpack.gameObjects.props.Land;
import pt.iul.poo.firefight.starterpack.utils.CacheOperation;
import pt.iul.poo.firefight.starterpack.utils.GameElementsUtils;
import pt.iul.poo.firefight.starterpack.utils.UnknownGameElementException;

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final String PLAYER = "Sargento 'tá' quentinho";
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	public static final String LEVELS_DIRECTORY = "core/levels"; // UPDATE THIS
	public static final int INITIAL_LEVEL = 0;
	private int currentLevel;
	private int numOflevels;
	private boolean isComplete;

	private static GameEngine gameEngine = new GameEngine();
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<AbstractGameElement> gameElements; // Lista de objetos
	private List<ImageTile> vfxs; // Lista de efeitos
	private AbstractControllableActor activeActor; // Actor ativo
	private Map<AbstractGameElement, CacheOperation> cachedChanges; // Mudanças em memória(cache)
	private PointScoringSystem score;

	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador
	// e' feito no construtor
	// Tambem poderia ser feito no main - estes passos tem sempre que ser feitos!
	private GameEngine() {

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		gameElements = new ArrayList<>();
		vfxs = new ArrayList<>();
		cachedChanges = new HashMap<>();
		currentLevel = INITIAL_LEVEL;
		numOflevels = availableLevels();
		score = new PointScoringSystem(PLAYER);
	}

	public static GameEngine getInstance() {
		return gameEngine;
	}

	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado
	// (neste caso seria a GUI)
	@Override
	public void update(Observed source) {
		if(isComplete)
			return;

		clearVFXs();

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada
		interpretInput(key);
		
		updateGameElements();
		updateScoreInfo();
		gui.update(); // redesenha as imagens na GUI, tendo em conta as novas posicoes

		if(GameElementsUtils.activeFires(gameElements)==0) {
			if(currentLevel < numOflevels-1)
				loadLevel(++currentLevel);
			else
				loadFinishMessage();
		}
	}

	private void interpretInput(int key) {
		if (Direction.isDirection(key))
			activeActor.move(Direction.directionFor(key));

		if (key == KeyEvent.VK_ENTER && activeActor instanceof Bulldozer) {
			Fireman fireman = new Fireman(activeActor.getPosition());
			setActiveActor(fireman);
			addGameElement(fireman);
		}

		if (key == KeyEvent.VK_P) {
			Plane plane = new Plane(new Point2D(GameElementsUtils.mostFireActiveColumn(gameElements), 9));
			addGameElement(plane);
		}
	}

	private void updateScoreInfo() {
		gui.setStatusMessage(score.getScoreMessage());
	}

	private void loadFinishMessage() {
		System.out.println("Rendered");
		try {
			score.registerScore();
			gui.setStatusMessage("Congratulations, you've just completed the game! " + score.getScoreMessage());
			isComplete=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void loadLevel(int level) {
		try {
			clear();
			loadScene(level);
			updateScoreInfo();
			sendImagesToGUI();
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
					activeActor = (Fireman) element;
				if (element instanceof Fire){
					AbstractGameElement vegetation = GameElementsUtils.getUpperMostElement(gameElements, position);
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

	public void removeGameElement(AbstractGameElement element) {
		gameElements.remove(element);
		gui.removeImage(element);
	}

	public void addGameElement(AbstractGameElement element) {
		gameElements.add(element);
		gui.addImage(element);
	}

	public void updateGameElements() {
		gameElements.forEach(element -> {
			if (element instanceof IUpdatable)
				((IUpdatable) element).update();
		});
		releaseCacheChanges();
	}

	private void releaseCacheChanges() {
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

    public void renderVFX(ImageTile tile) {
		gui.addImage(tile);
		vfxs.add(tile);
    }

	public void clearVFXs() {
		vfxs.forEach(vfx -> gui.removeImage(vfx));
	}

	public void setActiveActor(AbstractControllableActor actor) {
		activeActor = actor;
	}

	public void clear() {
		gui.removeImages(gameElements.stream().map(element -> (ImageTile) element).collect(Collectors.toList()));
		clearVFXs();
		cachedChanges.clear();
		gameElements.clear();
	}

	public int availableLevels() {
		return new File(LEVELS_DIRECTORY).listFiles().length;
	}

	public void addToScore(int points) {
		score.addToScore(points);
	}

	public final List<AbstractGameElement> getGameElements() {
		return gameElements;
	}
}