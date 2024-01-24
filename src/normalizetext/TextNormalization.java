
package normalizetext;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextNormalization {
    public static void main(String[] args) {
        try {
            normalizeText("input.txt", "output.txt");
            System.out.println("Text normalization completed successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void normalizeText(String inputFileName, String outputFileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {

            boolean isFirstWordInLine = true;
            boolean isInQuotes = false;
            String line;

            while ((line = reader.readLine()) != null) {
                // Normalize words and sentences in the line
                String[] words = line.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];

                    if (isInQuotes) {
                        // Remove spaces inside quotes
                        writer.write(word);
                        if (i < words.length - 1) {
                            writer.write(" ");
                        }
                    } else {
                        // Normalize words outside quotes
                        if (i == 0 && isFirstWordInLine) {
                            // First word in the first line
                            writer.write(Character.toUpperCase(word.charAt(0)) + word.substring(1));
                        } else {
                            // Subsequent words
                            writer.write(word.toLowerCase());
                        }

                        if (i < words.length - 1) {
                            // Add space after each word (except the last one)
                            writer.write(" ");
                        }
                    }

                    // Check for quotes
                    if (word.startsWith("\"")) {
                        isInQuotes = true;
                    }
                    if (word.endsWith("\"")) {
                        isInQuotes = false;
                    }
                }

                // Set flag for the first word in the line
                isFirstWordInLine = false;

                // Write new line character
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new IOException("Error reading or writing file: " + e.getMessage());
        }
    }
}
