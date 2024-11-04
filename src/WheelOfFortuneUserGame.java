import java.util.Scanner;

// Allows the user to play each game with a random phrase, and if there are more phrases, ask after the game if the player wants to continue
public class WheelOfFortuneUserGame extends WheelOfFortune {
    private int totalScore;

    // Constructor
    public WheelOfFortuneUserGame(int maxGuessCount) {
        super(maxGuessCount);
        this.totalScore = 0;
    }

    // Method to get a valid letter guess from user
    @Override
    public char getGuess(String previousGuesses) {
        Scanner scanner = new Scanner(System.in);
        char guessedLetter;

        // Loop until valid guess is true
        while (true) {
            System.out.println("Guesses left: " + (this.maxGuessCount - this.missedCount));
            System.out.print("Enter a letter to guess: ");
            String input = scanner.nextLine().trim().toLowerCase();

            // Validate the input to ensure it's a single letter
            if (input.length() != 1) {
                System.out.println("Please enter a single letter.");
                continue;
            }

            // Get the first character of the input
            guessedLetter = input.charAt(0);

            // Check if the guessed letter is a valid lower case letter
            if (guessedLetter < 'a' || guessedLetter > 'z') {
                System.out.println("Invalid input. Please enter a letter.");
                continue;
            }

            // Check if the letter has already been guessed
            if (previousGuesses.indexOf(guessedLetter) != -1) {
                System.out.println("Guesses left: " + (this.maxGuessCount - this.missedCount));
                System.out.println("You've already guessed that letter. Try again.");
                continue;
            }

            // Add valid guess to previous guesses
            this.previousGuesses += guessedLetter;
            break;
        }
        return guessedLetter;
    }

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

        return new GameRecord(totalScore, "Player ID"); // Handle player ID logic as needed
    }

    // Method to ask if the user wants to play again
    @Override
    public boolean playNext() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play again with another phrase? (yes/no): ");
        // Get user's response
        String response = scanner.nextLine().trim().toLowerCase();
        // Return true if the user wants to play again
        return response.equals("yes");
    }

    // Main method to run the program
    public static void main(String [] args) {
        WheelOfFortuneUserGame wofUserGame = new WheelOfFortuneUserGame(3);
        AllGamesRecord record = wofUserGame.playAll();
        System.out.println(record);
    }
}

