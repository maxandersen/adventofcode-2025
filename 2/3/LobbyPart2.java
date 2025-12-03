///usr/bin/env jbang "$0" "$@" ; exit $?

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class LobbyPart2 {
    private static final int BATTERIES_TO_SELECT = 12;
    
    /**
     * Find the maximum 12-digit joltage for a bank by selecting exactly 12 batteries.
     * Uses a greedy algorithm: at each position, select the largest digit possible
     * while ensuring we can still select enough digits to reach 12 total.
     * 
     * @param bank String of digits representing battery joltages
     * @return Maximum 12-digit number that can be formed
     */
    private long findMaxJoltage(String bank) {
        int[] digits = bank.chars().map(c -> c - '0').toArray();
        int n = digits.length;
        int k = BATTERIES_TO_SELECT;
        
        // We need to select k digits from n digits to form the largest number
        // Use greedy approach: select digits that maximize the result
        StringBuilder result = new StringBuilder();
        int startPos = 0;
        int remainingToSelect = k;
        
        for (int selected = 0; selected < k; selected++) {
            // We need to select remainingToSelect digits
            // We can look from startPos to (n - remainingToSelect) inclusive
            int endPos = n - remainingToSelect;
            
            // Find the maximum digit in the allowed range
            int maxDigit = -1;
            int maxPos = -1;
            for (int i = startPos; i <= endPos; i++) {
                if (digits[i] > maxDigit) {
                    maxDigit = digits[i];
                    maxPos = i;
                }
            }
            
            // Select this digit
            result.append(maxDigit);
            startPos = maxPos + 1;
            remainingToSelect--;
        }
        
        return Long.parseLong(result.toString());
    }
    
    /**
     * Read input file and calculate total output joltage.
     * 
     * @param inputFile Path to input file
     * @return Total output joltage (sum of max joltages from each bank)
     */
    private long solve(String inputFile) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(inputFile));
        long total = 0;
        
        for (String line : lines) {
            String bank = line.trim();
            if (!bank.isEmpty()) {
                long maxJolt = findMaxJoltage(bank);
                total += maxJolt;
            }
        }
        
        return total;
    }
    
    /**
     * Test with example from problem description.
     */
    private void testExample() {
        System.out.println("Testing with example:");
        String[] examples = {
            "987654321111111",
            "811111111111119",
            "234234234234278",
            "818181911112111"
        };
        
        long[] expected = {
            987654321111L,
            811111111119L,
            434234234278L,
            888911112111L
        };
        
        long exampleTotal = 0;
        for (int i = 0; i < examples.length; i++) {
            String bank = examples[i];
            long maxJolt = findMaxJoltage(bank);
            System.out.println("  " + bank + ": " + maxJolt + " (expected: " + expected[i] + ")");
            exampleTotal += maxJolt;
        }
        System.out.println("  Total: " + exampleTotal);
        System.out.println("  Expected: 3121910778619");
        System.out.println();
    }
    
    public void run(String[] args) {
        // Test with example
        testExample();
        
        // Solve with actual input
        String inputFile = args.length > 0 ? args[0] : "3.input";
        try {
            long result = solve(inputFile);
            System.out.println("Total output joltage: " + result);
        } catch (IOException e) {
            System.err.println("Error reading input file '" + inputFile + "': " + e.getMessage());
            System.err.println("Please provide the input file or download it from adventofcode.com");
        }
    }
    
    public static void main(String[] args) {
        new LobbyPart2().run(args);
    }
}

