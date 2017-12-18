import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Lenovo on 2017-12-18.
 */
public class LoadData {

    int recordsCounter;
    int inputCounter;
    Data[] data;
    Scanner scanner;


    public Data[] loadData(){

        try {
            scanner = new Scanner(new File("learnOnlyLetterBinary.txt"));
            scanner.useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recordsCounter = scanner.nextInt();
        inputCounter = scanner.nextInt();
        data = new Data[recordsCounter];
        int dataId =0;
        while(scanner.hasNext()){
            Data dataTmp = new Data(inputCounter);
            for (int i = 0; i < inputCounter; i++) {
                dataTmp.setX(i, scanner.nextDouble());
            }
            dataTmp.setY(scanner.nextLine());
//            System.out.println(dataTmp.getY());
            data[dataId] = dataTmp;
            dataId++;
        }

        return data;
    }

    public int getRecordsCounter() {
        return recordsCounter;
    }

    public void setRecordsCounter(int recordsCounter) {
        this.recordsCounter = recordsCounter;
    }
}
