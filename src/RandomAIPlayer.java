import java.util.Objects;
import java.util.Random;

// RandomAIPlayer makes random guesses in the WheelOfFortuneAIGame
public class RandomAIPlayer implements WheelOfFortunePlayer {
    private String playerId;
    private Random random;
    private String guessedLetters;

    // Constructor
    public RandomAIPlayer(String playerId) {
        this.playerId = playerId;
        this.random = new Random();
        this.guessedLetters = "";
    }

    // Generates a random letter from 'a' to 'z' for the next guess
    @Override
    public char nextGuess() {
        char guessedLetter;
        while (true) {
            guessedLetter = (char) ('a' + random.nextInt(26)); // Random letter from a-z
            if (guessedLetters.indexOf(guessedLetter) == -1) { // Ensure it's a new guess
                guessedLetters += guessedLetter;
                break;
            }
        }
        return guessedLetter;
    }

    // Retrieves player's ID
    @Override
    public String playerId() {
        return playerId;
    }

    // Resets the AI player's state by clearing the record of guessed letters
    @Override
    public void reset() {
        guessedLetters = "";  // Reset guessed letters for a new game
    }

    // RandomAIPlayer objects are equal based on their player's ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomAIPlayer that = (RandomAIPlayer) o;
        return Objects.equals(playerId, that.playerId) && Objects.equals(random, that.random) && Objects.equals(guessedLetters, that.guessedLetters);
    }

    // Provides a string representation of the RandomAIPlayer object
    @Override
    public String toString() {
        return "RandomAIPlayer{" +
                "playerId='" + playerId + '\'' +
                ", random=" + random +
                ", guessedLetters='" + guessedLetters + '\'' +
                '}';
    }
}
