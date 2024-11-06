import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public double average() {
        if (records.isEmpty()) return 0;
        int sum = 0;
        for (GameRecord record : records) {
            sum += record.getScore();
        }
        return (double) sum / records.size();
    }

    // Returns the average score for a specific player
    public double average(String playerId) {
        int sum = 0;
        int count = 0;
        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                sum += record.getScore();
                count++;
            }
        }
        return (double) sum / count;
    }

    // Returns a list of the top n GameRecords, sorted by score in descending order
    public List<GameRecord> highGameList(int n) {
        // Sort records in descending order based on score
        Collections.sort(records, new Comparator<GameRecord>() {
            @Override
            public int compare(GameRecord record1, GameRecord record2) {
                // Return a negative value if record1's score is greater than record2's
                return Integer.compare(record2.getScore(), record1.getScore());
            }
        });
        // Return sublist of the top n scores
        return records.subList(0, Math.min(n, records.size()));
    }

    // Returns a list of the top n GameRecords for a specific player, sorted in descending order
    public List<GameRecord> highGameList(String playerId, int n) {
        // List to store all player records
        List<GameRecord> playerRecords = new ArrayList<>();

        // Collect records for specific player
        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                playerRecords.add(record);
            }
        }

        // Sort the player's records based on scores in descending order
        Collections.sort(playerRecords, new Comparator<GameRecord>() {
            @Override
            public int compare(GameRecord record1, GameRecord record2) {
                // Return a negative value if record1's score is greater than record2's
                return Integer.compare(record2.getScore(), record1.getScore());
            }
        });

        // Return the top n records for that player
        return playerRecords.subList(0, Math.min(n, playerRecords.size()));
    }

    // Getter for records
    public List<GameRecord> getRecords() {
        return records;
    }

    // Returns string of all GameRecords in the records list
    @Override
    public String toString() {
        return records.toString();
    }
}