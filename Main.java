import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:/Users/rafin/OneDrive/Documents/hadoop100MB.txt"; 

        // Create a map to store word counts
        Map<String, Integer> wordCounts = new HashMap<>();

        // Start the timer
        long startTime = System.nanoTime();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Remove punctuation and convert to lowercase
                String cleanedLine = line.replaceAll("[^a-zA-Z ]", "").toLowerCase();

                // Split the line into individual words
                String[] words = cleanedLine.split("\\s+");

                // Count the occurrences of each word
                for (String word : words) {
                    if (wordCounts.containsKey(word)) {
                        // If the word is already present, increment its count
                        int count = wordCounts.get(word);
                        wordCounts.put(word, count + 1);
                    } else {
                        // Otherwise, add the word with a count of 1
                        wordCounts.put(word, 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stop the timer
        long endTime = System.nanoTime();

        // Calculate the execution time
        long executionTime = endTime - startTime;

        // Print the word counts
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            System.out.println(word + "\t" + count);
        }

        // Print the execution time
        System.out.println("Execution time: " + executionTime + " nanoseconds");
    }
}
