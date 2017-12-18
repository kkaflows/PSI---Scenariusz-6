import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Lenovo on 2017-12-18.
 */
public class Network {

    private double alfa0; //Współczynnik uczenia
    private double sigma0; //Początkowy promień sąsiedztwa
    private int how_many_neurons; //Ile neuronów w sieci
    private int iterationLimit;
    private Data[] lerningData; //Dane uczące
    private Data[] testingData; //Dane testujące
    private int how_many_records_lerningData;
    private int how_many_records_testingData;

    private Layer layer;

    public Network(double alfa0, double sigma0, int how_many_neurons, int iterationLimit){

        this.alfa0 = alfa0;
        this.sigma0 = sigma0;
        this.how_many_neurons = how_many_neurons;
        this.iterationLimit = iterationLimit;

        layer = new Layer(how_many_neurons, 35);

        LoadData loadData = new LoadData();

        lerningData =  loadData.loadData();
        how_many_records_lerningData = loadData.getRecordsCounter();

        testingData = loadData.loadData();
        how_many_records_testingData = loadData.getRecordsCounter();
    }


    //uczenie
    public void learning(){

        normalize(lerningData);

        int counter = 0;
        int winner;


        double sigma, theta,  alfa = 0;
        int sigmaRounded;

        ArrayList<Double> result;

        do{
            alfa = alfaFunction(counter);
            sigma = sigmaFunction(counter);
            sigmaRounded = (int)Math.round(sigma);

            for (int i=0; i< how_many_records_lerningData; i++){

                result = layer.calculateLayer(lerningData[i]);
                winner = result.indexOf(Collections.min(result));

                for(int j = (- sigmaRounded); j <= sigmaRounded; j++){
                    if( ((winner + j) >= 0) && ((winner + j) < how_many_neurons) ){
                        theta = thetaFunction(sigma, j);
                        layer.modify((winner +j), alfa, theta);
                    }
                }

                result.clear();
            }
            counter++;

        }while( counter < iterationLimit);

        System.out.println("Uczenie zakończone \nLiczba epok: " + counter + "\n" +
                "Promień po zakończeniu uczenia: " + sigmaRounded + "\n" +
                "Współczynnik uczenia po zakończeniu uczenia: " +alfa + "\n\n"
        );


    }


    //testowanie i wyswietlanie wynikow
    public void testing(){

        normalize(testingData);

        ArrayList<Double> result;
        ArrayList<Double> errors = new ArrayList<Double>();
        ArrayList<MyMap> gropus = new ArrayList<MyMap>();
        int winner;
        double globalError = 0.0;
        for(int i=0; i< how_many_records_testingData; i++) {

            result = layer.calculateLayer(testingData[i]);

            winner = result.indexOf(Collections.min( result ));
            errors.add(Collections.min( result ));

            boolean flag = true;
            for(MyMap el: gropus){
                if(el.id == winner) {
                    el.letters += testingData[i].getY() + ", ";
                    flag = false;
                }
            }

            if(flag){
                gropus.add(new MyMap(winner, (testingData[i].getY() + ", " )));
            }

//            System.out.println("ID: " + i + "  Rodzaj: " + testingData[i].getY() + "  Zwyciężył neuron: " + winner + "  Błąd kwantyzacji:  " + errors.get(i));
            System.out.println(testingData[i].getY() +" " + errors.get(i));

        }

        System.out.print("\n\n   PODSUMOWANIE:\n");

        for(MyMap el: gropus)
            System.out.println("ID: " + el.id + " LLTERY: " + el.letters);

        for(Double el: errors)
            globalError += el;

        globalError = (1.0 / (double)how_many_records_testingData ) * globalError;

        System.out.println("\nBłąd globalny: " +globalError);
    }


    //klasa pomocnicza do wyswietlania
    private class MyMap{
        public int id;
        public String letters;
        public MyMap(int id, String letters){this.id = id; this.letters = letters;}
    }


    //normalizacja wektorow
    private void normalize(Data[] data){
        double lenghtSquared;
        double lenght;
        for(int i =0 ; i< data.length; i++){
            lenghtSquared = 0;
            lenght = 0;
            for(int j=0; j<data[i].getHow_many_x(); j++)
                lenghtSquared += Math.pow(data[i].getX(j),2);

            lenght = Math.sqrt(lenghtSquared);

            for(int j=0; j<data[i].getHow_many_x(); j++)
                data[i].setX(j, (data[i].getX(j)/lenght));

        }

    }


    //obliczanie wspolczynnika uczenia w czasie (kolejnych iteracjach
    private double alfaFunction(int counter){
        return ( alfa0 * Math.exp( - ((double)counter ) / ((double)iterationLimit )) );
    }


    //obliczanie promienia sasiedztwa w kolejnych iteracjach
    private double sigmaFunction(int counter){
        return ( sigma0 * Math.exp( - ((double)counter ) / ((double)iterationLimit )) );
    }


    //obliczanie stopnia pobudzenia neuronu w zaleznosci od jego odleglosci od zwycieskiego neuronu
    private double thetaFunction(double sigma, int r){
        return Math.exp( - ( Math.pow(r, 2) ) / ( 2 * Math.pow(sigma,2) ));
    }
}
