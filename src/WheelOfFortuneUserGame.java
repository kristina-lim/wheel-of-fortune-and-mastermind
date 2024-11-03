import java.util.Scanner;

// Allows the user to play each game with a random phrase, and if there are more phrases, ask after the game if the player wants to continue
public class WheelOfFortuneUserGame extends WheelOfFortune {
    // Constructor
    public WheelOfFortuneUserGame(int maxGuessCount) {
        super(maxGuessCount);
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

