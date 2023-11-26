import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import Huffman.*;
public class MainFrame {

    private JFrame frame;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel label1;

    private Huffman huffman;

    private Helper helper = new Helper();

    public MainFrame() {
        initialize();
    }
    private void initialize() {

        frame = new JFrame();
        frame.setTitle("Huffman Coding Program");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);


        panel1 = panel1();
        panel2 = panel2();
        panel3 = panel3();

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.add(panel3, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    public JPanel panel1(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.GRAY);
        frame.add(panel, BorderLayout.NORTH);

        label1 = new JLabel("Welcome to the Huffman Coding Program!");
        panel.add(label1);
        frame.setLayout(new BorderLayout());
        label1.setForeground(Color.WHITE);

        label1.setFont(new Font("Sans-serif", Font.BOLD, 24));

        return panel;
    }
    private JPanel panel2() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton button1 = new JButton("Compress");
        JButton button2 = new JButton("Decompress");

        button1.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        button2.setFont(new Font("Sans-serif", Font.PLAIN, 14));

        button1.setMargin(new Insets(10, 10, 10, 10));
        button2.setMargin(new Insets(10, 10, 10, 10));

        // Set the correct preferred size for button1
        button1.setPreferredSize(new Dimension(150, 50));
        // Set the correct preferred size for button2
        button2.setPreferredSize(new Dimension(150, 50));

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String encodedText = huffman.encode();
                Helper.WriteToFile(helper.getOutputFileName(), encodedText);
                JOptionPane.showMessageDialog(frame, "File compressed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String encodedText = helper.GetFileData(helper.getFile(helper.getInputFileName()));
                String decodedText = huffman.decode(encodedText);
                Helper.WriteToFile(helper.getOutputFileName(), decodedText);
                JOptionPane.showMessageDialog(frame, "File decompressed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
        });

        // Add a rigid area to increase the distance between buttons to 30 pixels
        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(button1);
        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(button2);

        return panel;
    }
    private JPanel panel3(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));

        // Input panel with left-aligned labels
        JPanel inputPanel = createFormPanel(true);

        // Output panel with left-aligned labels
        JPanel outputPanel = createFormPanel(false);

        // Add all panels to the main panel
        panel.add(inputPanel);
        panel.add(outputPanel);

        return panel;
    }
    private JPanel createFormPanel(boolean operation){
        // Output panel with left-aligned labels
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.X_AXIS));

        JLabel label2;
        if(operation)
            label2 = new JLabel("Enter name of input file with extension:    ");
        else
            label2 = new JLabel("Enter name of output file with extension: ");


        label2.setFont(new Font("Sans-serif", Font.BOLD, 12));
        JTextField TextField = new JTextField(20);
        TextField.setMaximumSize(new Dimension(200, 25));

        outputPanel.add(label2);
        outputPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Add 5 pixels spacing
        outputPanel.add(TextField);

        JButton button = new JButton("Submit");
        button.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        button.setMargin(new Insets(5, 5, 5, 5));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String fileName = TextField.getText();

                //Check if input file exists
                if(operation) {
                    if (helper.getFile(fileName) == null) {
                        JOptionPane.showMessageDialog(frame, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    helper.setInputFileName(fileName);
                    String data = helper.GetFileData(helper.getFile(fileName));
                    huffman = new Huffman(data);
                }
                else
                    helper.setOutputFileName(fileName);


            }

        });

        outputPanel.add(Box.createHorizontalGlue()); // Space to the right of the text fields
        outputPanel.add(button);

        return outputPanel;
    }


}