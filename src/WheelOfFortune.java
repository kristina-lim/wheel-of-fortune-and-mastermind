import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Random;

// Abstract class for the Wheel of Fortune game, extends from Game class
public abstract class WheelOfFortune extends GuessingGame {
    protected String phrase;
    protected StringBuilder hiddenPhrase;
    protected String previousGuesses;
    protected int missedCount;
    protected int maxGuessCount;
    protected List<String> phraseList;

    // Constructor
    public WheelOfFortune(int maxGuessCount) {
        this.phrase = "";
        this.hiddenPhrase = new StringBuilder();
        this.previousGuesses = "";
        this.missedCount = 0;
        this.maxGuessCount = maxGuessCount;
        getHiddenPhrase();
        readPhrases();
    }

    // Reads phrases from text file
    public void readPhrases() {
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println("Error reading phrases file.");
        }
    }

    // Chooses a random phrase from the list
    protected void randomPhrase() {
        if (phraseList.isEmpty()) return;
        Random rand = new Random();
        int index = rand.nextInt(phraseList.size());
        this.phrase = phraseList.get(index); // Get a random phrase without removing it
        getHiddenPhrase();
    }

    // Converts phrase into hidden version
    protected void getHiddenPhrase() {
        this.hiddenPhrase = new StringBuilder();
        for (char c : this.phrase.toCharArray()) {
            if (Character.isLetter(c)) {
                this.hiddenPhrase.append('*');
            } else {
                this.hiddenPhrase.append(c);
            }
        }
    }

    // Check the guessed letter and update the game
    public boolean processGuess(char guessedLetter) {
        boolean correctGuess = false;

        // Check if guessed letter is in the phrase
        for (int i = 0; i < this.phrase.length(); i++) {
            if (Character.toLowerCase(this.phrase.charAt(i)) == guessedLetter) {
                this.hiddenPhrase.setCharAt(i, this.phrase.charAt(i));
                correctGuess = true;
            }
        }

        if (correctGuess) {
            System.out.println("Correct! You earned 1 point for the letter " + guessedLetter + ".");
        } else {
            // Increment missedCount only if the guess is wrong
            missedCount++;
            System.out.println("Sorry, there was no occurrence of the letter " + guessedLetter + " in the hidden phrase.");
        }

        System.out.println("Guesses left: " + (this.maxGuessCount - this.missedCount));
        return correctGuess;
    }

    // Compares this WheelOfFortune instance to another object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WheelOfFortune that = (WheelOfFortune) o;
        return missedCount == that.missedCount && maxGuessCount == that.maxGuessCount && Objects.equals(phrase, that.phrase) && Objects.equals(hiddenPhrase, that.hiddenPhrase) && Objects.equals(previousGuesses, that.previousGuesses) && Objects.equals(phraseList, that.phraseList);
    }

    // Returns a string representation of the class state
    @Override
    public String toString() {
        return "WheelOfFortune{" +
                "phrase='" + phrase + '\'' +
                ", hiddenPhrase=" + hiddenPhrase +
                ", previousGuesses='" + previousGuesses + '\'' +
                ", missedCount=" + missedCount +
                ", maxGuessCount=" + maxGuessCount +
                ", phraseList=" + phraseList +
                '}';
    }

    // Abstract method to get a guess from the player
    public abstract char getGuess(String previousGuesses);
}