import java.util.*;

// SimpleAIPlayer simulates a game by guessing vowels in a fixed order, followed by random guesses if all vowels are guessed
public class SimpleAIPlayer implements WheelOfFortunePlayer {
    private String playerId;
    private List<Character> vowelGuessOrder;
    private Set<Character> guessedLetters;

    // Constructor
    public SimpleAIPlayer(String playerId) {
        this.playerId = playerId;
        this.vowelGuessOrder = Arrays.asList('a', 'e', 'i', 'o', 'u');
        this.guessedLetters = new HashSet<>();
    }

    // Determines the next letter to guess
    @Override
    public char nextGuess() {
        for (char vowel : vowelGuessOrder) {
            if (!guessedLetters.contains(vowel)) {
                guessedLetters.add(vowel);
                return vowel;
            }
        }

        // If all vowels are guessed, start guessing random letters
        char guessedLetter;
        while (true) {
            guessedLetter = (char) ('a' + new Random().nextInt(26)); // Random letter from a-z
            if (!guessedLetters.contains(guessedLetter)) {
                guessedLetters.add(guessedLetter);
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

    // Resets the AI player's state by clearing the set of guessed letters
    @Override
    public void reset() {
        guessedLetters.clear();  // Reset guessed letters for a new game
    }

    // Checks if SimpleAIPlayer objects are equal based on their player ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleAIPlayer that = (SimpleAIPlayer) o;
        return Objects.equals(playerId, that.playerId) && Objects.equals(vowelGuessOrder, that.vowelGuessOrder) && Objects.equals(guessedLetters, that.guessedLetters);
    }

    // Provides a string representation of the SimpleAIPlayer object
    @Override
    public String toString() {
        return "SimpleAIPlayer{" +
                "playerId='" + playerId + '\'' +
                ", vowelGuessOrder=" + vowelGuessOrder +
                ", guessedLetters=" + guessedLetters +
                '}';
    }
}
