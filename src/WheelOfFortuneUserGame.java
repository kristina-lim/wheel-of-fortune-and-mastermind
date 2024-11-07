import java.util.*;

// Allows the user to play each game with a random phrase, and if there are more phrases, ask after the game if the player wants to continue
public class WheelOfFortuneUserGame extends WheelOfFortune {
    private int score;
    private String username;
    private String previousGuesses;
    private List<String> usedPhrases;

    // Constructor
    public WheelOfFortuneUserGame(int maxGuessCount) {
        super(maxGuessCount);
        this.score = score;
        this.previousGuesses = "";
        this.usedPhrases = new ArrayList<>(); // Initialize usedPhrases list
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

    // Method to ensure phrases don't get repeated for the user
    @Override
    protected void randomPhrase() {
        if (phraseList.isEmpty()) return;

        // If all phrases have been used, reset the list of used phrases
        if (usedPhrases.size() == phraseList.size()) {
            usedPhrases.clear();
        }
        Random rand = new Random();
        int index;
        do {
            index = rand.nextInt(phraseList.size());
        } while (usedPhrases.contains(phraseList.get(index))); // Avoid previously used phrase

        this.phrase = phraseList.remove(index); // Remove the chosen phrase from the list
        usedPhrases.add(this.phrase); // Track the used phrase
        getHiddenPhrase();
    }

    // Main game loop
    @Override
    public GameRecord play() {
        this.previousGuesses = "";
        this.missedCount = 0;
        this.score = 0;

        // Prompt for username if it's not set
        if (username == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username: ");
            username = scanner.nextLine().trim();
        }

        System.out.println("\nWelcome to Wheel of Fortune! The rules are quite simple: ");
        System.out.println("1) You will have a maximum of " + maxGuessCount + " guesses to figure out the hidden phrase.");
        System.out.println("2) Only guess one letter at a time.");
        System.out.println("3) For each correct guessed letter, you earn one point.");
        System.out.println("4) Have fun and good luck!\n");

        // Select a random phrase
        randomPhrase();

        // Continuous loop for the guessing process
        while (true) {
            System.out.println("Current Phrase: " + hiddenPhrase);
            System.out.print("Previous guesses: " + previousGuesses + "\n");
            // Get user guess
            char guessedLetter = getGuess(previousGuesses);
            // Process guess
            boolean correctGuess = processGuess(guessedLetter);

            // If the guess is correct, add 1 point to score
            if (correctGuess) {
                score++;
            }

            // Check for game win condition
            if (hiddenPhrase.indexOf("*") == -1) {
                System.out.println("Congratulations!");
                System.out.println("You guessed the phrase: " + this.phrase + ".");
                System.out.println("You've won with a score of: " + score);

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
        return new GameRecord(score, username); // Handle player ID logic as needed
    }

    // Method to ask if the user wants to play again
    @Override
    public boolean playNext() {
        return super.playNext();
    }

    // Compares this WheelOfFortuneUserGame object with another object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WheelOfFortuneUserGame that = (WheelOfFortuneUserGame) o;
        return score == that.score && Objects.equals(username, that.username) && Objects.equals(previousGuesses, that.previousGuesses) && Objects.equals(usedPhrases, that.usedPhrases);
    }

    // Returns a string representation of the WheelOfFortune object
    @Override
    public String toString() {
        return "WheelOfFortuneUserGame{" +
                "score=" + score +
                ", username='" + username + '\'' +
                ", previousGuesses='" + previousGuesses + '\'' +
                ", usedPhrases=" + usedPhrases +
                '}';
    }

    // Main method to run the program
    public static void main(String [] args) {
        WheelOfFortuneUserGame wofUserGame = new WheelOfFortuneUserGame(3);
        AllGamesRecord record = wofUserGame.playAll();
        System.out.println(record);
        // After playing the games, display the high game list and average score
        System.out.println("High Game List for the top gamers:");
        List<GameRecord> highGames = record.highGameList(wofUserGame.score);
        for (GameRecord game : highGames) {
            System.out.println(game);
        }
        System.out.println("\nAverage score for all games played: " + record.average());
    }
}
