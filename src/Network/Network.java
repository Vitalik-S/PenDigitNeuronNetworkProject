package Network;

import ui.LoadingDialog;
import utils.Tools;
import utils.WorkFinishedCallback;

import java.util.Arrays;

public class Network {

   private static Neuron[] layer1;
   private static Neuron[] layer2;
   private static Neuron[] layer3;

    /**
     * Constructor that make the model of neuron system
     * @param neuronOnLayer The number of neurons on each layer
     * @param inputDataLength The length of the input vector in each neuron
     * of each layer
     */
    public Network(int[] neuronOnLayer, int[] inputDataLength, WorkFinishedCallback callback){
        try {
            layer1 = new Neuron[neuronOnLayer[0]];
            layer2 = new Neuron[neuronOnLayer[1]];
            layer3 = new Neuron[neuronOnLayer[2]];


            for (int i = 0; i < layer1.length; i++) {
                layer1[i] = new Neuron(inputDataLength[0]);
            }
            for (int i = 0; i < layer2.length; i++) {
                layer2[i] = new Neuron(inputDataLength[1]);
            }
            for (int i = 0; i < layer3.length; i++) {
                layer3[i] = new Neuron(inputDataLength[2]);
            }

            double[][] inputData = Tools.readFromFile("res/pendigits.tra");
            LoadingDialog.changeBarValue(10);

            teachNetwork(inputData, Correct_Output.getOutputExpect());
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(e.getMessage());
        }
        callback.onFinish(this);
    }


    /**
     * The method teachNetwork is the method, that starts
     * the studying of the network
     * @param inputData The array of input data vectors for studying
     * @param outputExpect The expected output from the last network layer
     */
    private void teachNetwork(double[][] inputData, double[][] outputExpect){

        double[] output1Layer = new double[layer1.length];
        double[] output2Layer = new double[layer2.length];
        double[] output3Layer = new double[layer3.length];

        for (int k = 0; k < 30; k ++) {
            LoadingDialog.changeBarValue(LoadingDialog.getBarValue() + 3);
            //random data set
     /*       for (int i = 0; i < inputData.length; i++) {
                int index = (int)(Math.random()*inputData.length);
                double[] tmp = inputData[i];
                inputData[i] = inputData[index];
                inputData[index] = tmp;
            }*/
            for (double[] anInputData : inputData) {

                for (int j = 0; j < output1Layer.length; j++) {
                    output1Layer[j] = countNeuronOutput(layer1[j], anInputData);
                }
                for (int j = 0; j < output2Layer.length; j++) {
                    output2Layer[j] = countNeuronOutput(layer2[j], output1Layer);
                }

                for (int j = 0; j < output3Layer.length; j++) {
                    output3Layer[j] = countNeuronOutput(layer3[j], output2Layer);
                }


                layer3 = studyOutputLayer(layer3, output2Layer, output3Layer, outputExpect[(int) anInputData[16]]);
                layer2 = studyLayer1(layer2, output1Layer, output2Layer);
                layer1 = studyLayer1(layer1, anInputData, output1Layer);

            }
        }

    }

    /**
     * The method studyOutputLayer is the method, that starts
     * the studying of the second(output) layer of network
     * for one input data vector
     * @param neuronsLayer The array of output neurons layer
     * @param inputData The output from first layer(input for second layer)
     * @param realOutput The real output from second network layer
     * @param outputExpect The expected output from network
     */
    private Neuron[] studyOutputLayer(Neuron[] neuronsLayer, double[] inputData, double[] realOutput, double[] outputExpect){
        for (int i = 0; i < outputExpect.length; i++) {
            neuronsLayer[i] = studyOutputNeuronLayer(neuronsLayer[i],inputData, outputExpect[i], realOutput[i]);
        }
        return neuronsLayer;
    }

    /**
     * The method studyOutputNeuronLayer is the method, that
     * study a neuron from the output layer of network
     * for one input data vector
     * @param neuronLayer2 One neuron from output layer neurons
     * @param inputData The output from first layer(input for second layer)
     * @param realOutput The real from this neuron
     * @param outputExpect The expected output from this neuron
     */
    private Neuron studyOutputNeuronLayer(Neuron neuronLayer2, double[] inputData, double outputExpect, double realOutput){
        double y = realOutput;
        double n = 0.25;
        double d = y*(1 - y)*(outputExpect - y);
        neuronLayer2.d = d;
        for (int i = 0; i < neuronLayer2.w.length; i++) {
            neuronLayer2.w[i] += n*d*inputData[i];
        }
        neuronLayer2.b += n*d;
        return neuronLayer2;
    }

