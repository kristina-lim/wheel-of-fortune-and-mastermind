// class encapsulates code for looping through a set of games and recording the results
public abstract class Game {
    // Plays a set of games and record the results
    public AllGamesRecord playAll(){// Initialize the record holder for all games
        AllGamesRecord allGamesRecord = new AllGamesRecord();
        GameRecord gameRecord = play();   // Play a game and get the result as a GameRecord
        allGamesRecord.add(gameRecord);
        // Loop until playNext() returns false
        while (playNext()) {
            gameRecord = play();   // Play a game and get the result as a GameRecord
            allGamesRecord.add(gameRecord);   // Add the result to AllGamesRecord
        }
        return allGamesRecord;

    };

    // Compares object to another object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return true;
    }

    // toString method
    @Override
    public String toString() {
        return "Game{}";
    }

    // Abstract method to play a game
    public abstract GameRecord play();
    // Abstract method to determine if the next game should be played
    public abstract boolean playNext();
}
