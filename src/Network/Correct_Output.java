package Network;

/**
 * Created by Vitalij on 15.06.17.
 */
public class Correct_Output {
    private static final double[][] outputExpect =
            {
                    {1,0,0,0,0,0,0,0,0,0}, //0
                    {0,1,0,0,0,0,0,0,0,0}, //1
                    {0,0,1,0,0,0,0,0,0,0}, //2
                    {0,0,0,1,0,0,0,0,0,0}, //3
                    {0,0,0,0,1,0,0,0,0,0}, //4
                    {0,0,0,0,0,1,0,0,0,0}, //5
                    {0,0,0,0,0,0,1,0,0,0}, //6
                    {0,0,0,0,0,0,0,1,0,0}, //7
                    {0,0,0,0,0,0,0,0,1,0}, //8
                    {0,0,0,0,0,0,0,0,0,1}, //9
            };

    public static double[][] getOutputExpect() {
        return outputExpect;
    }
}
