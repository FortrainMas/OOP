package ru.nsu.shebanov.graph;

import java.util.*;

public class IncidenceMatrixGraph<T> implements Graph<T> {

    private Map<T, Integer> vertices;
    private int[][] incidenceMatrix;
    private int numVertices;
    private int numEdges;

    public IncidenceMatrixGraph() {
        vertices = new HashMap<>();
        numVertices = 0;
        numEdges = 0;
    }

    @Override
    public void addVertex(T vertex) {
        if (!vertices.containsKey(vertex)) {
            vertices.put(vertex, numVertices++);
            int newSize = numVertices;
            int[][] newMatrix = new int[newSize][numEdges];

            if (incidenceMatrix != null) {
                for (int i = 0; i < incidenceMatrix.length; i++) {
                    System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, numEdges);
                }
            }

            incidenceMatrix = newMatrix;
        }
    }

    @Override
    public void removeVertex(T vertex) {

    }

    @Override
    public void addEdge(T from, T to) {
        int fromIndex = vertices.get(from);
        int toIndex = vertices.get(to);

        int newSize = numEdges + 1;
        int[][] newMatrix = new int[numVertices][newSize];

        // Copy the old matrix to the new one
        if (incidenceMatrix != null) {
            for (int i = 0; i < numVertices; i++) {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, numEdges);
            }
        }

        newMatrix[fromIndex][numEdges] = 1;
        newMatrix[toIndex][numEdges] = -1;

        incidenceMatrix = newMatrix;
        numEdges++;
    }

    @Override
    public void removeEdge(T v1, T v2) {

    }

    @Override
    public List<T> neighbors(T vertex) {
        Set<T> neighbors = new HashSet<>();
        int vertexIndex = vertices.get(vertex);

        for (int i = 0; i < numEdges; i++) {
            if (incidenceMatrix[vertexIndex][i] != 0) {
                for (Map.Entry<T, Integer> en   try : vertices.entrySet()) {
                    if (incidenceMatrix[entry.getValue()][i] != 0 && entry.getValue() != vertexIndex) {
                        neighbors.add(entry.getKey());
                        break;
                    }
                }
            }
        }

        return neighbors;
    }

    @Override
    public List<T> toposort() {
        Map<T, Integer> inDegree = new HashMap<>();
        for (T vertex : vertices.keySet()) {
            inDegree.put(vertex, 0);
        }

        for (T vertex : vertices.keySet()) {
            for (T neighbor : neighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<T> queue = new ArrayDeque<>();
        for (T vertex : vertices.keySet()) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        List<T> sortedVertices = new ArrayList<>();
        while (!queue.isEmpty()) {
            T vertex = queue.remove();
            sortedVertices.add(vertex);

            for (T neighbor : neighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sortedVertices.size() != vertices.size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort is not possible");
        }

        return sortedVertices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numVertices; i++) {
            for (Map.Entry<T, Integer> entry : vertices.entrySet()) {
                if (entry.getValue() == i) {
                    sb.append("Vertex ").append(entry.getKey()).append(": ");
                    break;
                }
            }
            for (int j = 0; j < numEdges; j++) {
                sb.append(incidenceMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
