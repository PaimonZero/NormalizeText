
package normalizetext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class fixx {
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
            boolean capitalizeNextWord = false;
            String line;

            while ((line = reader.readLine()) != null) {
                if(line.isBlank() || line.isEmpty()) continue;
                // Normalize words and sentences in the line
                String[] words = line.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    if(word.isBlank() || word.isEmpty()) continue;
                    
                    if(word.equals(",") || word.equals(".") || word.equals(":")){
                        continue;                       //loại bỏ dấu câu(vì đã thêm vào từ vòng lặp trc)
                    }
                    if((i+1) < words.length) {          //Xử lý trường hợp từ tiếp theo là dấu câu ,.:
                        String wordAfter = words[i+1];
                        if(wordAfter.equals(",") || wordAfter.equals(".") || wordAfter.equals(":") 
                                || wordAfter.equals("?") || wordAfter.equals("!")){
                            word = word + wordAfter;        //gắn dấu câu vào từ trước
                        }
                    }
                    
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
                            if (capitalizeNextWord) {
                                writer.write(Character.toUpperCase(word.charAt(0)) + word.substring(1));
                                capitalizeNextWord = false;
                            } else {
                                writer.write(word.toLowerCase());
                            }
                        }

                        if (i < words.length - 1) {
                            // Add space after each word (except the last one)
                            writer.write(" ");
                        }

                        // Check for Uppercase next
                        if (word.endsWith(":") || word.endsWith(".") || word.endsWith("!") || word.endsWith("?")) {
                            capitalizeNextWord = true;
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
