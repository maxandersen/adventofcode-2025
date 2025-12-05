///usr/bin/env jbang "$0" "$@" ; exit $?

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PrintingDepartmentB {
    /**
     * Checks if a roll of paper at position (row, col) can be accessed by a forklift.
     * A forklift can access a roll if there are fewer than 4 rolls in the 8 adjacent positions.
     */
    private boolean canAccessRoll(List<StringBuilder> grid, int row, int col) {
        int adjacentRolls = 0;
        
        // Check all 8 adjacent positions
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                // Skip the current position itself
                if (dr == 0 && dc == 0) {
                    continue;
                }
                
                int newRow = row + dr;
                int newCol = col + dc;
                
                // Check bounds
                if (newRow >= 0 && newRow < grid.size() && 
                    newCol >= 0 && newCol < grid.get(newRow).length()) {
                    if (grid.get(newRow).charAt(newCol) == '@') {
                        adjacentRolls++;
                    }
                }
            }
        }
        
        return adjacentRolls < 4;
    }
    
    /**
     * Removes all accessible rolls in one iteration.
     * Returns the number of rolls removed in this iteration.
     */
    private int removeAccessibleRolls(List<StringBuilder> grid) {
        List<int[]> toRemove = new ArrayList<>();
        
        // Find all rolls that can be accessed
        for (int row = 0; row < grid.size(); row++) {
            StringBuilder line = grid.get(row);
            for (int col = 0; col < line.length(); col++) {
                // Only check positions that have a roll
                if (line.charAt(col) == '@') {
                    if (canAccessRoll(grid, row, col)) {
                        toRemove.add(new int[]{row, col});
                    }
                }
            }
        }
        
        // Remove the accessible rolls
        for (int[] pos : toRemove) {
            grid.get(pos[0]).setCharAt(pos[1], '.');
        }
        
        return toRemove.size();
    }
    
    /**
     * Counts the total number of rolls that can be removed by iteratively
     * removing accessible rolls until no more can be removed.
     */
    public void solve() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<StringBuilder> grid = new ArrayList<>();
            
            // Read the grid
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                grid.add(new StringBuilder(line));
            }
            
            int totalRemoved = 0;
            
            // Keep removing accessible rolls until no more can be removed
            while (true) {
                int removedThisIteration = removeAccessibleRolls(grid);
                if (removedThisIteration == 0) {
                    // No more rolls can be removed
                    break;
                }
                totalRemoved += removedThisIteration;
            }
            
            System.out.println("Total rolls of paper that can be removed: " + totalRemoved);
        }
    }
    
    public static void main(String[] args) {
        new PrintingDepartmentB().solve();
    }
}

