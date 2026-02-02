package sinkastartup;

import java.util.ArrayList;

/*
 * Startup represents a single Startup in the game.
 * Each Startup:
 * - Has a name
 * - Occupies multiple locations on the grid
 * - Can be hit, missed, or completely killed
 */
public class Startup {

    // Stores all grid locations occupied by this Startup
    // Example: ["A3", "A4", "A5"]
    private ArrayList<String> locationCells;

    // Name of the Startup (used for messages)
    private String name;

    /*
     * Sets the location cells for this Startup.
     * Called once during game setup.
     */
    public void setLocationCells(ArrayList<String> loc) {
        locationCells = loc;
    }

    /*
     * Sets the name of the Startup.
     */
    public void setName(String n) {
        name = n;
    }

    /*
     * Checks the user's guess against this Startup.
     *
     * Logic:
     * 1. Assume the guess is a "miss"
     * 2. Check if the guessed cell exists in locationCells
     * 3. If found:
     *    - Remove that cell (it has been hit)
     *    - If no cells remain → "kill"
     *    - Otherwise → "hit"
     */
    public String checkYourself(String userInput) {

        // Default result if the guess does not match any location
        String result = "miss";

        // Find the index of the guessed cell in locationCells
        int index = locationCells.indexOf(userInput);

        // index >= 0 means the guess was a hit
        if (index >= 0) {

            // Remove the hit location so it can't be hit again
            locationCells.remove(index);

            // If no locations are left, the Startup is sunk
            if (locationCells.isEmpty()) {
                result = "kill";
                System.out.println("Ouch! You sunk " + name + " :(");
            }
            // Otherwise, it's just a hit
            else {
                result = "hit";
            }
        }

        // Return the result to StartupBust
        return result;
    }
}
