package ui;

import Network.Network;
import utils.FileReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame{
    public Network network;
    public JLabel paintResult;
    public JLabel testingResult;

    public MainFrame(Network network) {
        this.network = network;
        this.setDefaultCloseOperation(3);
        this.setPreferredSize(new Dimension(750, 400));
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        this.initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initComponents() {
        this.add(this.createPaintBox(), BorderLayout.WEST);
        this.add(this.createTestingBox(), BorderLayout.EAST);
        this.pack();
    }

    private JPanel createTestingBox() {
        JPanel testingPanel = new JPanel();
        testingPanel.setLayout(new BorderLayout());
        testingPanel.setPreferredSize(new Dimension(375, getHeight()));
        testingPanel.setBackground(Color.decode("#e0e0e0"));
        this.testingResult = new JLabel("The result of testing the neural network : ");
        JButton runTestButton = new JButton("Run \"pendigits.tes\"");
        runTestButton.addActionListener(e -> {
            try {
                double[][] input = FileReader.readFromFile("res/pendigits.tes");
                String output = network.testData(input);
                testingResult.setText(testingResult.getText() + "\n" + output);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(testingPanel.getBackground());
        buttonPanel.add(runTestButton, "Center");
        testingPanel.add(buttonPanel, "North");
        testingPanel.add(this.testingResult, "Center");
        return testingPanel;
    }

    private JPanel createPaintBox() {
        JPanel paintPanel = new JPanel();
        paintPanel.setLayout(new BorderLayout());
        paintPanel.setPreferredSize(new Dimension(375, getHeight()));
        paintPanel.setBackground(Color.decode("#eeeeee"));
        paintPanel.add(new JLabel("Draw your digit", SwingConstants.CENTER), "North");
        JPanel drawPanel = new JPanel();
        drawPanel.add(new PaintArea(this), "Center");
        paintPanel.add(drawPanel, "Center");
        this.paintResult = new JLabel("Result = ");
        paintPanel.add(this.paintResult, "South");
        return paintPanel;
    }
}
