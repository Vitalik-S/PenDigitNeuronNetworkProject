package utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class Tools {
    /**
     * @param fileLoc - relativeFileLocation
     * @return String[]
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

    public static ArrayList<Pair<Integer, Integer>> compressVector(ArrayList<Pair<Integer, Integer>> inputVector){
        ArrayList<Pair<Integer, Integer>> expandedPoints = expandPoints(inputVector);
        final double digitLength = calcDigitLength(expandedPoints);
        final double partLength = digitLength/7;
        double currentLength = 0;
        double prevLength = 0;
        Pair<Integer, Integer> prevPoint = null;
        ArrayList<Pair<Integer, Integer>> compressedVector = new ArrayList<>();
        compressedVector.add(expandedPoints.get(0));
        for (Iterator<Pair<Integer, Integer>> iterator = expandedPoints.iterator(); iterator.hasNext(); ) {
            if (prevPoint == null)
                prevPoint = iterator.next();
            Pair<Integer, Integer> currPoint = iterator.next();
            currentLength += Math.sqrt(
                    Math.pow((currPoint.getX() - prevPoint.getX()), 2)
                            + Math.pow((currPoint.getY() - prevPoint.getY()) ,2)
            );
            if (currentLength < partLength) {
                prevLength = currentLength;
                prevPoint = currPoint;
            } else {
                if ((partLength - prevLength) < (currentLength - partLength)){
                    compressedVector.add(prevPoint);
                }else {
                    compressedVector.add(currPoint);
                }
                currentLength = 0;
                prevLength = 0;
            }
        }
        System.out.println(compressedVector.size());
        return compressedVector;
    }

    private static ArrayList<Pair<Integer, Integer>> expandPoints(ArrayList<Pair<Integer, Integer>> inputVector){
        int maxX = -1;
        int maxY = -1;
        int minX = 101;
        int minY = 101;

        for (Pair<Integer, Integer> pair : inputVector){
            int x = pair.getX();
            int y = pair.getY();
            maxX = x > maxX ? x : maxX;
            maxY = y > maxY ? y : maxY;
            minX = x < minX ? x : minX;
            minY = y < minY ? y : minY;
        }

        int width = (maxX-minX);
        int height = (maxY-minY);
        ArrayList<Pair<Integer, Integer>> newVector = new ArrayList<>();
        for (Pair<Integer, Integer> pair : inputVector) {
            int newX = minX - pair.getX();
            newX = newX < 0 ? -newX : newX;
            int newY = minY - pair.getY();
            newY = newY < 0 ? -newY : newY;
            int xPerc = (int)Math.round(((double) newX * 100.0)/(double) width);
            int yPerc = (int)Math.round(((double) newY * 100.0)/(double) height);
            newVector.add(Pair.createPair(xPerc, yPerc));
        }
        return newVector;
    }

    private static double calcDigitLength(ArrayList<Pair<Integer, Integer>> expandedPoints){
        Pair<Integer, Integer> tmpPoint = null;
        double digitLength = 0;
        for (Iterator<Pair<Integer, Integer>> iterator = expandedPoints.iterator(); iterator.hasNext(); ) {
            if (tmpPoint == null){
                tmpPoint = iterator.next();
            }
            Pair<Integer, Integer> currPoint = iterator.next();
            digitLength += Math.sqrt(
                    Math.pow((currPoint.getX() - tmpPoint.getX()), 2)
                            + Math.pow((currPoint.getY() - tmpPoint.getY()) , 2)
            );
            tmpPoint = currPoint;
        }
        return digitLength;
    }
}
