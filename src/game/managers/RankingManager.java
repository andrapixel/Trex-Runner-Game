package game.managers;

import game.objects.Player;

import java.util.ArrayList;

public class RankingManager {

    private static RankingManager rankingManagerInstance = new RankingManager();
    private ArrayList<Player> rankingList;  // list that stores all the player rankings

    private RankingManager()
    {
        rankingList = new ArrayList<>();
    }

    // getting a unique instance of the ranking manager
    public static RankingManager getInstance() {
        if (rankingManagerInstance == null) {
            return new RankingManager();
        }

        return rankingManagerInstance;
    }

    // method that returns the ranking list of the players
    public ArrayList<Player> getPlayerRanking()
    {
        return rankingList;
    }

    // method that adds a player to the ranking list
    public void addPLayerToRankingList(Player player)
    {
        rankingList.add(player);
    }

    // method that returns a player's highest score
    public int getPlayerHS(Player player)
    {
        int highScore = -1;
        ArrayList<Player> selectedPlayerScores = getRankingByPlayer(player.getPlayerName());

        // compare all the scores of a player in order to determine the highest one
        for (int i = 0; i < selectedPlayerScores.size(); ++i)
            if (selectedPlayerScores.get(i).getScore() >= highScore)
                highScore = selectedPlayerScores.get(i).getScore();

        return highScore;
    }

    // method that returns a list of all the scores obtained by a single specific player
    private ArrayList<Player> getRankingByPlayer(String playerName)
    {
        ArrayList<Player> selectedPlayers = new ArrayList<>();

        for (int i = 0; i < rankingList.size(); ++i)
            if (rankingList.get(i).getPlayerName() == playerName)
                selectedPlayers.add(rankingList.get(i));

        return selectedPlayers;
    }
}
