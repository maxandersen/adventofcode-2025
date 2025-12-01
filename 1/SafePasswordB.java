///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.google.guava:guava:33.0.0-jre

import java.util.Scanner;

class SafePasswordB {
    // The dial has 100 positions (0 through 99)
    private static final int MODULO = 100;
    
    // The current position of the dial (starts at 50)
    private int currentPosition = 50;
    
    // The counter for how many times the dial lands on 0 (including during rotations)
    private int zeroCount = 0;

    /**
     * Reads rotation inputs and calculates the final password using method 0x434C49434B.
     * This method counts every time the dial points at 0, including during rotations.
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
            
            // Perform the rotation and count all times it passes through 0
            performRotation(direction, distance);
        }

        scanner.close();
        System.out.println("The actual password (number of times the dial points at 0, including during rotations) is: " + zeroCount);
    }

    /**
     * Updates the dial position based on the rotation and checks if it lands on 0
     * during each click of the rotation, not just at the end.
     * @param direction 'L' for left (lower numbers), 'R' for right (higher numbers)
     * @param distance The number of clicks to rotate
     */
    private void performRotation(char direction, int distance) {
        // For each click in the rotation, check if we pass through 0
        for (int i = 0; i < distance; i++) {
            if (direction == 'R') {
                // Right rotation: move to next higher number
                currentPosition = (currentPosition + 1) % MODULO;
            } else if (direction == 'L') {
                // Left rotation: move to next lower number
                // For left, we need to handle wrapping: (currentPosition - 1 + MODULO) % MODULO
                currentPosition = (currentPosition - 1 + MODULO) % MODULO;
            } else {
                // Should not happen based on problem description
                throw new IllegalArgumentException("Invalid rotation direction: " + direction);
            }
            
            // Check if the dial is pointing at 0 after this click
            if (currentPosition == 0) {
                zeroCount++;
            }
        }
    }

    public static void main(String[] args) {
        new SafePasswordB().solve();
    }
}

