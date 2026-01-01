package org.example;

public class MaximumSpanningTree {

    private Graph graph;
    private Integer[][] spanningTrees;

    public MaximumSpanningTree(Graph graph) {
        this.graph = graph;
    }

    private Edge[] merge(Edge[] edges, int p, int q, int r) {
        var nL = q - p + 1;
        var nR = r - q;
        Edge[] edgesL = new Edge[nL];
        Edge[] edgesR = new Edge[nR];

        for (int i = 0; i < nL; i++) {
            edgesL[i] = edges[p + i];
        }

        for (int i = 0; i < nR; i++) {
            edgesR[i] = edges[q + i + 1];
        }

        var i = 0;
        var j = 0;
        var k = p;
        while (i < nL && j < nR) {
            if (edgesL[i].getWeight() >= edgesR[j].getWeight()) {
                edges[k] = edgesL[i];
                i += 1;
            } else {
                edges[k] = edgesR[j];
                j += 1;
            }
            k += 1;
        }

        while (i < nL) {
            edges[k] = edgesL[i];
            i += 1;
            k += 1;
        }

        while (j < nR) {
            edges[k] = edgesR[j];
            j += 1;
            k += 1;
        }

        return edges;
    }

    private Edge[] mergeSort(Edge[] edges, int p, int r) {
        if (p >= r) {
            return edges;
        }
        var q = (p + r) / 2;
        mergeSort(edges, p, q);
        mergeSort(edges, q + 1, r);
        return merge(edges, p, q, r);
    }


    public void findMaximumSpanningTree() {
        Edge[] edges = graph.getEdges();
        var m = edges.length;

        edges = mergeSort(edges, 0, m - 1);

        Vertice[] vertices = graph.getVertices();
        var n = vertices.length;
        spanningTrees = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            Integer[] spanningTree = new Integer[n];
            spanningTree[0] = vertices[i].getIndex();
            spanningTrees[i] = spanningTree;
        }

        for (Edge edge : edges) {
            if (!verticesBelongToSameTree(edge.getVerticeAIndex(), edge.getVerticeBIndex()) && edge.getWeight() > 0) {
                union(edge.getVerticeAIndex(), edge.getVerticeBIndex());
            } else {
                edge.setChosen(true);
            }
        }
    }

    private boolean verticesBelongToSameTree(int v1, int v2) {
        for (int i = 0; i < spanningTrees.length; i++) {
            var vA = false;
            var vB = false;
            for (int j = 0; j < spanningTrees[i].length; j++) {
                if (spanningTrees[i][j] == null) {
                    continue;
                }
                if (spanningTrees[i][j] == v1) {
                    vA = true;
                }
                if (spanningTrees[i][j] == v2) {
                    vB = true;
                }
            }
            if (vA && vB) {
                return true;
            }
        }

        return false;
    }

    private void union(int v1, int v2) {
        Integer[] tree1 = new Integer[spanningTrees.length];
        int tree1Index = 0;
        Integer[] tree2 = new Integer[spanningTrees.length];
        int tree2Index = 0;
        for (int i = 0; i < spanningTrees.length; i++) {
            Integer[] tree = spanningTrees[i];
            for (int j = 0; j < spanningTrees[i].length; j++) {
                if (spanningTrees[i][j] == null) {
                    continue;
                }
                if (spanningTrees[i][j] == v1) {
                    tree1 = tree;
                    tree1Index = i;
                }
                if (spanningTrees[i][j] == v2) {
                    tree2 = tree;
                    tree2Index = i;
                }
            }
        }

        var tree1NotNulllength = 0;
        for (int i = 0; i < tree1.length; i++) {
            if (tree1[i] == null) {
                break;
            }
            tree1NotNulllength++;
        }
        var tree2NotNulllength = 0;
        for (int i = 0; i < tree2.length; i++) {
            if (tree2[i] == null) {
                break;
            }
            tree2NotNulllength++;
        }

        for (int i = tree1NotNulllength; i < tree1NotNulllength + tree2NotNulllength; i++) {
            spanningTrees[tree1Index][i] = tree2[i - tree1NotNulllength];
        }

        for (int i = 0; i < tree2.length; i++) {
            spanningTrees[tree2Index][i] = null;
        }
    }
}
