package ru.nsu.shebanov.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class AdjacencyMatrixGraph<T> implements Graph<T> {

    private final Map<T, Integer> verticesMap = new HashMap<>();
    private int[][] adjacencyMatrix;
    private int vertexCount = 0;

    @Override
    public void addVertex(T vertex) {
        if (verticesMap.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex already exists");
        }

        verticesMap.put(vertex, vertexCount);

        if (vertexCount == 0) {
            adjacencyMatrix = new int[1][1];
        } else {
            int[][] newMatrix = new int[vertexCount + 1][vertexCount + 1];
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < vertexCount; j++) {
                    newMatrix[i][j] = adjacencyMatrix[i][j];
                }
            }
            adjacencyMatrix = newMatrix;
        }

        vertexCount++;
    }

    @Override
    public void removeVertex(T vertex) {
        if (!verticesMap.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        int vertexIndex = verticesMap.get(vertex);
        verticesMap.remove(vertex);

        int[][] newMatrix = new int[vertexCount - 1][vertexCount - 1];
        for (int i = 0; i < vertexCount; i++) {
            if (i == vertexIndex) continue;
            for (int j = 0; j < vertexCount; j++) {
                if (j == vertexIndex) continue;
                int newRow = i < vertexIndex ? i : i - 1;
                int newCol = j < vertexIndex ? j : j - 1;
                newMatrix[newRow][newCol] = adjacencyMatrix[i][j];
            }
        }
        adjacencyMatrix = newMatrix;
        vertexCount--;

        // Reindex vertices after removal
        reindexVertices(vertexIndex);
    }

    private void reindexVertices(int removedVertexIndex) {
        Map<T, Integer> newVerticesMap = new HashMap<>();
        int newIndex = 0;
        for (Map.Entry<T, Integer> entry : verticesMap.entrySet()) {
            newVerticesMap.put(entry.getKey(), newIndex++);
        }
        verticesMap.clear();
        verticesMap.putAll(newVerticesMap);
    }

    @Override
    public void addEdge(T v1, T v2) {
        int index1 = verticesMap.get(v1);
        int index2 = verticesMap.get(v2);

        if (index1 == -1 || index2 == -1) {
            throw new IllegalArgumentException("One or both vertices do not exist");
        }

        adjacencyMatrix[index1][index2] = 1;
    }

    @Override
    public void removeEdge(T v1, T v2) {
        int index1 = verticesMap.get(v1);
        int index2 = verticesMap.get(v2);

        if (index1 == -1 || index2 == -1) {
            throw new IllegalArgumentException("One or both vertices do not exist");
        }

        adjacencyMatrix[index1][index2] = 0;
    }

    @Override
    public List<T> neighbors(T vertex) {
        int vertexIndex = verticesMap.get(vertex);
        if (vertexIndex == -1) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        List<T> neighbors = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[vertexIndex][i] == 1) {
                for (Map.Entry<T, Integer> entry : verticesMap.entrySet()) {
                    if (entry.getValue() == i) {
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
        for (T vertex : verticesMap.keySet()) {
            inDegree.put(vertex, 0);
        }

        for (T vertex : verticesMap.keySet()) {
            for (T neighbor : neighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<T> queue = new ArrayDeque<>();
        for (T vertex : verticesMap.keySet()) {
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

        if (sortedVertices.size() != verticesMap.size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort is not possible");
        }

        return sortedVertices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertexCount; i++) {
            sb.append("Vertex ").append(getVertex(i)).append(": ");
            for (int j = 0; j < vertexCount; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    sb.append(getVertex(j)).append(", ");
                }
            }
            sb.delete(sb.length()-2, sb.length());
            sb.append("\n");
        }
        return sb.toString();
    }

    private T getVertex(int index) {
        for (Map.Entry<T, Integer> entry : verticesMap.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }
}
