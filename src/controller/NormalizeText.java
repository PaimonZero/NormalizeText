package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.ReadAndWriteFile;
import view.Validation;

public class NormalizeText {
    private ReadAndWriteFile currentFile = new ReadAndWriteFile();
    private Validation val = new Validation();
    
    public void loadData() {
        try {
            FileReader fr = new FileReader(currentFile.getInputFile());
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                if(line.isBlank() || line.isEmpty()) continue;
                currentFile.getData().add(line);                //thỏa mãn đk thì add vào
            }
            } catch (IOException | NumberFormatException e) {
            System.err.println("Some thing wrong!");
        }
    }
    
    public void writeData() {
        try{ 
            FileWriter fw = new FileWriter(currentFile.getOutputFile());     //FileWriter fw = new FileWriter(filename, true); để viết thêm vào file mà ko xóa dữ liệu
            BufferedWriter bw = new BufferedWriter(fw);
        //Có thể rút gọn 2 dòng trên thành:
        //  BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (String stringData : currentFile.getData()) {
                bw.write(stringData);       
                // bw.newLine();                //Vì bài này chỉ viết 1 đoạn, ko xuống dòng nên loại bỏ hàm này
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println("Can't find that file!");
        }
    }
    
    public void normalizeText() {
        boolean isFirstWordInLine = true;
        boolean isInQuotes = false;
        boolean capitalizeNextWord = false;
        String line;

        for (int n = 0; n < currentFile.getData().size(); n++) {
            line = currentFile.getData().get(n); // Lấy câu
            StringBuilder result = new StringBuilder();
            if (line.isBlank() || line.isEmpty()) {
                continue;
            }

            // Normalize words and sentences in the line
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.isBlank() || word.isEmpty()) {
                    continue;
                }

                if (word.equals(",") || word.equals(".") || word.equals(":")
                        || word.equals("!") || word.equals("?")) {
                    // Loại bỏ dấu câu (vì đã thêm vào từ vòng lặp trước)
                    continue;
                }

                if ((i + 1) < words.length) { // Xử lý trường hợp từ tiếp theo là dấu câu ,.:
                    String wordAfter = words[i + 1];
                    if (wordAfter.equals(",") || wordAfter.equals(".") || wordAfter.equals(":")
                        || wordAfter.equals("!") || wordAfter.equals("?")) {
                        word = word + wordAfter; // Gắn dấu câu vào từ trước
                    }
                }

                if (isInQuotes) {
                    // Remove spaces inside quotes
                    result.append(word);
                    if (i < words.length - 1) {
                        result.append(" ");
                    }
                } else {
                    // Normalize words outside quotes
                    if (isFirstWordInLine) {
                        // First word in the first line
                        result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
                        isFirstWordInLine = false;
                    } else {
                        // Subsequent words
                        if (capitalizeNextWord) {
                            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
                            capitalizeNextWord = false;
                        } else {
                            result.append(word.toLowerCase());
                        }
                    }

                    if (i == (words.length - 1) && n == (currentFile.getData().size() - 1)) {
                        // Add a period if it's the last word in the last line
                        result.append(".");
                        capitalizeNextWord = true;      //vì là tự thêm nên set lại cờ
                    } else {
                        result.append(" ");
                    }

                    // Check for Uppercase next
                    if (word.contains(".") || word.contains(":")
                        || word.contains("!") || word.contains("?")) {
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
            // Gán kết quả vào dữ liệu của line
            currentFile.getData().set(n, result.toString());
        }
    }
    
    public void getPathFromUser() {
        String path = val.getString(">Enter your path input: ");
        String pathO = val.getString(">Enter your path output: ");
        currentFile.setInputFile(path);
        currentFile.setOutputFile(pathO);
        System.out.println("================================================================");
    }
    
}
