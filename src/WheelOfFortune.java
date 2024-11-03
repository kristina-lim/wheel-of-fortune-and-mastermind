import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Abstract class for the Wheel of Fortune game, extends from Game class
public abstract class WheelOfFortune extends Game {
    protected String phrase;
    protected StringBuilder hiddenPhrase;
    protected String previousGuesses;
    protected int missedCount;
    protected int maxGuessCount;
    protected List<String> phraseList;
    private int totalScore;

    // Constructor
    public WheelOfFortune(int maxGuessCount) {
        this.phrase = "";
        this.hiddenPhrase = new StringBuilder();
        this.previousGuesses = "";
        this.missedCount = 0;
        this.maxGuessCount = maxGuessCount;
        this.totalScore = 0;
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
        this.phrase = phraseList.remove(index); // Remove chosen phrase
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

        // Check if letter has been guessed before, update prevGuesses, and increment one if guess is wrong
        if (this.previousGuesses.indexOf(guessedLetter) == -1) {
            this.previousGuesses += guessedLetter;
            if (!correctGuess) {
                this.missedCount++;
            }
        }

        // Check if letter is incorrect and update the missed count
        if (!correctGuess) {
            this.missedCount++;
            System.out.println("Guesses left: " + (this.maxGuessCount - this.missedCount));
            System.out.println("Sorry, there was no occurrence of the letter " + guessedLetter + " in the hidden phrase.");
        }

        return correctGuess;
    }

    // Abstract method to get a guess from the player
    public abstract char getGuess(String previousGuesses);

    // Main game loop
    @Override
    public GameRecord play() {
        // Reset previous guesses for each new game
        this.previousGuesses = "";
        this.missedCount = 0;

        System.out.println("Welcome to Wheel of Fortune! The rules are quite simple: ");
        System.out.println("1) You will have a maximum of " + maxGuessCount + " guesses to figure out the hidden phrase.");
        System.out.println("2) Only guess one letter at a time.");
        System.out.println("3) Have fun and good luck!\n");
        // Select a random phrase
        randomPhrase();
        int score = 0;

        // Continuous loop for the guessing process
        while (true) {
            System.out.println("Current Phrase: " + hiddenPhrase);
            System.out.print("Previous guesses: " + previousGuesses + "\n");
            // Get user guess
            char guessedLetter = getGuess(previousGuesses);
            // Process guess
            boolean correctGuess = processGuess(guessedLetter);

            // Check for game win condition
            if (hiddenPhrase.indexOf("*") == -1) {
                score = Math.max(0, maxGuessCount - missedCount);
                totalScore += score;
                System.out.println("Congratulations!");
                System.out.println("You guessed the phrase: " + this.phrase + ".");
                System.out.println("You've won with a score of: " + score);
                System.out.println("Your total score is now: " + totalScore);

                // Discard the used phrase
                phraseList.remove(phrase);
                break;
            }

            // Check for game over condition
            if (missedCount >= maxGuessCount) {
                System.out.println("Game over! The phrase was: " + phrase);
                break;
            }
        }

        return new GameRecord(score, "Player ID"); // Handle player ID logic as needed
    }

    // Method to ask if the user wants to play again
    @Override
    public boolean playNext() {
        // If no phrases left, no need to ask
        if (phraseList.isEmpty()) {
            return false;
        }
            return true; // Default implementation allows playing next
    }

}