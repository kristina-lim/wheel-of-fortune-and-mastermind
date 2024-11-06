import java.util.*;

public class SimpleAIPlayer implements WheelOfFortunePlayer {
    private String playerId;
    private List<Character> vowelGuessOrder;
    private Set<Character> guessedLetters;

    public SimpleAIPlayer(String playerId) {
        this.playerId = playerId;
        this.vowelGuessOrder = Arrays.asList('a', 'e', 'i', 'o', 'u');
        this.guessedLetters = new HashSet<>();
    }

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

    @Override
    public String playerId() {
        return playerId;
    }

    @Override
    public void reset() {
        guessedLetters.clear();  // Reset guessed letters for a new game
    }
}
