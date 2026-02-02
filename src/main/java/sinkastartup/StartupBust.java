package sinkastartup;

import java.util.ArrayList;

/*
 * StartupBust is the main controller class of the game.
 * It is responsible for:
 * 1. Setting up the game (creating Startups and assigning locations)
 * 2. Running the game loop (taking user guesses)
 * 3. Tracking number of guesses
 * 4. Ending the game and showing results
 */
public class StartupBust {

    // Helper object used for user input and random startup placement
    private GameHelper helper = new GameHelper();

    // List that holds all active Startup objects in the game
    private ArrayList<Startup> startups = new ArrayList<>();

    // Counts how many guesses the user has made
    private int numOfGuesses = 0;

    /*
     * Sets up the game:
     * - Creates Startup objects
     * - Assigns them names
     * - Places them at random locations on the grid
     */
    private void setUpGame() {

        // Create Startup objects
        Startup one = new Startup();
        one.setName("Adi");

        Startup two = new Startup();
        two.setName("Golu");

        Startup three = new Startup();
        three.setName("Amore");

        // Add all startups to the ArrayList
        startups.add(one);
        startups.add(two);
        startups.add(three);

        // Display game instructions
        System.out.println("Your goal is to sink three Startups.");
        System.out.println("Adi, Golu, Amore");
        System.out.println("Try to sink them all in the fewest number of guesses!!");

        /*
         * For each Startup:
         * - Ask GameHelper to generate a valid random location of size 3
         * - Assign that location to the Startup
         */
        for (Startup startup : startups) {
            ArrayList<String> newLocation = helper.placeStartup(3);
            startup.setLocationCells(newLocation);
        }
    }

    /*
     * Main game loop:
     * - Continues until all Startups are removed (i.e., sunk)
     * - Takes user input and checks the guess
     */
    private void startPlaying() {
        while (!startups.isEmpty()) {
            String userGuess = helper.getUserInput("Enter a guess: ");
            checkUserGuess(userGuess);
        }

        // Once all startups are sunk, end the game
        finishGame();
    }

    /*
     * Processes a single user guess:
     * - Increments guess count
     * - Checks the guess against each Startup
     * - Removes a Startup if it is killed
     */
    private void checkUserGuess(String userGuess) {

        // Increment total number of guesses
        numOfGuesses++;

        // Default result assumes a miss
        String result = "miss";

        /*
         * Loop through all remaining startups and test the guess.
         * Stop checking as soon as a "hit" or "kill" occurs.
         */
        for (Startup startupToTest : startups) {

            result = startupToTest.checkYourself(userGuess);

            // If it's a hit, no need to check other startups
            if (result.equals("hit")) {
                break;
            }

            // If it's a kill, remove the startup from the list
            if (result.equals("kill")) {
                startups.remove(startupToTest);
                break;
            }
        }

        // Display result of the guess
        System.out.println(result);
    }

    /*
     * Displays the final game result based on performance
     */
    private void finishGame() {

        System.out.println("All Startups are dead!! Your stock is now worthless.");

        // Good performance case
        if (numOfGuesses <= 18) {
            System.out.println("It only took you " + numOfGuesses + " guesses.");
            System.out.println("You got out before your options sank.");
        }
        // Poor performance case
        else {
            System.out.println("Took you long enough. " + numOfGuesses + " guesses.");
            System.out.println("Fish are dancing with your options");
        }
    }

    /*
     * Program entry point:
     * - Creates the game object
     * - Sets up the game
     * - Starts gameplay
     */
    public static void main(String[] args) {
        StartupBust game = new StartupBust();
        game.setUpGame();
        game.startPlaying();
    }
}
