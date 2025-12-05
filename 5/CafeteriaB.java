///usr/bin/env jbang "$0" "$@" ; exit $?

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class CafeteriaB {
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
         * Returns the number of IDs in this range (inclusive).
         */
        long size() {
            return end - start + 1;
        }
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
     * Merges overlapping and adjacent ranges, then calculates the total coverage.
     */
    private long calculateTotalCoverage(List<Range> ranges) {
        if (ranges.isEmpty()) {
            return 0;
        }
        
        // Sort ranges by start value
        ranges.sort(Comparator.comparingLong(r -> r.start));
        
        // Merge overlapping/adjacent ranges
        List<Range> merged = new ArrayList<>();
        Range current = ranges.get(0);
        
        for (int i = 1; i < ranges.size(); i++) {
            Range next = ranges.get(i);
            
            // Check if ranges overlap or are adjacent (next.start <= current.end + 1)
            if (next.start <= current.end + 1) {
                // Merge: extend current range to cover both
                current = new Range(current.start, Math.max(current.end, next.end));
            } else {
                // No overlap: save current and start a new one
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);
        
        // Calculate total coverage by summing sizes of merged ranges
        long total = 0;
        for (Range range : merged) {
            total += range.size();
        }
        
        return total;
    }
    
    /**
     * Processes the database file and counts all unique ingredient IDs
     * that are considered fresh according to the ranges.
     * Input format:
     * - List of ranges (e.g., "3-5")
     * - Blank line (we stop reading here)
     * - List of ingredient IDs (ignored in Part 2)
     */
    public void solve() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<Range> ranges = new ArrayList<>();
            
            // Read ranges until we hit a blank line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                
                if (line.isEmpty()) {
                    // Blank line separates ranges from ingredient IDs
                    // In Part 2, we only care about ranges, so we can stop here
                    break;
                }
                
                // Parse and store the range
                ranges.add(parseRange(line));
            }
            
            // Calculate total coverage using interval merging
            long totalFresh = calculateTotalCoverage(ranges);
            
            System.out.println("Total ingredient IDs considered fresh: " + totalFresh);
        }
    }
    
    public static void main(String[] args) {
        new CafeteriaB().solve();
    }
}

