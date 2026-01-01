package org.example;

public class Main {
    public static void main(String[] args) {
        var graph = new Graph("input.txt");
        var algorithm = new MaximumSpanningTree(graph);
        algorithm.findMaximumSpanningTree();
        graph.writeToFile("output.txt");
    }
}