import Network.Network;
import ui.MainFrame;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //120 65|80 10 // 16 120 65|80  |||| 500, 140, 10 // 16, 500, 140
        int[] neuronOnLayer = {120, 75, 10};
        int[] inputDataLength = {16, 120, 75};
        Network n = new Network(neuronOnLayer,inputDataLength);
        new MainFrame(n);
    }
}
