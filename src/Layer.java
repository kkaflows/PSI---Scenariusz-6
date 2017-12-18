import java.util.ArrayList;

/**
 * Created by Lenovo on 2017-12-18.
 */
public class Layer {

    public Neuron[] neurons;
    private int how_many_x; //Liczba wejśc na neurony
    private int how_many_neurons; //Liczba neuronów
    private Data data;


    public Layer(int how_many_neurons, int how_many_x){

        this.how_many_neurons = how_many_neurons;
        this.how_many_x = how_many_x;

        neurons = new Neuron[this.how_many_neurons];
        for(int i = 0; i< this.how_many_neurons; i++){
            neurons[i] = new Neuron(this.how_many_x);
        }
    }

    public ArrayList<Double> calculateLayer(Data input){
        ArrayList<Double> results = new ArrayList<Double>();
        this.data = input;

        for (int i=0 ; i < how_many_neurons; i++){
            results.add(neurons[i].calculate(input));
        }

        return results;
    }


    public void modify(int id, double alfa, double theta){

        neurons[id].modify(data, alfa, theta);
    }
}
