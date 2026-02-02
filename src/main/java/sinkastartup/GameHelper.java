package sinkastartup;

import java.util.*;

/*
 * GameHelper is a utility / helper class.
 * Its responsibilities are:
 * 1. Taking user input
 * 2. Managing the game grid
 * 3. Randomly placing Startups on the grid
 * 4. Converting numeric grid positions into user-friendly coordinates (a0, b3, etc.)
 *
 * This class intentionally hides all grid math from the rest of the game.
 */
public class GameHelper {

    // Letters used for column labels (a–g for a 7-column grid)
    private static final String ALPHABET = "abcdefg";

    // Grid configuration constants
    private static final int GRID_LENGTH = 7;     // number of columns
    private static final int GRID_SIZE = 49;      // 7 × 7 grid
    private static final int MAX_ATTEMPTS = 200;  // safety limit to avoid infinite loops

    /*
     * Direction increments:
     * - HORIZONTAL_INCREMENT moves right by 1 column
     * - VERTICAL_INCREMENT moves down by one full row
     *
     * NOTE: An enum would be cleaner, but constants are used for simplicity.
     */
    static final int HORIZONTAL_INCREMENT = 1;
    static final int VERTICAL_INCREMENT = GRID_LENGTH;

    // Represents the grid; 0 = empty, 1 = occupied
    private final int[] grid = new int[GRID_SIZE];

    // Random number generator for placing startups
    private final Random random = new Random();

    // Keeps track of how many startups have been placed so far
    private int startupCount = 0;

    /*
     * Prompts the user and reads input from the console.
     * Input is converted to lowercase for consistent comparison.
     */
    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();
    }

    /*
     * Randomly places a Startup of given size on the grid.
     *
     * Algorithm:
     * 1. Decide orientation (horizontal or vertical)
     * 2. Try random starting positions
     * 3. Check if the Startup fits and doesn't overlap others
     * 4. Save the position once a valid placement is found
     */
    public ArrayList<String> placeStartup(int startupSize) {

        // Stores proposed grid indexes (0–48)
        int[] startupCoords = new int[startupSize];

        int attempts = 0;           // number of attempts made
        boolean success = false;    // did we find a valid position?

        startupCount++;             // count this Startup
        int increment = getIncrement(); // decide placement direction

        /*
         * Try random locations until a valid one is found
         * or maximum attempts is reached
         */
        while (!success && attempts++ < MAX_ATTEMPTS) {

            // Random starting cell
            int location = random.nextInt(GRID_SIZE);

            // Build candidate coordinates
            for (int i = 0; i < startupCoords.length; i++) {
                startupCoords[i] = location;
                location += increment;
            }

            System.out.println("Trying: " + Arrays.toString(startupCoords));

            // Check boundaries and collisions
            if (startupFits(startupCoords, increment)) {
                success = coordsAvailable(startupCoords);
            }
        }

        // Mark final position on the grid
        savePositionToGrid(startupCoords);

        // Convert numeric positions to alphanumeric format
        ArrayList<String> alphaCells =
                convertCoordsToAlphaFormat(startupCoords);

        System.out.println("Placed at: " + alphaCells);
        return alphaCells;
    }

    /*
     * Checks whether the Startup stays within grid boundaries.
     */
    boolean startupFits(int[] startupCoords, int increment) {

        int finalLocation = startupCoords[startupCoords.length - 1];

        if (increment == HORIZONTAL_INCREMENT) {
            // Horizontal: ensure it stays in the same row
            return calcRowFromIndex(startupCoords[0]) ==
                    calcRowFromIndex(finalLocation);
        } else {
            // Vertical: ensure it doesn't fall off the grid bottom
            return finalLocation < GRID_SIZE;
        }
    }

    /*
     * Checks whether all proposed coordinates are free.
     */
    boolean coordsAvailable(int[] startupCoords) {
        for (int coord : startupCoords) {
            if (grid[coord] != 0) {
                System.out.println("position: " + coord + " already taken.");
                return false;
            }
        }
        return true;
    }

    /*
     * Marks chosen grid positions as occupied.
     */
    void savePositionToGrid(int[] startupCoords) {
        for (int index : startupCoords) {
            grid[index] = 1;
        }
    }

    /*
     * Converts numeric grid indexes into "a0"-style coordinates.
     */
    private ArrayList<String> convertCoordsToAlphaFormat(int[] startupCoords) {

        ArrayList<String> alphaCells = new ArrayList<>();

        for (int index : startupCoords) {
            String alphaCoords = getAlphaCoordsFromIndex(index);
            alphaCells.add(alphaCoords);
        }
        return alphaCells;
    }

    /*
     * Converts a single grid index into an alphanumeric coordinate.
     * Example: index 15 → "b2"
     */
    String getAlphaCoordsFromIndex(int index) {

        int row = calcRowFromIndex(index);
        int column = index % GRID_LENGTH;

        String letter =
                ALPHABET.substring(column, column + 1);

        return letter + row;
    }

    /*
     * Calculates the row number from a grid index.
     */
    private int calcRowFromIndex(int index) {
        return index / GRID_LENGTH;
    }

    /*
     * Alternates placement direction:
     * - Even-numbered Startups → horizontal
     * - Odd-numbered Startups → vertical
     */
    private int getIncrement() {
        if (startupCount % 2 == 0) {
            return HORIZONTAL_INCREMENT;
        } else {
            return VERTICAL_INCREMENT;
        }
    }
}
