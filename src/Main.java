import Network.Network;
import ui.ErrorDialog;
import ui.LoadingDialog;
import ui.MainFrame;
import utils.WorkFinishedCallback;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        //120 65|80 10 // 16 120 65|80  |||| 500, 140, 10 // 16, 500, 140
        int[] neuronOnLayer = {120, 75, 10};
        int[] inputDataLength = {16, 120, 75};
        LoadingDialog.show();
        new Thread(() -> new Network(neuronOnLayer, inputDataLength, new WorkFinishedCallback() {
            @Override
            public void onFinish(Network network) {
                LoadingDialog.hide();
                SwingUtilities.invokeLater(() -> {
                    new MainFrame(network);// start MainWindow
                });
            }

            @Override
            public void onError(String message) {
                ErrorDialog.show(message);
            }
        })).start();

    }
}
