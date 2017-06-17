package utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Vitalij on 15.06.17.
 */

public class FileReader {
    /**
     * The readFromFile read input data from file to double matrix
     * @param fileLoc The location of the data set file
     */
    public static double[][] readFromFile(String fileLoc) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        RandomAccessFile aFile = new RandomAccessFile(fileLoc, "r");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(inChannel.read(buffer) > 0) {
            buffer.flip();
            for (int i = 0; i < buffer.limit(); i++) {
                stringBuilder.append((char) buffer.get());
            }
            buffer.clear();
        }
        inChannel.close();
        aFile.close();

        String[] result = stringBuilder.toString().split("\n");
        double[][] res = new double[result.length][17];

        for (int i = 0; i < result.length; i++) {
            String[] s = result[i].split(",");
            for (int j = 0; j < s.length; j++) {
                res[i][j] = Integer.parseInt(s[j].trim());
            }
        }
        return res;
    }
}
