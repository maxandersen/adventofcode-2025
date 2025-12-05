///usr/bin/env jbang "$0" "$@" ; exit $?

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PrintingDepartment {
    /**
     * Checks if a roll of paper at position (row, col) can be accessed by a forklift.
     * A forklift can access a roll if there are fewer than 4 rolls in the 8 adjacent positions.
     */
    private boolean canAccessRoll(List<String> grid, int row, int col) {
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
     * Counts how many rolls of paper can be accessed by forklifts.
     */
    public void solve() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<String> grid = new ArrayList<>();
            
            // Read the grid
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                grid.add(line);
            }
            
            int accessibleCount = 0;
            
            // Check each position in the grid
            for (int row = 0; row < grid.size(); row++) {
                String line = grid.get(row);
                for (int col = 0; col < line.length(); col++) {
                    // Only check positions that have a roll
                    if (line.charAt(col) == '@') {
                        if (canAccessRoll(grid, row, col)) {
                            accessibleCount++;
                        }
                    }
                }
            }
            
            System.out.println("Number of rolls of paper accessible by forklifts: " + accessibleCount);
        }
    }
    
    public static void main(String[] args) {
        new PrintingDepartment().solve();
    }
}

