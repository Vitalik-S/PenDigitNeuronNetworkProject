package ui;

import utils.Pair;
import utils.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class PaintArea extends JPanel {
    private MainFrame parent;
    private ArrayList<Pair<Integer, Integer>> digitVector;
    private ArrayList<Pair<Integer, Integer>> resArr;

    public PaintArea(MainFrame parent){
        this.parent = parent;
        setPreferredSize(new Dimension(100, 100));
        setMaximumSize(new Dimension(100, 100));
        digitVector = new ArrayList<>();
        initListeners();
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        digitVector.forEach(i -> g.drawRect(i.getX(), 100 - i.getY(), 1, 1));
    }

    private void initListeners(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = getMousePosition();
                if (point != null){
                    Pair<Integer, Integer> coordinates = Pair.createPair(point.x, 100 - point.y);
                    if (!digitVector.contains(coordinates))
                        digitVector.add(coordinates);
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                if (digitVector.size() > 8) {
                    resArr = Tools.compressVector(digitVector);
                    int result = parent.network.recognize(Tools.listToDoubleArr(resArr));
                    parent.paintResult.setText("Result = " + result);
                    digitVector.clear();
                    repaint();
                }else {
                    JOptionPane.showMessageDialog(parent, "Please draw number again");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void drawLines(Graphics g) {
        double[] number = new double[1];
        int prevX = (int)number[0];
        int prevY = (int)number[1];
        for (int i = 2; i < number.length; i+=2) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine((int)prevX, getHeight() - prevY, (int)number[i], (int)(getHeight() - (number[i+1])));
            //g.drawRect(number[i], getHeight() - (number[i+1] +22), 1,1);
            prevX = (int)number[i];
            prevY = (int)number[i+1];
        }
    }
}
