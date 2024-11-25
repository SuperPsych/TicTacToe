import java.io.*;

public class Bot {
    String data;
    int layers;
    int height;
    double[] inputLayer;
    double[] outputLayer;
    double[][] innerLayers;
    double[][] weights;
    double[][] biases;

    final double LEARNING_RATE = 0.01;
    final int DEFAULT_LAYERS = 5;
    final int DEFAULT_HEIGHT = 10;

    public Bot(String data) {
        this.data = data;
        layers = DEFAULT_LAYERS;
        height = DEFAULT_HEIGHT;
        initialize();
        build();
    }
    public void build(){
        inputLayer = new double[10];
        outputLayer = new double[9];
        innerLayers = new double[layers][height];
        weights = new double[layers][height];
        biases = new double[layers][height];

    }
    public void initialize(){
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(data))){
                StringBuilder builder = new StringBuilder();
                for(int i=0; i<layers; i++){
                    builder.append(Math.random());
                    if(i<layers-1){
                        builder.append(',');
                    }
                }
                writer.write(builder.toString());
                writer.newLine();
                builder = new StringBuilder();
                for(int i=0; i<layers; i++){
                    builder.append(Math.random());
                    if(i<layers-1){
                        builder.append(',');
                    }
                }
                writer.write(builder.toString());
            }
            catch(IOException f){
                System.out.println("Error. Failed to initialize the bot.");
            }
        }
    }
}
