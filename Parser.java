

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    public static Graph parseFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read number of vertices
            int vertices = Integer.parseInt(reader.readLine().trim());
            Graph graph = new Graph(vertices);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 3) {
                    int from = Integer.parseInt(parts[0]);
                    int to = Integer.parseInt(parts[1]);
                    int capacity = Integer.parseInt(parts[2]);
                    graph.addEdge(from, to, capacity);
                }
            }
            return graph;
        }
    }
}
