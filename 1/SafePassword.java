///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.google.guava:guava:33.0.0-jre

import java.util.Scanner;

class SafePassword {
    // The dial has 100 positions (0 through 99)
    private static final int MODULO = 100;
    
    // The current position of the dial (starts at 50)
    private int currentPosition = 50;
    
    // The counter for how many times the dial lands on 0
    private int zeroCount = 0;

    /**
     * Reads rotation inputs and calculates the final password.
     * The input is read from standard input.
     */
    public void solve() {
        Scanner scanner = new Scanner(System.in);

        // Process each line of rotation input until the input stream ends
        while (scanner.hasNextLine()) {
            String rotation = scanner.nextLine().trim();
            if (rotation.isEmpty()) {
                continue; // Skip empty lines
            }
            
            // The first character is the direction ('L' or 'R')
            char direction = rotation.charAt(0);
            
            // The rest of the string is the distance
            int distance = Integer.parseInt(rotation.substring(1));
            
            // Perform the rotation and update the counter
            performRotation(direction, distance);
        }

        System.out.println("The actual password (number of times the dial lands on 0) is: " + zeroCount);
    }

    /**
     * Updates the dial position based on the rotation and checks if it landed on 0.
     * * @param direction 'L' for left (lower numbers), 'R' for right (higher numbers)
     * @param distance The number of clicks to rotate
     */
    private void performRotation(char direction, int distance) {
        // Calculate the new raw position (can be negative or > 99)
        int newRawPosition;

        if (direction == 'R') {
            // Right rotation: add distance
            newRawPosition = currentPosition + distance;
        } else if (direction == 'L') {
            // Left rotation: subtract distance
            newRawPosition = currentPosition - distance;
        } else {
            // Should not happen based on problem description
            throw new IllegalArgumentException("Invalid rotation direction: " + direction);
        }

        // --- Modular Arithmetic for Circular Movement ---
        // 1. Calculate the remainder with MODULO (100).
        //    For Java, the result of '%' for a negative number can be negative, 
        //    e.g., (-5) % 100 = -5.
        // 2. To ensure the result is always non-negative (0-99), 
        //    we add MODULO and then take the modulo again.
        //    (e.g., (-5 + 100) % 100 = 95 % 100 = 95)
        currentPosition = (newRawPosition % MODULO + MODULO) % MODULO;

        // Check if the new position is 0
        if (currentPosition == 0) {
            zeroCount++;
        }
    }

    public static void main(String[] args) {
        new SafePassword().solve();
    }
}