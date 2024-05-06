package controllers.Back.Appointment;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.Pooling2D;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.zoo.model.VGG16;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

public class ModelLoader {
    public static MultiLayerNetwork loadModel() throws IOException {
        // Load the pre-trained VGG16 model from the model zoo
        VGG16 vgg16 = VGG16.builder().build();

        // Replace the last layer with a new one for 3-class classification
        int numLabels = 3; // happy, sad, neutral
        int outputSize = numLabels * 1; // 1 output per label
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .seed(123)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Adam(1e-3))
                .list()
                .layer(new ConvolutionLayer.Builder(3, 3)
                        .nIn(3)
                        .nOut(512)
                        .activation(Activation.RELU)
                        .build())
                .layer(new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(new DenseLayer.Builder()
                        .nIn(512 * 7 * 7)
                        .nOut(4096)
                        .activation(Activation.RELU)
                        .build())
                .layer(new DenseLayer.Builder()
                        .nIn(4096)
                        .nOut(4096)
                        .activation(Activation.RELU)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(4096)
                        .nOut(outputSize)
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutional(224, 224, 3))
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();

        // Prepare the data normalization for the input images
        DataNormalization scaler = new ImagePreProcessingScaler(0, 255);
        INDArray input = model.input();
        scaler.transform(input);
        model.setInput(input);
        return model;
    }
    public static int predictEmotion(INDArray image) {
        try {
            // Load the pre-trained model
            MultiLayerNetwork model = ModelLoader.loadModel();

            // Perform inference on the image
            INDArray output = model.output(image);

            // Print the predicted class
            return output.argMax(1).getInt(0);
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Return -1 on error
        }
    }
}
