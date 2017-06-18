package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoadingDialog {
    private static JFrame frame;
    private static JProgressBar progressBar;

    public static void show() {
        frame = new JFrame("Loading please wait..");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Container content = frame.getContentPane();
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Teaching...");
        progressBar.setBorder(border);
        content.add(progressBar, BorderLayout.NORTH);
        frame.setSize(300, 100);
        frame.setVisible(true);
    }

    public static void changeBarValue(int newValue){
        progressBar.setValue(newValue);
        //progressBar.updateUI();
    }

    public static int getBarValue(){
        return progressBar.getValue();
    }

    public static void hide() {
        frame.setVisible(false);
        frame.dispose();
    }
}
