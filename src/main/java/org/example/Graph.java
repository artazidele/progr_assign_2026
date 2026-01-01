package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {

    private Edge[] edges;
    private Vertice[] vertices;
    private int n;

    public Graph(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            int ch = reader.read();
            while (Character.isWhitespace(ch)) {
                ch = reader.read();
            }
            this.n = Character.getNumericValue(ch);
            ch = reader.read();
            while (!Character.isWhitespace(ch)) {
                this.n = this.n * 10 + Character.getNumericValue(ch);
                ch = reader.read();
            }
            edges = new Edge[this.n * this.n];
            for (int i = 0; i < this.n * this.n; i++) {
                edges[i] = null;
            }
            vertices = new Vertice[this.n];
            for (int i = 0; i < this.n; i++) {
                var vertice = new Vertice(i+1);
                vertices[i] = vertice;
            }
            var edgeIndex = 0;
            while ((ch = reader.read()) != -1) {
                while (Character.isWhitespace(ch)) {
                    ch = reader.read();
                }
                var verticeAIndex = Character.getNumericValue(ch);
                ch = reader.read();
                while (!Character.isWhitespace(ch)) {
                    verticeAIndex = verticeAIndex * 10 + Character.getNumericValue(ch);
                    ch = reader.read();
                }
                while (Character.isWhitespace(ch)) {
                    ch = reader.read();
                }
                var verticeBIndex = Character.getNumericValue(ch);
                ch = reader.read();
                while (!Character.isWhitespace(ch)) {
                    verticeBIndex = verticeBIndex * 10 + Character.getNumericValue(ch);
                    ch = reader.read();
                }
                while (Character.isWhitespace(ch)) {
                    ch = reader.read();
                }
                int w;
                if (ch == '-') {
                    ch = reader.read();
                    w = Character.getNumericValue(ch) * -1;
                    ch = reader.read();
                    while (ch != -1 && !Character.isWhitespace(ch)) {
                        w = w * 10 - Character.getNumericValue(ch);
                        ch = reader.read();
                    }
                } else {
                    w = Character.getNumericValue(ch);
                    ch = reader.read();
                    while (ch != -1 && !Character.isWhitespace(ch)) {
                        w = w * 10 + Character.getNumericValue(ch);
                        ch = reader.read();
                    }

                }
                var edge = new Edge(this.vertices[verticeAIndex-1].getIndex(), this.vertices[verticeBIndex-1].getIndex(), w);
                this.edges[edgeIndex++] = edge;
            }
            var edgesCount = 0;
            for (int i = 0; i < this.edges.length; i++) {
                if (this.edges[i] != null) {
                    edgesCount++;
                }
            }
            Edge[] newEdges = new Edge[edgesCount];
            for (int i = 0; i < edgesCount; i++) {
                newEdges[i] = this.edges[i];
            }
            this.edges = newEdges;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Edge[] getEdges() {
        return edges;
    }

    public Vertice[] getVertices() {
        return vertices;
    }

    public void writeToFile(String filename) {
        Edge[] chosenEdges = new Edge[this.n * this.n];
        var k = 0;
        for (int i = 0; i < this.edges.length; i++) {
            if (this.edges[i] == null) {
                break;
            }
            if (this.edges[i].isChosen()) {
                chosenEdges[k++] = this.edges[i];
            }
        }

        Edge[] newEdges = new Edge[k];
        for (int i = 0; i < k; i++) {
            newEdges[i] = chosenEdges[i];
        }

        var sum = 0;
        for (int i = 0; i < k; i++) {
            sum += newEdges[i].getWeight();
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(Integer.toString(k));
            writer.write(' ');
            writer.write(Integer.toString(sum));
            for (Edge edge : newEdges) {
                writer.write(' ');
                writer.write(Integer.toString(edge.getVerticeAIndex()));
                writer.write(' ');
                writer.write(Integer.toString(edge.getVerticeBIndex()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
