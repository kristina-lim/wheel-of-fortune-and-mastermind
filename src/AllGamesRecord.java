import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Class manages and tracks the results of multiple games played in the game system
public class AllGamesRecord {
    // List to store all GameRecord objects
    private List<GameRecord> records = new ArrayList<>();

    // Constructor
    public AllGamesRecord() {
        records = new ArrayList<GameRecord>();
    }

    // Adds new GameRecord to the records list
    public void add(GameRecord record) {
        records.add(record);
    }

    // Calculates the average score of all games in the records
    public int average() {
        int sum = 0;
        // Sums all scores in records
        for (GameRecord record : records) {
            sum +=  record.getScore();
        }
        // Returns average score
        return sum / records.size();
    }

    // Calculates the average score for a specific player through their ID
    public int average(String playerId) {
        int sum = 0;
        int gamesPlayed = 0;
        // Sums all scores game played by specific player
        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                sum += record.getScore();
                gamesPlayed++;
            }
        }
        // Returns average score for player
        return sum / gamesPlayed;
    }

    // Returns a list of the top n GameRecords
    public List<GameRecord> highGameList(int n) {
        // Sort records based on scores
        Collections.sort(records);
        // Returns sublist of the top n scores
        return records.subList(Math.max(records.size() - n, 0), records.size());
    }

    // Returns a list of the top n GameRecords for specific player through ID
    public List<GameRecord> highGameList(String playerId, int n) {
        // List to store all player records
        List<GameRecord> playerRecords = new ArrayList<>();
        // Collect records for specific player
        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                playerRecords.add(record);
            }
        }
        // Sort the player's records based on scores
        Collections.sort(playerRecords);
        // Returns sublist of the top n scores of the specific player
        return playerRecords.subList(Math.max(playerRecords.size() - n, 0), playerRecords.size());
    }

    // Returns string of all GameRecords in the records list
    @Override
    public String toString() {
        return records.toString();
    }
}

