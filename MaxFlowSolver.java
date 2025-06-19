
import java.util.*;

public class MaxFlowSolver {
    private final Graph graph;
    private final int source;
    private final int sink;

    public MaxFlowSolver(Graph graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
    }

    public int findMaxFlow() {
        int maxFlow = 0;
        List<Edge>[] residualGraph = createResidualGraph();

        while (true) {
            // Find augmenting path using BFS
            int[] parent = new int[graph.getVertices()];
            Arrays.fill(parent, -1);
            
            if (!bfs(residualGraph, parent)) {
                break;
            }

            // Find minimum residual capacity along the path
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                for (Edge edge : residualGraph[u]) {
                    if (edge.getTo() == v) {
                        pathFlow = Math.min(pathFlow, edge.getResidualCapacity());
                        break;
                    }
                }
            }

            // Update residual capacities
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                for (Edge edge : residualGraph[u]) {
                    if (edge.getTo() == v) {
                        edge.setFlow(edge.getFlow() + pathFlow);
                        break;
                    }
                }
            }

            maxFlow += pathFlow;
            System.out.println("Found augmenting path with flow: " + pathFlow);
        }

        return maxFlow;
    }

    private List<Edge>[] createResidualGraph() {
        @SuppressWarnings("unchecked")
        List<Edge>[] residualGraph = new ArrayList[graph.getVertices()];
        for (int i = 0; i < graph.getVertices(); i++) {
            residualGraph[i] = new ArrayList<>();
        }

        for (Edge edge : graph.getEdges()) {
            residualGraph[edge.getFrom()].add(edge);
        }

        return residualGraph;
    }

    private boolean bfs(List<Edge>[] residualGraph, int[] parent) {
        boolean[] visited = new boolean[graph.getVertices()];
        Queue<Integer> queue = new LinkedList<>();
        
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            
            for (Edge edge : residualGraph[u]) {
                int v = edge.getTo();
                if (!visited[v] && edge.getResidualCapacity() > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return visited[sink];
    }
}
