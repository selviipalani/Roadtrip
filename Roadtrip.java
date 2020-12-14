import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.ArrayList;

class RoadTrip {
    ArrayList<Boolean> known;
    List<String> citiesOfInterest;
    int[][] Dijkstras_table;


    public RoadTrip() {

    }

    public RoadTrip(List<String> vertices) {
        Dijkstras_table = new int[vertices.size()][2];
        known = new ArrayList<>(vertices.size());
        citiesOfInterest = new ArrayList<>();
    }


    /**
     * Read the roads file to create graph
     * @param graph
     * @param vertices
     * @param filepath
     * @throws Exception
     */
    public void readFile(Graph graph, ArrayList<String> vertices, String filepath) throws Exception {
        //create graph
        // str[0] vertex1
        // str[1] vertex2
        //graph = new Graph(vertices.size());
        //read the file
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            //empty string which will store each line
            String line = "";
            line = br.readLine();
            //while we are not at the end of the file
            while (line != null) {
                //split each line by commas and store result in array
                String[] str = line.split(",", 4);
                int miles = Integer.parseInt(str[2]);
                //add each vertex to vertices ArrayList
                if(!vertices.contains(str[0])){
                    vertices.add(str[0]);
                }
                if(!vertices.contains(str[1])){
                    vertices.add(str[1]);
                }
                graph.addEdge(str[0],str[1],miles);
                //read the next line
                line = br.readLine();

            }
        }
        System.out.println(graph.toString());
    }

    /**
     * Read attractions file to make sure its included in path
     * @param attractions
     * @param filepath
     * @throws Exception
     */
    public void readSecondFile(Hashtable<String, String> attractions, String filepath) throws Exception{
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))){
            String line = "";
            line = bufferedReader.readLine();

            while(line != null){
                String[] str = line.split(",",2);
                attractions.put(str[0],str[1]);
                line = bufferedReader.readLine();
            }
        }
        System.out.println("Attractions: "+ attractions.toString());
    }

    public String findLowest(List<String> vertices, Set<String> Q, List<String> citiesOfInterest, int[][] Dijkstras_table) {
        int lowest = Integer.MAX_VALUE;
        String vertex = "";
        for (String key : Q) {
            if(citiesOfInterest.contains(key)) {
                lowest = Dijkstras_table[vertices.indexOf(key)][0];
                vertex = key;
                return vertex;
            }else if(Dijkstras_table[vertices.indexOf(key)][0] < lowest && Dijkstras_table[vertices.indexOf(key)][0] >= 0) {
                lowest = Dijkstras_table[vertices.indexOf(key)][0];
                vertex = key;
            }
        }

        return vertex;
    }

    /**
     * Route function that calculates shortest path using Dijkstra's algorithm from a starting city to an ending city in an undirectional graph
     * @param starting_city
     * @param ending_city
     * @param attractions
     * @param attraction_city_set
     * @param vertices
     * @param usa
     */
    public void route(String starting_city, String ending_city, List<String> attractions, Hashtable<String, String> attraction_city_set, List<String> vertices, Graph usa) {
        Hashtable<String,Integer> neighbors;
        Dijkstras_table = new int[vertices.size()][2];

        //get the cities of interest for this road trip
        for (int i = 0; i < attractions.size(); i++) {
            citiesOfInterest.add(attraction_city_set.get(attractions.get(i)));
        }

        //add all the vertices to known arraylist and initialize to false at first
        for(int i = 0; i < vertices.size(); i++) {
            known.add(i, false);
        }

        //initialize known path and cost
        for(int i = 0; i < vertices.size(); i++) {
            if(i == vertices.indexOf(starting_city)) {
                Dijkstras_table[i][0] = 0;
                known.set(vertices.indexOf(starting_city), true);
            }else {
                Dijkstras_table[i][0] = Integer.MAX_VALUE;
                known.set(vertices.indexOf(starting_city), false);
            }
            Dijkstras_table[i][1] = -1;

        }

        Dijkstras_table[vertices.indexOf(starting_city)][0] = 0;
        Set<String> queue = new HashSet<>();
        queue.add(starting_city);
        known.set(vertices.indexOf(starting_city), true);

        //start the loop of Dijkstras
        while(!queue.isEmpty()) {
            String curr = findLowest(vertices, queue, citiesOfInterest, Dijkstras_table);
            neighbors = usa.findNeighbors(curr, vertices);
            queue.remove(curr);
            for(String neighbor : neighbors.keySet()) {
                if(!known.get(vertices.indexOf(neighbor))) {
                    if(Dijkstras_table[vertices.indexOf(neighbor)][0] >= Dijkstras_table[vertices.indexOf(curr)][0] + neighbors.get(neighbor) || Dijkstras_table[vertices.indexOf(neighbor)][0] < 0) {
                        Dijkstras_table[vertices.indexOf(neighbor)][0] = Dijkstras_table[vertices.indexOf(curr)][0] + neighbors.get(neighbor);
                        Dijkstras_table[vertices.indexOf(neighbor)][1] = vertices.indexOf(curr);
                    }
                    queue.add(neighbor);
                }
            }
            known.set(vertices.indexOf(curr), true);
        }

        System.out.println("Known: " + known);
    }

    /**
     * Function that backtracks from target to source in Dijkstra's table to get the path in order
     * @param source
     * @param target
     * @param vertices
     * @return List of cities that form the shortest path
     */
    public List<String> getShortestPath(String source, String target, List<String> vertices) {
        ArrayList<String> path = new ArrayList<>();

        String curr = target;
        path.add(target);

        while(!curr.equals(source)) {
            path.add(vertices.get(Dijkstras_table[vertices.indexOf(curr)][1]));
            curr = vertices.get(Dijkstras_table[vertices.indexOf(curr)][1]);
        }


        Collections.reverse(path);

        return path;
    }
}
