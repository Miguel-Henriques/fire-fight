package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.actors.Fireman;
import pt.iul.poo.firefight.starterpack.behaviours.AbstractGameElement;
import pt.iul.poo.firefight.starterpack.behaviours.UnknownGameElementException;
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

	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<AbstractGameElement> gameElements; // Lista de objetos
	private Fireman fireman; // Referencia para o bombeiro

	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador
	// e' feito no construtor
	// Tambem poderia ser feito no main - estes passos tem sempre que ser feitos!
	public GameEngine() {

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		gameElements = new ArrayList<>();
	}

	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado
	// (neste caso seria a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada

		if (Direction.isDirection(key))
			fireman.move(Direction.directionFor(key));
		gui.update(); // redesenha as imagens na GUI, tendo em conta as novas posicoes
	}

	// Criacao dos objetos e envio das imagens para GUI
	public void start() {
		// createTerrain(); // criar mapa do terreno
		try {
			loadScene(INITIAL_LEVEL);
			sendImagesToGUI(); // enviar as imagens para a GUI
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
					addGameElement(line[x_coord], new Point2D(x_coord, y_coord));
					addTerrain(new Point2D(x_coord, y_coord));
				}
			} else {
				line = scanner.nextLine().split(" ");
				addGameElement(line[0], new Point2D(Integer.parseInt(line[1]), Integer.parseInt(line[2])));
			}
		}
		scanner.close();
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		gui.addImages(gameElements.stream().map(object -> (ImageTile) object).collect(Collectors.toList()));
	}

	private void addGameElement(String objectCode, Point2D position) {
		if (objectCode != "_") {
			AbstractGameElement element;
			try {
				element = AbstractGameElement.interpretGameElement(objectCode, position);
				gameElements.add(element);
			} catch (UnknownGameElementException e) {
				e.printStackTrace();
			}
		}
	}

	private void addTerrain(Point2D position) {
		gui.addImage(new Land(position));
	}
}