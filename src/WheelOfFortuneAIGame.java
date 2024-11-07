import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class WheelOfFortuneAIGame extends WheelOfFortune {
    private List<WheelOfFortunePlayer> players; // List to hold players (AI or otherwise)
    private List<GameRecord> allGamesRecord;
    private List<String> usedPhrases;
    private int currentPlayerIndex = 0;

    // Default constructor with a default AI player
    public WheelOfFortuneAIGame() {
        super(3);  // Calls WheelOfFortune's constructor, max guesses set to 3
        this.players = new ArrayList<>();
        this.players.add(new RandomAIPlayer("RandomBot"));  // Default AI player
        this.players.add(new HungryAIPlayer("HungryBot"));
        this.players.add(new SimpleAIPlayer("SimpleBot"));
        this.allGamesRecord = new ArrayList<>();  // Initialize the allGamesRecord list
        this.usedPhrases = new ArrayList<>(); // Initialize usedPhrases list
        readPhrases();
    }

    // Constructor that accepts a single AI player
    public WheelOfFortuneAIGame(WheelOfFortunePlayer player) {
        super(3);  // Calls WheelOfFortune's constructor, max guesses set to 3
        this.players = new ArrayList<>();
        this.players.add(player);  // Adds the specified AI player
        this.allGamesRecord = new ArrayList<>();  // Initialize the allGamesRecord list
        this.usedPhrases = new ArrayList<>(); // Initialize usedPhrases list
        readPhrases();
    }

    // Constructor that accepts a list of AI players
    public WheelOfFortuneAIGame(List<WheelOfFortunePlayer> players) {
        super(3);  // Calls WheelOfFortune's constructor, max guesses set to 3
        this.players = players;  // Initializes the players list with the provided list
        this.allGamesRecord = new ArrayList<>();  // Initialize the allGamesRecord list
        this.usedPhrases = new ArrayList<>(); // Initialize usedPhrases list
        readPhrases();
    }

    @Override
    public char getGuess(String previousGuesses) {
        // Just get the next guess from the first player (you could extend to multiple players if needed)
        WheelOfFortunePlayer player = players.get(0);  // In this case, it's a single AI player
        return player.nextGuess();
    }

    // Method to select a new phrase for the bot without repetition
    @Override
    protected void randomPhrase() {
        if (phraseList.isEmpty()) return;

        // If all phrases have been used, reset the list of used phrases
        if (usedPhrases.size() == phraseList.size()) {
            usedPhrases.clear();
        }

        Random rand = new Random();
        int index;
        String selectedPhrase;

        // Select a new unused phrase
        do {
            index = rand.nextInt(phraseList.size());
            selectedPhrase = phraseList.get(index);
        } while (usedPhrases.contains(selectedPhrase)); // Avoid previously used phrase

        this.phrase = selectedPhrase; // Assign the selected phrase
        usedPhrases.add(selectedPhrase); // Track the used phrase
        getHiddenPhrase();
    }

    @Override
    public GameRecord play() {
        this.previousGuesses = "";  // Reset previous guesses before starting the game
        int playerScore = 0;
        String playerId = "";

        // Loop through all players and make them play 3 rounds
        for (int i = 0; i < 3; i++) {
            WheelOfFortunePlayer player = players.get(currentPlayerIndex);
            playerId = player.playerId();
            player.reset();  // Reset the player's state before starting a new game
            randomPhrase();   // Pick a new phrase
            System.out.println("\n\nNew phrase for this round: " + phrase);
            playerScore = 0;
            this.previousGuesses = "";
            this.missedCount = 0;
            this.hiddenPhrase = new StringBuilder();
            getHiddenPhrase();  // Ensure you start with the correct hidden phrase

            System.out.println("Game " + (i + 1) + " for " + player.playerId());

            // Run the game loop until the player has guessed the phrase or missed all guesses
            while (missedCount < maxGuessCount) {
                System.out.println("Current Phrase: " + hiddenPhrase);
                System.out.println("Previous guesses: " + previousGuesses);

                // AI guesses the next letter
                char guessedLetter = player.nextGuess();

                // Process the AI's guess
                boolean correctGuess = processGuess(guessedLetter);

                // If the guess was correct, update the score
                if (correctGuess) {
                    playerScore++; // Increment score for each correct guess
                }

                // Check if the game is won
                if (hiddenPhrase.indexOf("*") == -1) {
                    System.out.println("AI Player " + player.playerId() + " guessed the phrase: " + this.phrase);
                    System.out.println("Score for this game: " + playerScore);
                    break;
                }
            }

            // Game over condition: if the AI misses too many guesses
            if (missedCount >= maxGuessCount) {
                System.out.println("AI Player " + player.playerId() + " failed to guess the phrase: " + phrase);
                System.out.println("Game over! The phrase was: " + phrase + "\n");
            }

            // After 3 games, record the player's total score
            GameRecord record = new GameRecord(playerScore, player.playerId());
            allGamesRecord.add(record); // Add the result to the AllGamesRecord
        }
        return new GameRecord(playerScore, playerId);
    }

    // Increment the counter, stop the game when it reaches the end of the players list
    @Override
    public boolean playNext() {
        currentPlayerIndex++;  // Move to the next player
        if (currentPlayerIndex >= players.size()) {
            return false;  // Stop the game if all players have played
        }
        return true;
    }

    public static void main(String [] args) {
        // Define players
        List<WheelOfFortunePlayer> players = new ArrayList<>();
        players.add(new RandomAIPlayer("RandomBot"));
        players.add(new HungryAIPlayer("HungryBot"));
        players.add(new SimpleAIPlayer("SimpleBot"));

        // Create game instance and play all 9 games
        WheelOfFortuneAIGame wofAIGame = new WheelOfFortuneAIGame(players);
        AllGamesRecord allGamesRecord = wofAIGame.playAll();

        // Display results
        System.out.println("High Game List for each player:");
        for (WheelOfFortunePlayer player : players) {
            String playerId = player.playerId();
            List<GameRecord> highScores = allGamesRecord.highGameList(3, playerId); // Getting top 3 scores for each player
            System.out.println(playerId + " High Scores: " + highScores);
        }

        System.out.println("Average score of all 9 games: " + allGamesRecord.average());

        System.out.println("Average score for each player:");
        for (WheelOfFortunePlayer player : players) {
            System.out.println(player.playerId() + " Average: " + allGamesRecord.average(player.playerId()));
        }
    }
}
