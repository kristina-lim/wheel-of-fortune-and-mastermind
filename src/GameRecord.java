// Class keeps track of the score and player ID for a single play of a game
public class GameRecord implements Comparable<GameRecord> {
    // Variable score keeps track of the score
    private int score;
    private String playerId;

    // Constructor
    public GameRecord(int score, String playerId) {
        this.score = score;
        this.playerId = playerId;
    }

    // Getters for score
    public int getScore() {
        return score;
    }

    // Getters for player ID
    public String getPlayerId() {
        return playerId;
    }

    // Default compareTo method
    // Compares GameRecord to another GameRecord for sorting purposes
    @Override
    public int compareTo(GameRecord other) {
        return Integer.compare(this.score, other.score);
    }

    // toString method to show player ID and score
    @Override
    public String toString() {
        return "Player ID: " + playerId + ", Score: " + score;
    }
}