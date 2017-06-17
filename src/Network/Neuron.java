package Network;

/**
 * Created by Vitalij on 18.04.17.
 */
public class Neuron {

    double[] w; //weights
    double b;   //biase
    double d;   //error


    public Neuron(int input) {
        w = random(new double[input]);
        b = Math.random()*2-1;
    }


    //random weights [-1;1]
    double[] random(double[] w){
        for (int i = 0; i < w.length; i++) {
            w[i] = Math.random()*2-1;
        }
        return w;
    }
}
