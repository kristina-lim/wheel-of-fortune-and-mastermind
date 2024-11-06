import java.util.Random;

public class RandomAIPlayer implements WheelOfFortunePlayer {
    private String playerId;
    private Random random;
    private String guessedLetters;

    public RandomAIPlayer(String playerId) {
        this.playerId = playerId;
        this.random = new Random();
        this.guessedLetters = "";
    }

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

    @Override
    public String playerId() {
        return playerId;
    }

    @Override
    public void reset() {
        guessedLetters = "";  // Reset guessed letters for a new game
    }
}
