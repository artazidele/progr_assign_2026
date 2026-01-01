package org.example;

public class Edge {


    private int verticeAIndex;
    private int verticeBIndex;
    private int weight;
    private boolean chosen = false;

    public Edge(int verticeAIndex, int verticeBIndex, int weight) {
        this.verticeAIndex = verticeAIndex;
        this.verticeBIndex = verticeBIndex;
        this.weight = weight;
    }

    public int getVerticeAIndex() {
        return this.verticeAIndex;
    }

    public int getVerticeBIndex() {
        return this.verticeBIndex;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isChosen() {
        return chosen;
    }
}
