import java.util.*;

public class Graph <T> extends RoadTrip {
    Hashtable<String, Hashtable<String, Integer>> adjacency;
    int numVertices;


    public Graph(int numVertices){
        adjacency = new Hashtable<>();
        this.numVertices = numVertices;

    }

    /**
     * Adds edge to graph
     * @param v1 Vertex we are coming from
     * @param v2 Vertex we are going to
     * @param weight weight
     */
    public void addEdge(String v1, String v2, int weight) {
        //check if either city is already in adjacency
        Hashtable<String,Integer> neighbors;

        if(v2.equals(v1)) {
            return;
        }

        if(!adjacency.containsKey(v1)){
            //if not there, create new hashtable of neighbors for the city
            neighbors = new Hashtable<>();

            //put the neighbor and the weight to get there in neighbors HT
            neighbors.put(v2,weight);
            //add it back to the adjacency list
            adjacency.put(v1,neighbors);
            //if the key we are coming from is already in adjacency
        }else if(adjacency.containsKey(v1) && !adjacency.get(v1).containsKey(v2)){
            //update the existing neighbors hashtable
            //add it back to the adjacency list
            Hashtable<String, Integer> temp = adjacency.get(v1);
            temp.put(v2, weight);

            adjacency.replace(v1, temp);
            //same thing for v2, since this is undirected graph
        }

        if(!adjacency.containsKey(v2)){
            neighbors = new Hashtable<>();
            neighbors.put(v1,weight);
            adjacency.put(v2,neighbors);
        }else if(adjacency.containsKey(v2) && !adjacency.get(v2).containsKey(v1)){
            Hashtable<String, Integer> temp = adjacency.get(v2);
            temp.put(v1, weight);

            adjacency.replace(v2,temp);
        }
    }

    /**
     * Returns neighbors of specified vertex
     * @param vertex we want to find neighbors of
     * @param vertices list of all cities
     * @return neighbors of vertex
     */
    public Hashtable<String,Integer> findNeighbors(String vertex, List<String> vertices){
        Hashtable<String,Integer> neighbors;
        neighbors= adjacency.get(vertex);

        return neighbors;
    }

    /**
     * To string function
     * @return graph as hashtable
     */
    public String toString() {
        return "Graph: " +
                "adjacency= " + adjacency +
                "} " + super.toString();
    }
}


