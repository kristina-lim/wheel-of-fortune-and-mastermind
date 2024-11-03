import java.util.Scanner;

// Allows the user to play each game with a random phrase, and if there are more phrases, ask after the game if the player wants to continue
public class WheelOfFortuneUserGame extends WheelOfFortune {
    // Constructor
    public WheelOfFortuneUserGame(int maxGuessCount) {
        super(maxGuessCount);
        // Read phrases only once in the constructor
        readPhrases();
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

    // Main game loop that handles playing WoF game
    @Override
    public GameRecord play() {
        System.out.println("Welcome to Wheel of Fortune! The rules are quite simple: ");
        System.out.println("1) You will have a maximum of " + maxGuessCount + " guesses to figure out the hidden phrase.");
        System.out.println("2) Only guess one letter at a time.");
        System.out.println("3) Have fun and good luck!\n");

        // Continue playing until there's no phrases left
        while (phraseList.size() > 0) {
            // Select random phrase for the round
            randomPhrase();
            // Initialize score for this round
            int score = 0;

            // Loop for guessing process
            while (true) {
                System.out.println("Current Phrase: " + hiddenPhrase);
                System.out.print("Previous guesses: " + previousGuesses + "\n");
                // Get guess from the user
                char guessedLetter = getGuess(previousGuesses);
                // Process the guessed letter
                processGuess(guessedLetter);

                // Check if the game is won
                if (hiddenPhrase.indexOf("*") == -1) {
                    // Calculate score
                    score = Math.max(0, maxGuessCount - missedCount);
                    System.out.println("You've won with a score of: " + score);
                    // Remove used phrase from the list
                    phraseList.remove(phrase);
                    break;
                }
            }
        }

        System.out.println("No more phrases available. Thank you for playing!");
        // Return game record
        return new GameRecord(0, "getPlayerId"); // Handle player ID logic as needed
    }

    // Method to ask if the user wants to play again
    @Override
    public boolean playNext() {
       // Loop continuously to ask if the user wants to play again
         while (true) {
             // Start a new game
             play();
             // Check if the user wants to continue playing
             if(!playNext()) {
                 // Exit if the user chooses not to play
                 break;
             }
         }

         // If there's no more phrases, return false
         if (phraseList.isEmpty()) {
             return false;
         }

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

