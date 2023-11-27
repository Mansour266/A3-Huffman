package Huffman;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Helper {

    private String inputFileName;

    private String outputFileName;

    private static String data;

    public static File getFile(String inputFileName) {
        File file = new File(inputFileName);
        return (file.exists() && file.isFile()) ? file : null;
    }

    public static String GetFileData(String fileName) {
        File file = getFile(fileName);
        String dataString = "";
        if (file == null) {
            System.out.println("File does not exist or is not a regular file.");
            return null;
        }
        StringBuilder data = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            dataString = data.toString();
            scanner.close();
            if (isBinaryString(dataString))
                dataString = convertFromBinary(dataString);
        } catch (IOException e) {
            e.printStackTrace();
        }
            setData(dataString);
            return dataString;

    }

    public static String convertFromBinary(String binary) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            String subBinary = binary.substring(i, i + 8);
            int ascii = Integer.parseInt(subBinary, 2);
            char character = (char) ascii;
            str.append(character);
        }
        return str.toString();
    }

    public static void WriteToFile(String fileName, String data, boolean isBinary) {
        try {
            if (fileName == null) {
                System.out.println("File name is null.");
                return;
            }

            File outputFile = new File(fileName);
            PrintWriter writer = new PrintWriter(outputFile);
            if (isBinary)
                data = convertToBinary(data);
            writer.print(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertToBinary(String str) {
        StringBuilder binary = new StringBuilder();
        for (char c : str.toCharArray()) {
            int ascii = (int) c;
            String binaryRepresentation = Integer.toBinaryString(ascii);
            while (binaryRepresentation.length() < 8) {
                binaryRepresentation = "0" + binaryRepresentation;
            }
            binary.append(binaryRepresentation);
        }
        return binary.toString();
    }
    public static boolean isBinaryString(String str) {
        String regex = "^[01]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
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

    public static String getData() {
        return data;
    }

    public static void setData(String data) {
        Helper.data = data;
    }

}
