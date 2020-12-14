import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Driver class to run Roadtrip file
 */
public class Driver {

    public static void main(String[] args) throws Exception {
        ArrayList<String> vertices = new ArrayList<>(10);
        Graph usa = new Graph(vertices.size());
        String filepath = "/Users/selviipalani/Downloads/CS 245/roads.csv";
        String second_filepath = "/Users/selviipalani/Downloads/CS 245/attractions.csv";


        ArrayList<String> attractions = new ArrayList<>();
        Hashtable<String, String> attraction_city_set = new Hashtable<>();
        attractions.add("Grand Canyon");
        attractions.add("Paul Bunyon and Babe the Blue Ox");
        attractions.add("Statue of Liberty");

        RoadTrip roadtrip = new RoadTrip(vertices);
        roadtrip.readFile(usa, vertices, filepath);
        roadtrip.readSecondFile(attraction_city_set, second_filepath);
        roadtrip.route("San Jose CA", "Rock Springs WY",attractions, attraction_city_set, vertices, usa);
        System.out.println("Path: " + roadtrip.getShortestPath("San Jose CA", "Rock Springs WY", vertices));


    }


}
