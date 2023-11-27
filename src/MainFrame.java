import java.awt.*;
import javax.swing.*;

import Huffman.*;
public class MainFrame {
    private JFrame frame;
    private Huffman huffman;
    private final Helper helper;

    public MainFrame() {
        initialize();
        helper = new Helper();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Huffman Coding Program");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        JPanel welcomeMessagePanel = initializeWelcomeMessagePanel();
        JPanel actionButtonsPanel = initializeActionButtonsPanel();
        JPanel inputsPanel = initializeInputsPanel();

        frame.add(welcomeMessagePanel, BorderLayout.NORTH);
        frame.add(inputsPanel, BorderLayout.CENTER);
        frame.add(actionButtonsPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public JPanel initializeWelcomeMessagePanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.GRAY);
        frame.add(panel, BorderLayout.NORTH);

        JLabel message = new JLabel("Welcome to the Huffman Coding Program!");
        panel.add(message);
        frame.setLayout(new BorderLayout());
        message.setForeground(Color.WHITE);

        message.setFont(new Font("Sans-serif", Font.BOLD, 24));

        return panel;
    }

    private JPanel initializeActionButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton compressButton = new JButton("Compress");
        JButton decompressButton = new JButton("Decompress");

        compressButton.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        decompressButton.setFont(new Font("Sans-serif", Font.PLAIN, 14));

        compressButton.setMargin(new Insets(10, 10, 10, 10));
        decompressButton.setMargin(new Insets(10, 10, 10, 10));

        // Set the correct preferred size for compressButton
        compressButton.setPreferredSize(new Dimension(150, 50));
        // Set the correct preferred size for decompressButton
        decompressButton.setPreferredSize(new Dimension(150, 50));

        initializeActionButtonListeners(compressButton, decompressButton);

        // Add a rigid area to increase the distance between buttons to 30 pixels
        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(compressButton);
        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(decompressButton);

        return panel;
    }

    private void initializeActionButtonListeners(JButton compressButton, JButton decompressButton) {
        compressButton.addActionListener(e -> {
            String encodedText = huffman.encode();
            Helper.WriteToFile(helper.getOutputFileName(), encodedText);
            JOptionPane.showMessageDialog(frame, "File compressed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        decompressButton.addActionListener(e -> {
            String encodedText = Helper.GetFileData(helper.getInputFileName());
            String decodedText = huffman.decode(encodedText);
            Helper.WriteToFile(helper.getOutputFileName(), decodedText);
            JOptionPane.showMessageDialog(frame, "File decompressed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private JPanel initializeInputsPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));

        // Input panel with left-aligned labels
        JPanel inputFilePanel = createFormPanel(true);

        // Output panel with left-aligned labels
        JPanel outputFilePanel = createFormPanel(false);

        // Add all panels to the main panel
        panel.add(inputFilePanel);
        panel.add(outputFilePanel);

        return panel;
    }

    private JButton initializeSubmitButton(JTextField TextField, boolean operation) {
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        submitButton.setMargin(new Insets(5, 5, 5, 5));

        submitButton.addActionListener(e -> {
            String fileName = TextField.getText();

            //Check if input file exists
            if(operation) {
                if (Helper.getFile(fileName) == null) {
                    JOptionPane.showMessageDialog(frame, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                helper.setInputFileName(fileName);
                String data = Helper.GetFileData(fileName);
                huffman = new Huffman(data);
            } else
                helper.setOutputFileName(fileName);
        });
        return submitButton;
    }

    private JPanel createFormPanel(boolean operation){
        // Output panel with left-aligned labels
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.X_AXIS));

        String operationType  = (operation) ? "input" : "output";
        JLabel label2 = new JLabel("Enter name of " + operationType + " file with extension: " + ((operation) ? "   " : ""));

        label2.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        JTextField TextField = new JTextField(20);
        TextField.setMaximumSize(new Dimension(200, 25));

        outputPanel.add(label2);
        outputPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Add 5 pixels spacing
        outputPanel.add(TextField);

        outputPanel.add(Box.createHorizontalGlue()); // Space to the right of the text fields
        outputPanel.add(initializeSubmitButton(TextField, operation));

        return outputPanel;
    }
}
