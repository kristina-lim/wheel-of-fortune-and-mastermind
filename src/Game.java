// class encapsulates code for looping through a set of games and recording the results
public abstract class Game {
    // Plays a set of games and record the results
    public AllGamesRecord playAll() {
        // Create a new object to hold the results of all games played
        AllGamesRecord allGamesRecord = new AllGamesRecord();

        do {
            // Play the game and get the GameRecord
            GameRecord record = play();
            // Add the record to the AllGamesRecord
            allGamesRecord.add(record);
        } while (playNext()); // Loop continues if the user says yes

        // Return all recorded games
        return allGamesRecord;
    }

    // Abstract method to play a game
    public abstract GameRecord play();
    // Abstract method to determine if the next game should be played
    public abstract boolean playNext();
}
