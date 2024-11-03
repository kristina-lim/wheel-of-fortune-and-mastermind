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

    // Constructor
    public WheelOfFortune(int maxGuessCount) {
        this.phrase = "";
        this.hiddenPhrase = new StringBuilder();
        this.previousGuesses = "";
        this.missedCount = 0;
        this.maxGuessCount = maxGuessCount;
    }

    // Reads phrases from text file
    public void readPhrases() {
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println("Error reading phrases file.");
        }
    }

    // Chooses a random phrase from the phrase list and initializes hidden phrase
    protected void randomPhrase() {
        readPhrases();
        Random rand = new Random();
        int index = rand.nextInt(3);
        this.phrase = phraseList.get(index);
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

        // Check if the game is won or lost
        if (this.hiddenPhrase.indexOf("*") == -1) {
            System.out.println("Congratulations!");
            System.out.println("You guessed the phrase: " + this.phrase + ".");
            System.exit(0);
        } else if (correctGuess) {
            System.out.println("Nice! The letter " + guessedLetter + " is present in the hidden phrase!");
        } else {
            this.missedCount++;
            System.out.println("Guesses left: " + (this.maxGuessCount - this.missedCount));
            System.out.println("Sorry, there was no occurrence of the letter " + guessedLetter + " in the hidden phrase.");
        }

        // Check for game over
        if (this.missedCount >= this.maxGuessCount) {
            System.out.println("Womp womp! GAME OVER. The phrase was: " + this.phrase + ".");
            System.exit(0);
        }
        return correctGuess;
    }

    // Abstract method to get a guess from the player
    public abstract char getGuess(String previousGuesses);

    // Main game loop
    @Override
    public GameRecord play() {
        randomPhrase();
        int score = 0;

        // Continuous loop for the guessing process
        while (true) {
            System.out.println("Current Phrase: " + hiddenPhrase);
            System.out.print("Previous guesses: " + previousGuesses + "\n");
            // Get player's guess
            char guessedLetter = getGuess(previousGuesses);
            // Process guess
            processGuess(guessedLetter);

            // Check if game is won
            if (hiddenPhrase.indexOf("*") == -1) {
                score = Math.max(0, maxGuessCount - missedCount);
                break;
            }
        }

        return new GameRecord(score, "getPlayerId");
    }

    // Method to prompt the player to play again
    @Override
    public boolean playNext() {
        // Check if there's no more phrases to play
        if (phraseList.isEmpty()) {
            return false;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes");
    }
}