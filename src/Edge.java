import java.util.HashMap;

/**
 * Created by ben on 2016-11-03.
 */
public class Edge {
    private double distance;


    public Edge () {
        this.distance = 999999999;
    }

    public Edge (int distance) {
        this.distance = distance;
    }

    void updateDistance(double newDistance){
        this.distance = newDistance;
    }

    public double getDistance(){
        return this.distance;
    }

    public String toString(){
        StringBuilder newString = new StringBuilder();
        newString.append("Distance = " + distance + "\n");
        return newString.toString();
    }
}
