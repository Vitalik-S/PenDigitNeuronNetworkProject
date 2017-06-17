package ui;

import utils.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class PaintArea extends JPanel {

    private ArrayList<Integer[]> digitVector;
    double[] res;
    private MainFrame parent;
    boolean draw = false;

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
        //if (!draw)
        digitVector.forEach(i -> g.drawRect(i[0], i[1], 1,1));
        //else
         //   draw(g, res);

    }

    private void initListeners(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = getMousePosition();
                if (point != null){
                    digitVector.add(new Integer[]{point.x, point.y});
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //TODO
                double[] x = Tools.compressVector(digitVector);
                res = x;
                System.out.println(Arrays.toString(x));
                int result = parent.network.recognize(x);
                parent.paintResult.setText(parent.paintResult.getText() + " " + result);
                digitVector.clear();
                draw = true;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void draw(Graphics g, double[] x) {
        double [] number = x;
        int prevX = (int) number[0];
        int prevY = (int) number[1];
        for (int i = 2; i < number.length; i+=2) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(prevX, getHeight() - prevY, (int)number[i], (int)(getHeight() - (number[i+1])));
            //g.drawRect(number[i], getHeight() - (number[i+1] +22), 1,1);
            prevX = (int)number[i];
            prevY = (int)number[i+1];
        }
    }
}
