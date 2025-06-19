

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final String BENCHMARK_DIR = "benchmarks";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Run benchmarks");
            System.out.println("2. Exit");
            System.out.print("Enter your choice (1-2): ");
            
            int choice = scanner.nextInt();
            
            if (choice == 2) {
                System.out.println("Exiting program...");
                break;
            } else if (choice == 1) {
                runBenchmarks(scanner);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }

    private static void runBenchmarks(Scanner scanner) {
        System.out.println("\nSelect benchmark files to run:");
        System.out.println("1. Run all benchmark files");
        System.out.println("2. Select specific files");
        System.out.print("Enter your choice (1-2): ");
        
        int choice = scanner.nextInt();
        
        List<String> benchmarkFiles = getBenchmarkFiles();
        
        if (choice == 1) {
            runAllBenchmarks(benchmarkFiles);
        } else if (choice == 2) {
            selectAndRunBenchmarks(scanner, benchmarkFiles);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static List<String> getBenchmarkFiles() {
        File folder = new File(BENCHMARK_DIR);
        File[] files = folder.listFiles((dir, name) -> 
            name.endsWith(".txt") && (name.startsWith("bridge_") || name.startsWith("ladder_")));
        
        List<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                fileNames.add(file.getName());
            }
        }
        Collections.sort(fileNames, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] parts1 = o1.split("_");
                String[] parts2 = o2.split("_");
                
                if (parts1[0].equals(parts2[0])) {
                    int num1 = Integer.parseInt(parts1[1].replace(".txt", ""));
                    int num2 = Integer.parseInt(parts2[1].replace(".txt", ""));
                    return Integer.compare(num1, num2);
                }
                return parts1[0].compareTo(parts2[0]);
            }
        });
        return fileNames;
    }

    private static void selectAndRunBenchmarks(Scanner scanner, List<String> benchmarkFiles) {
        System.out.println("\nAvailable benchmark files:");
        for (int i = 0; i < benchmarkFiles.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, benchmarkFiles.get(i));
        }
        
        System.out.println("\nEnter file numbers to run (comma-separated) or 0 to go back:");
        scanner.nextLine();
        String input = scanner.nextLine();
        
        if (!input.equals("0")) {
            String[] selections = input.split(",");
            for (String sel : selections) {
                try {
                    int index = Integer.parseInt(sel.trim()) - 1;
                    if (index >= 0 && index < benchmarkFiles.size()) {
                        runBenchmark(benchmarkFiles.get(index));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + sel);
                }
            }
        }
    }

    private static void runAllBenchmarks(List<String> benchmarkFiles) {
        for (String fileName : benchmarkFiles) {
            runBenchmark(fileName);
        }
    }

    private static void runBenchmark(String fileName) {
        System.out.println("\nRunning benchmark: " + fileName);
        try {
            String filePath = BENCHMARK_DIR + File.separator + fileName;
            Graph graph = Parser.parseFile(filePath);
            MaxFlowSolver solver = new MaxFlowSolver(graph, 0, graph.getVertices() - 1);
            int maxFlow = solver.findMaxFlow();
            System.out.println("Maximum flow: " + maxFlow);
            System.out.println("----------------------------------------");
        } catch (IOException e) {
            System.err.println("Error running benchmark " + fileName + ": " + e.getMessage());
        }
    }
}