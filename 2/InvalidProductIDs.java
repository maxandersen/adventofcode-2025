///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.google.guava:guava:33.0.0-jre

import java.util.Scanner;

class InvalidProductIDs {
    /**
     * Checks if a number is an invalid product ID.
     * An invalid ID is made of a sequence of digits repeated twice.
     * Examples: 55 (5 twice), 6464 (64 twice), 123123 (123 twice)
     */
    private boolean isInvalidID(long id) {
        String idStr = String.valueOf(id);
        int len = idStr.length();
        
        // Must have even length to be split into two equal halves
        if (len % 2 != 0) {
            return false;
        }
        
        // Split into two halves
        int halfLen = len / 2;
        String firstHalf = idStr.substring(0, halfLen);
        String secondHalf = idStr.substring(halfLen);
        
        // Check if both halves are equal
        return firstHalf.equals(secondHalf);
    }
    
    /**
     * Finds all invalid IDs in a given range [start, end] (inclusive).
     */
    private long findInvalidIDsInRange(long start, long end) {
        long sum = 0;
        for (long id = start; id <= end; id++) {
            if (isInvalidID(id)) {
                sum += id;
            }
        }
        return sum;
    }
    
    /**
     * Parses the input line and processes all ranges.
     * Input format: "start-end,start-end,..."
     */
    public void solve() {
        try (Scanner scanner = new Scanner(System.in)) {
            // Read the entire line of ranges
            String input = scanner.nextLine().trim();
            
            // Split by comma to get individual ranges
            String[] ranges = input.split(",");
            
            long totalSum = 0;
            
            for (String range : ranges) {
                range = range.trim();
                if (range.isEmpty()) {
                    continue;
                }
                
                // Split by dash to get start and end
                String[] parts = range.split("-");
                if (parts.length != 2) {
                    System.err.println("Invalid range format: " + range);
                    continue;
                }
                
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                
                long rangeSum = findInvalidIDsInRange(start, end);
                totalSum += rangeSum;
            }
            
            System.out.println("Sum of all invalid product IDs: " + totalSum);
        }
    }
    
    public static void main(String[] args) {
        new InvalidProductIDs().solve();
    }
}

