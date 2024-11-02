public abstract class Game {
    // Plays a set of games and record the results
    public AllGamesRecord playAll() {
        // Create a new object to hold the results of all games played
        AllGamesRecord allGamesRecord = new AllGamesRecord();
        // Loop to play games until playNext() is false
        while (playNext()) {
            // Play the game and add the result GameRecord to allGamesRecord
            allGamesRecord.add(play());
        }
        // Returns that contains all the recorded games
        return allGamesRecord;
    }

    // Abstract method to play a game
    public abstract GameRecord play();
    // Abstract method to determine if the next game should be played
    public abstract boolean playNext();
}
