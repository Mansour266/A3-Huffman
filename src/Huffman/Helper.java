package Huffman;

import java.io.*;
import java.util.*;
public class Helper {

    private String inputFileName;

    private String outputFileName;

    public static File getFile(String inputFileName) {
        File file = new File(inputFileName);
        return (file.exists() && file.isFile()) ? file : null;
    }

    public static boolean isBinary(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            int size = Math.min(inputStream.available(), 1024); // Read up to 1024 bytes for analysis
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            for (byte b : buffer) {
                if (b == 0) {
                    return true; // Null byte detected, likely a binary file
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String GetFileData(String fileName) {
        File file = getFile(fileName);

        if (file == null) {
            System.out.println("File does not exist or is not a regular file.");
            return null;
        }

        if (!isBinary(file)) {
            // If it's not a binary file, read the string and return
            return readTextFile(file);
        } else {
            // If it's a binary file, read the bits and convert to string
            return readBitsFromFile(file);
        }
    }

    public static void WriteToFile(String fileName, String data) {
        try {
            if (fileName == null) {
                System.out.println("File name is null.");
                return;
            }

            File outputFile = new File(fileName);

            // Use FileOutputStream with append mode set to false (overwrite)
            try (OutputStream outputStream = new FileOutputStream(outputFile, false)) {
                if (isBinary(outputFile)) {
                    writeBitsToFile(outputFile, data);
                } else {
                    writeTextFile(outputFile, data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readTextFile(File file) {
        StringBuilder data = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    private static String readBitsFromFile(File file) {
        StringBuilder bits = new StringBuilder();
        try {
            InputStream inputStream = new FileInputStream(file);
            int data;
            while ((data = inputStream.read()) != -1) {
                // Convert the byte to a binary string representation
                String binaryString = String.format("%8s", Integer.toBinaryString(data & 0xFF)).replace(' ', '0');
                bits.append(binaryString);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bits.toString();
    }

    private static void writeTextFile(File file, String data) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeBitsToFile(File file, String data) {
        try {
            OutputStream outputStream = new FileOutputStream(file);

            // Convert each 8 data to a byte and write to the output stream
            for (int i = 0; i < data.length(); i += 8) {
                String byteString = data.substring(i, Math.min(i + 8, data.length()));
                int byteValue = Integer.parseInt(byteString, 2);
                outputStream.write(byteValue);
            }

            outputStream.close();
        } catch (IOException e) {
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
