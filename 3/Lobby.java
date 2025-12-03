///usr/bin/env jbang "$0" "$@" ; exit $?

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Lobby {
    /**
     * Find the maximum two-digit joltage for a bank.
     * The joltage is formed by selecting two digits in order
     * (first digit must appear before second digit).
     * 
     * @param bank String of digits representing battery joltages
     * @return Maximum two-digit number that can be formed
     */
    private int findMaxJoltage(String bank) {
        int[] digits = bank.chars().map(c -> c - '0').toArray();
        int maxJoltage = 0;
        
        // Try all pairs (i, j) where i < j
        for (int i = 0; i < digits.length; i++) {
            for (int j = i + 1; j < digits.length; j++) {
                int joltage = digits[i] * 10 + digits[j];
                maxJoltage = Math.max(maxJoltage, joltage);
            }
        }
        
        return maxJoltage;
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
                int maxJolt = findMaxJoltage(bank);
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
        
        long exampleTotal = 0;
        for (String bank : examples) {
            int maxJolt = findMaxJoltage(bank);
            System.out.println("  " + bank + ": " + maxJolt);
            exampleTotal += maxJolt;
        }
        System.out.println("  Total: " + exampleTotal);
        System.out.println("  Expected: 357");
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
        new Lobby().run(args);
    }
}

