# Wheel of Fortune and Mastermind
#### My project is based on the Wheel of Fortune and the Mastermind game. The Wheel of Fortune has a hierarchy, where the Game is the grandparent and the rest of the classes are its descendants.

#### In my Wheel of Fortune game, you can either play or have three different AI players attempt to guess the phrase.
     AI Players:
     - Random AI: Guesses letters randomly.
     - Simple AI: Guesses vowels, and after that's all been guessed, they guess other letters randomly.
     - Hungry AI: Guesses available letters and doesn't guess the same letter again.
#### The Mastermind game refactors the Wheel of Fortune so that the similarities between the games are codified in a superclass, Guessing Game, where it shares methods that would be used in the Wheel of Fortune and Mastermind classes.
