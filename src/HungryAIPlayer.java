import java.util.*;

public class HungryAIPlayer implements WheelOfFortunePlayer {
    private String playerId;
    private Set<Character> guessedLetters = new HashSet<>();

    public HungryAIPlayer(String playerId) {
        this.playerId = playerId;
//        this.guessedLetters = new HashSet<>();
    }

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

    @Override
    public String playerId() {
        return playerId;
    }

    @Override
    public void reset() {
        guessedLetters.clear();  // Reset guessed letters for a new game
    }
}
