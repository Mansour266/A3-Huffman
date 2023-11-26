package Huffman;

import java.io.File;
import java.util.*;
public class Helper {

    private String inputFileName;

    private String outputFileName;

    public static File getFile(String inputFileName) {
        File file = new File(inputFileName);
        return (file.exists() && file.isFile()) ? file : null;
    }

    public static String GetFileData(File file) {
        String data = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data += scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void WriteToFile(String fileName, String data) {
        try {
            Formatter formatter = new Formatter(fileName);
            formatter.format("%s", data);
            formatter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

}
