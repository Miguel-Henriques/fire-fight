package pt.iul.poo.firefight.starterpack.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PointScoringSystem {

    private Score score;
    private static final String SAVE_PATH = "core/scoreboard/scoreboard.txt";
    private static final int SCOREBOARD_MAX_SIZE = 5;

    public PointScoringSystem(String player) {
        score = new Score(player, 0);
    }

    public void addToScore(int points) {
        score.addPoints(points);
    }

    /**
     * Returns scoreboard as is (may be unordered if manipulated manually)
     * 
     * @return
     * @throws FileNotFoundException
     */
    public List<Score> fetchScoreboard() throws FileNotFoundException {
        List<Score> scoreboard = new ArrayList<>();
        Scanner reader = new Scanner(new File(SAVE_PATH));
        while (reader.hasNextLine()) {
            String nextLine = reader.nextLine();
            if (!nextLine.isEmpty())
                scoreboard.add(new Score(nextLine));
        }
        return scoreboard;
    }

    public void registerScore() throws IOException {
        File file = new File(SAVE_PATH);
        List<Score> revisedScoreboard = new ArrayList<>();
        revisedScoreboard.add(score);
        if (!file.createNewFile()) {
            List<Score> scoreboard = fetchScoreboard();
            scoreboard.add(score);
            Collections.sort(scoreboard);
            revisedScoreboard = scoreboard.stream().limit(SCOREBOARD_MAX_SIZE).collect(Collectors.toList());
        }
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        revisedScoreboard.forEach(s -> writer.println(s));
        writer.close();
        if (revisedScoreboard.get(0).equals(score))
            score.setHighscore(true);
    }

    public String getScoreMessage() {
        String message = score.toString();
        if (score.isHighscore())
            message += " (High score)";
        return message + " points";
    }
}
