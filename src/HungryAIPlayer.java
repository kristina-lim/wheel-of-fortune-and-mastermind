import java.util.*;

// Represents an AI Player that guesses a selection of unused letters from the alphabet
public class HungryAIPlayer implements WheelOfFortunePlayer {
    private String playerId;
    private Set<Character> guessedLetters = new HashSet<>();

    // Constructor
    public HungryAIPlayer(String playerId) {
        this.playerId = playerId;
        this.guessedLetters = new HashSet<>();
    }

    // Determines the next letter the AI will guess
    @Override
    public char nextGuess() {
        // Create a set of all lowercase alphabet letters
        Set<Character> allLetters = new HashSet<>();
        for (char c = 'a'; c <= 'z'; c++) {
            allLetters.add(c);
        }

        // Remove letters the AI has already guessed
        allLetters.removeAll(guessedLetters);

        // Choose a random letter from the remaining ones
        Random rand = new Random();
        List<Character> availableLetters = new ArrayList<>(allLetters);
        char guess = availableLetters.get(rand.nextInt(availableLetters.size()));

        // Add the guessed letter to the set of guessed letters
        guessedLetters.add(guess);

        return guess;
    }

    // Returns player ID associated with this AI Player
    @Override
    public String playerId() {
        return playerId;
    }

    // Resets the set of guessed letter for a new game
    @Override
    public void reset() {
        guessedLetters.clear();  // Reset guessed letters for a new game
    }

    // Compares HungryAIPlayer instance to another object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HungryAIPlayer that = (HungryAIPlayer) o;
        return Objects.equals(playerId, that.playerId) && Objects.equals(guessedLetters, that.guessedLetters);
    }

    // Returns a string representation of the HungryAIPlayer object
    @Override
    public String toString() {
        return "HungryAIPlayer{" +
                "playerId='" + playerId + '\'' +
                ", guessedLetters=" + guessedLetters +
                '}';
    }
}
