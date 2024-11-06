import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

// Abstract class for the Wheel of Fortune game, extends from Game class
public abstract class WheelOfFortune extends Game {
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

        // If it's a correct guess, add the points to the score
        if (correctGuess) {
            System.out.println("Correct! You earned 1 point for the letter " + guessedLetter + ".");
        }

        // Check if letter has been guessed before, update prevGuesses, and increment one if guess is wrong
        if (this.previousGuesses.indexOf(guessedLetter) == -1) {
            this.previousGuesses += guessedLetter;
            if (!correctGuess) {
                this.missedCount++;
            }
        }

        // Check if letter is incorrect and update the missed count
        if (!correctGuess) {
            System.out.println("Guesses left: " + (this.maxGuessCount - this.missedCount));
            System.out.println("Sorry, there was no occurrence of the letter " + guessedLetter + " in the hidden phrase.");
        }
        return correctGuess;
    }

    // Abstract method to get a guess from the player
    public abstract char getGuess(String previousGuesses);
}