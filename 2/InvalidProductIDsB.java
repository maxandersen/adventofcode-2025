///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.google.guava:guava:33.0.0-jre

import java.util.Scanner;

class InvalidProductIDsB {
    /**
     * Checks if a number is an invalid product ID.
     * An invalid ID is made of a sequence of digits repeated at least twice.
     * Examples: 12341234 (1234 two times), 123123123 (123 three times), 
     *           1212121212 (12 five times), 1111111 (1 seven times)
     */
    private boolean isInvalidID(long id) {
        String idStr = String.valueOf(id);
        int len = idStr.length();
        
        // Try different pattern lengths from 1 to len/2
        // We need at least 2 repetitions, so pattern length can be at most len/2
        for (int patternLen = 1; patternLen <= len / 2; patternLen++) {
            // Check if len is divisible by patternLen
            if (len % patternLen != 0) {
                continue;
            }
            
            // Extract the pattern (first patternLen digits)
            String pattern = idStr.substring(0, patternLen);
            
            // Check if the entire string is made of repetitions of this pattern
            boolean isValidPattern = true;
            int numRepetitions = len / patternLen;
            
            for (int i = 1; i < numRepetitions; i++) {
                int startIdx = i * patternLen;
                String currentSegment = idStr.substring(startIdx, startIdx + patternLen);
                if (!currentSegment.equals(pattern)) {
                    isValidPattern = false;
                    break;
                }
            }
            
            if (isValidPattern && numRepetitions >= 2) {
                return true;
            }
        }
        
        return false;
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
        new InvalidProductIDsB().solve();
    }
}

