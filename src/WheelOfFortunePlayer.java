public interface WheelOfFortunePlayer {
    // Method to get the next letter guess from the player
    char nextGuess();
    // Method to return the player's ID
    String playerId();
    // Method to reset the player for a new game
    void reset();
}
