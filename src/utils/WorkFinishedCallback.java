package utils;

import Network.Network;

public interface WorkFinishedCallback {
    void onFinish(Network network);
    void onError(String message);
}
