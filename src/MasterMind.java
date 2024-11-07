import java.util.*;

// Mastermind game where the player tries to guess the hidden code
public class MasterMind extends GuessingGame {
    private static final String COLORS = "RGBYOP"; // Colors that can be used in the code
    private static final int SIZE = 4; // The number of color slots in the code
    private String hiddenCode;

    // Constructor
    public MasterMind() {
        getHiddenPhrase();
    }

    // Generates a random hidden code using the superclass method
    public void getHiddenPhrase() {
        hiddenCode = super.getHiddenPhrase(COLORS, SIZE);
    }

    // Prompts the player to input a guess for the hidden code
    public String getGuess() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter guess (e.g., RGRY): ");
        String input = scanner.nextLine().toUpperCase().trim();

        while (!isGuessValid(input)) {
            System.out.println("Invalid guess. Use only the following letters: R, G, B, Y, O, P.");
            System.out.print("Enter your guess (e.g., RGRY): ");
            input = scanner.nextLine().toUpperCase().trim();
        }
        return input;
    }

    // Validates the player's guess to ensure it has the correct guess
    private boolean isGuessValid(String guess) {
        if (guess.length() != SIZE) {
            return false;
        }
        for (char color : guess.toCharArray()) {
            if (!COLORS.contains(String.valueOf(color))) {
                return false;
            }
        }
        return true;
    }

    // Restarts the game
    private void restartGame() {
        getHiddenPhrase();
    }

    // Main game play
    @Override
    public GameRecord play() {
        System.out.println("\nWelcome to MasterMind! The rules are quite simple: ");
        System.out.println("1) Guess the hidden code. There are 6 possible colors to guess from: R, G, B, Y, O, P.");
        System.out.println("2) The code contains 4 colors. Best of luck!");

        int numberOfAttempts = 0;

        while (true) {
            String playerGuess = getGuess();
            numberOfAttempts++;

            int exactMatchCount = count(playerGuess);
            int partialMatchCount = countPartial(playerGuess) - exactMatchCount;

            System.out.println("Score count: " + exactMatchCount + " exact matches, " + partialMatchCount + " partial matches.");

            if (exactMatchCount == SIZE) {
                System.out.println("Good job! You cracked the code in " + numberOfAttempts + " attempts.");
                break;
            }
        }

        return new GameRecord(101 - numberOfAttempts, "Player");
    }

    // Asks player if they want to play again
    @Override
    public boolean playNext() {
        boolean playNext = super.playNext();
        if (playNext) {
            restartGame();
        }
        return playNext;
    }

    // Counts the number of exact matches between player's guess and the hidden code
    private int count(String guess) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (guess.charAt(i) == hiddenCode.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    // Counts the number of partial matches between player's guess and the hidden code
    private int countPartial(String guess) {
        int partialCount = 0;
        Map<Character, Integer> hiddenCodeMap = createFrequencyMap(hiddenCode);
        Map<Character, Integer> guessMap = createFrequencyMap(guess);

        for (char color : COLORS.toCharArray()) {
            if (guessMap.containsKey(color) && hiddenCodeMap.containsKey(color)) {
                partialCount += Math.min(guessMap.get(color), hiddenCodeMap.get(color));
            }
        }
        return partialCount;
    }

    // Creates a frequency map for a given string, mapping each character to its occurrence count in the string
    private Map<Character, Integer> createFrequencyMap(String input) {
        // Create a new HashMap to store the frequency of each character
        Map<Character, Integer> frequencyMap = new HashMap<>();
        // Iterate over each character in the input string
        for (char color : input.toCharArray()) {
            // For each character, update its frequency in the map
            frequencyMap.put(color, frequencyMap.getOrDefault(color, 0) + 1);         // `getOrDefault(color, 0)` returns the current count of the character, or 0 if it's not in the map yet
        }
        // Return the frequency map after processing all characters
        return frequencyMap;
    }

    // Compares this MasterMind instance to another object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterMind that = (MasterMind) o;
        return Objects.equals(hiddenCode, that.hiddenCode);
    }

    // Returns a string representation of the Mastermind object
    @Override
    public String toString() {
        return "MasterMind{" +
                "hiddenCode='" + hiddenCode + '\'' +
                '}';
    }

    // Main method to run the program
    public static void main(String[] args) {
        MasterMind game = new MasterMind();
        AllGamesRecord allGameRecords = game.playAll();
        System.out.println(allGameRecords.highGameList(2));
        System.out.println("Average Score: " + allGameRecords.average());
        System.out.println(allGameRecords);
    }
}