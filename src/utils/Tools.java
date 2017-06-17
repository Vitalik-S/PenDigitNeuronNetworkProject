package utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class Tools {


    public static double[] compressVector(ArrayList<Integer[]> inputVector){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(inputVector.get(0)[0]);
        result.add(100 - inputVector.get(0)[1]);

        int sectionSize = inputVector.size() / 7;
        int tmp = sectionSize;
        for (int i = 0; i < inputVector.size(); i++) {
            if (i == tmp){
                result.add(inputVector.get(i)[0]);
                result.add(100 - inputVector.get(i)[1]);
                tmp += sectionSize;
            }
        }
        return result.stream().mapToDouble(i->i).toArray();
    }
}
