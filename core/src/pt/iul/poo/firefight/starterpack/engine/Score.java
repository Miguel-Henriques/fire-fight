package pt.iul.poo.firefight.starterpack.engine;

public class Score implements Comparable<Score>{

    private int points;
    private String player;
    private boolean isHighscore;

    public Score(String player, int points) {
        this.player=player;
        this.points=points;
    }

    public boolean isHighscore() {
        return isHighscore;
    }

    public void setHighscore(boolean isHighscore) {
        this.isHighscore = isHighscore;
    }

    public Score(String line) {
        player = line.split(":")[0].trim();
        points = Integer.parseInt(line.split(":")[1].trim());
    }

    @Override
    public String toString() {
        return player + ": " + points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int compareTo(Score o) {
        return Integer.compare(o.getPoints(), points);
    }
    
    public void addPoints(int points) {
        this.points+=points;
    }
}