    /**
     * The method studyLayer1 is the method, that starts
     * the studying of the input layer of network
     * for one input data vector
     * @param neuronsLayer The array of output layer neurons
     * @param inputData The output from first layer(input for second layer)
     * @param realOutput The real output from second network layer
     */
    private Neuron[] studyLayer1(Neuron[] neuronsLayer, double[] inputData, double[] realOutput){
        for (int i = 0; i < neuronsLayer.length; i++) {
            neuronsLayer[i] = studyNeuronLayer1(neuronsLayer[i], inputData, realOutput[i],  i);
        }
        return neuronsLayer;
    }

    /**
     * The method studyNeuronLayer1 is the method, that
     * study a neuron from the input layer of network
     * for one input data vector
     * @param neuronLayer One neuron from output layer neurons
     * @param inputData The output from first layer(input for second layer)
     * @param realOutput The real from this neuron
     * @param index The index of neuron
     */
    private Neuron studyNeuronLayer1(Neuron neuronLayer, double[] inputData, double realOutput, int index){
        double y = realOutput;
        double n = 0.25;
        double d = 0;
        if (neuronLayer.w.length == 16) {
            for (Neuron aLayer2 : layer2) {
                d += (aLayer2.w[index] * aLayer2.d);
            }
        }else{
            for (Neuron aLayer3 : layer3) {
                d += (aLayer3.w[index] * aLayer3.d);
            }
        }
        d = y * (1-y) * d;

        for (int i = 0; i < neuronLayer.w.length; i++) {
            neuronLayer.w[i] += n*d*inputData[i];
        }
        neuronLayer.b += n*d;
        return neuronLayer;
    }


    /**
     * The method countNeuronOutput count the output 'y' of the neuron
     * using the sigmoid unipolar activation function
     * @param neuron The neuron for which we count the output
     * @param inputData The input data vector in neuron
     * of each layer
     */
    private double countNeuronOutput(Neuron neuron, double[] inputData){
        double net = neuron.b;
        for (int i = 0; i < neuron.w.length; i++) {
            net += (neuron.w[i] * inputData[i]);
        }
        return 1/(1+ Math.pow(Math.E, -net));
    }

    public int recognize(double[] inputData) {
        double[] output1Layer = new double[layer1.length];
        double[] output2Layer = new double[layer2.length];
        double[] output3Layer = new double[layer3.length];

        for (int j = 0; j < output1Layer.length; j++) {
            output1Layer[j] = countNeuronOutput(layer1[j], inputData);
        }

        for (int j = 0; j < output2Layer.length; j++) {
            output2Layer[j] = countNeuronOutput(layer2[j], output1Layer);
        }

        for (int j = 0; j < output3Layer.length; j++) {
            output3Layer[j] = Math.round(countNeuronOutput(layer3[j], output2Layer));
        }

        for (int j = 0; j < Correct_Output.getOutputExpect().length; j++) {
            if (Arrays.equals(output3Layer, Correct_Output.getOutputExpect()[j])) {
                return j;
            }
        }
        return -1;
    }

    public String testData(double[][] inputData) {
        double[] output1Layer = new double[layer1.length];
        double[] output2Layer = new double[layer2.length];
        double[] output3Layer = new double[layer3.length];

        int cor = 0;
        int incor = 0;
        for (double[] anInputData : inputData) {
            for (int j = 0; j < output1Layer.length; j++) {
                output1Layer[j] = countNeuronOutput(layer1[j], anInputData);
            }

            for (int j = 0; j < output2Layer.length; j++) {
                output2Layer[j] = countNeuronOutput(layer2[j], output1Layer);
            }

            for (int j = 0; j < output3Layer.length; j++) {
                output3Layer[j] = Math.round(countNeuronOutput(layer3[j], output2Layer));
            }

            if (Arrays.equals(output3Layer, Correct_Output.getOutputExpect()[(int) anInputData[16]])) {
                cor++;
            } else {
                incor++;
            }
        }
        double correctness = (cor*100)/(cor+incor);
        return cor + " " +incor + " " + correctness;
    }
}