package model;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadAndWriteFile {

    private ArrayList<String> data = new ArrayList<>();
    private String path = Paths.get("").toAbsolutePath().toString();   //lấy địa chỉ tuyệt đối
    private String inputFile = path + "/src/executed/input.txt";       //thêm địa chỉ đuôi
    private String outputFile = path + "/src/executed/output.txt";
    private Scanner sc = new Scanner(System.in);

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> dataInput) {
        this.data = dataInput;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

}
