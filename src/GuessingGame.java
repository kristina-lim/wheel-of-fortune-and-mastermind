import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

// Abstract lass that represents a guessing game, extending game
public abstract class GuessingGame extends Game {
    public String hiddenPhrase;

    // Handles play again logic
    public boolean playNext() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nDo you want to play again with another phrase? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        // Loop until a valid input ("yes" or "no") is provided
        while (!response.equals("yes") && !response.equals("no")) {
            System.out.print("\nInvalid input. Please enter 'yes' or 'no': ");
            response = scanner.nextLine().trim().toLowerCase();
        }

        return response.equals("yes");
    }

    // Generates random hidden phrase by selecting characters from given phrase
    public String getHiddenPhrase(String phrase, int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(phrase.charAt(random.nextInt(phrase.length())));
        }
        hiddenPhrase = code.toString();
        return hiddenPhrase;
    }

    // Compares GuessingGame instance to another object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuessingGame that = (GuessingGame) o;
        return Objects.equals(hiddenPhrase, that.hiddenPhrase);
    }

    // String representation of the GuessingGame object
    @Override
    public String toString() {
        return "GuessingGame{" +
                "hiddenPhrase='" + hiddenPhrase + '\'' +
                '}';
    }
}
