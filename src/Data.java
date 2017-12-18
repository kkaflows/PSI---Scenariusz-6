/**
 * Created by Lenovo on 2017-12-18.
 */
public class Data {
    private double x [];
    private int how_many_x;
    private String y;

    public Data(double[] x, String y, int how_many_x) {
        this.x = x;
        this.y = y;
        this.how_many_x = how_many_x;

    }

    public Data(int how_many_x){
        this.x = new double[how_many_x];
        this.how_many_x = how_many_x;

    }


    public double getX(int i) {
        return x[i];
    }

    public void setX(int i, double x) {
        this.x[i] = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public int getHow_many_x() {
        return how_many_x;
    }

    public void setHow_many_x(int how_many_x) {
        this.how_many_x = how_many_x;
    }
}
