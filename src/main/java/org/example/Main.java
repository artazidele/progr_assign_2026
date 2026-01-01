package org.example;

public class Main {
    public static void main(String[] args) {
        var graph = new Graph("src/main/resources/input.txt");
        var algorithm = new MaximumSpanningTree(graph);
        algorithm.findMaximumSpanningTree();
        graph.writeToFile("src/main/resources/output.txt");
    }
}