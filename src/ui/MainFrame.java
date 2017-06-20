package ui;

import Network.Network;
import utils.Tools;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame{

    public Network network;
    public JLabel paintResult;

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
        testingPanel.setLayout(new BoxLayout(testingPanel, BoxLayout.PAGE_AXIS));
        testingPanel.setPreferredSize(new Dimension(375, getHeight()));
        testingPanel.setBackground(Color.decode("#e0e0e0"));
        JLabel testingResult1 = new JLabel("");
        JLabel testingResult2 = new JLabel("");
        JLabel testingResult3 = new JLabel("");
        JButton runTestButton = new JButton("Run \"pendigits.tes\"");
        runTestButton.addActionListener(e -> {
            try {
                runTestButton.setEnabled(false);
                double[][] input = Tools.readFromFile("res/pendigits.tes");
                String[] output = network.testData(input).split(" ");
                testingResult1.setText("Number of correct digits : " + output[0]);
                testingResult2.setText("Number of incorrect digits : " + output[1]);
                testingResult3.setText("Model correctness " + output[2]);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        runTestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testingPanel.add(runTestButton);
        testingResult1.setAlignmentX(Component.CENTER_ALIGNMENT);
        testingPanel.add(testingResult1);
        testingResult2.setAlignmentX(Component.CENTER_ALIGNMENT);
        testingPanel.add(testingResult2);
        testingResult3.setAlignmentX(Component.CENTER_ALIGNMENT);
        testingPanel.add(testingResult3);
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
        this.paintResult = new JLabel("");
        paintPanel.add(this.paintResult, "South");
        return paintPanel;
    }
}
