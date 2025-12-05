///usr/bin/env jbang "$0" "$@" ; exit $?

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cafeteria {
    /**
     * Represents a range of fresh ingredient IDs (inclusive).
     */
    private static class Range {
        final long start;
        final long end;
        
        Range(long start, long end) {
            this.start = start;
            this.end = end;
        }
        
        /**
         * Checks if an ingredient ID falls within this range (inclusive).
         */
        boolean contains(long id) {
            return id >= start && id <= end;
        }
    }
    
    /**
     * Checks if an ingredient ID is fresh (falls into any of the ranges).
     */
    private boolean isFresh(long id, List<Range> ranges) {
        for (Range range : ranges) {
            if (range.contains(id)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Parses a range string in the format "start-end".
     */
    private Range parseRange(String rangeStr) {
        String[] parts = rangeStr.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid range format: " + rangeStr);
        }
        long start = Long.parseLong(parts[0]);
        long end = Long.parseLong(parts[1]);
        return new Range(start, end);
    }
    
    /**
     * Processes the database file and counts fresh ingredient IDs.
     * Input format:
     * - List of ranges (e.g., "3-5")
     * - Blank line
     * - List of ingredient IDs (e.g., "1", "5", "8")
     */
    public void solve() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<String> allLines = new ArrayList<>();
            
            // Read all lines first
            while (scanner.hasNextLine()) {
                allLines.add(scanner.nextLine());
            }
            
            List<Range> ranges = new ArrayList<>();
            List<Long> ingredientIDs = new ArrayList<>();
            boolean foundBlankLine = false;
            
            // Process lines: ranges before blank line, IDs after
            for (String line : allLines) {
                if (line.trim().isEmpty()) {
                    foundBlankLine = true;
                    continue;
                }
                
                if (!foundBlankLine) {
                    // This is a range
                    ranges.add(parseRange(line.trim()));
                } else {
                    // This is an ingredient ID
                    ingredientIDs.add(Long.parseLong(line.trim()));
                }
            }
            
            // Count how many ingredient IDs are fresh
            int freshCount = 0;
            for (long id : ingredientIDs) {
                if (isFresh(id, ranges)) {
                    freshCount++;
                }
            }
            
            System.out.println("Number of fresh ingredient IDs: " + freshCount);
        }
    }
    
    public static void main(String[] args) {
        new Cafeteria().solve();
    }
}

