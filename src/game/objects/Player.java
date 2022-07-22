package game.objects;

public class Player {

    private String playerName;
    private int score;  // the score this player got for ONE game

    // no-parameter Player constructor
    public Player() {}

    // Getters & Setters region
    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    // end region
}
