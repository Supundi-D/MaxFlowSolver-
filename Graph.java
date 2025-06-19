
import java.util.*;

public class Graph {
    private final int vertices;
    private final List<Edge>[] adjacencyList;
    private final List<Edge> edges;

    @SuppressWarnings("unchecked")
    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new ArrayList[vertices];
        this.edges = new ArrayList<>();
        
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int from, int to, int capacity) {
        Edge edge = new Edge(from, to, capacity);
        adjacencyList[from].add(edge);
        edges.add(edge);
    }

    public int getVertices() {
        return vertices;
    }

    public List<Edge>[] getAdjacencyList() {
        return adjacencyList;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
