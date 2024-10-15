package ru.nsu.shebanov.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Queue;


public class AdjacencyListGraph<T> implements Graph<T> {
    private final List<Set<Integer>> graph = new ArrayList<>();
    private final Map<T, Integer> vertices = new HashMap<>();


    @Override
    public void addVertex(T vertex) {
        if (vertices.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex already added");
        } else {
            vertices.put(vertex, graph.size());
            graph.add(new HashSet<>());
        }
    }

    @Override
    public void removeVertex(T vertex) {
        if (vertices.containsKey(vertex)) {
            int vertexNumber = vertices.get(vertex);

            graph.remove(vertexNumber);

            for (Set<Integer> neighbors : graph) {
                neighbors.remove(vertexNumber);
            }

            vertices.remove(vertex);

            reindexVertices(vertexNumber);
        } else {
            throw new IllegalArgumentException("Graph does not contain such vertex");
        }
    }

    private void reindexVertices(int removedVertexIndex) {
        for (Map.Entry<T, Integer> entry : vertices.entrySet()) {
            if (entry.getValue() > removedVertexIndex) {
                entry.setValue(entry.getValue() - 1);
            }
        }

        for (int i = 0; i < graph.size(); i++) {
            Set<Integer> neighbors = graph.get(i);
            Set<Integer> updatedNeighbors = new HashSet<>();
            for (Integer neighborIndex : neighbors) {
                if (neighborIndex > removedVertexIndex) {
                    updatedNeighbors.add(neighborIndex - 1);
                } else {
                    updatedNeighbors.add(neighborIndex);
                }
            }
            graph.set(i, updatedNeighbors);
        }
    }


    @Override
    public void addEdge(T v1, T v2) {
        Integer index1 = vertices.get(v1);
        Integer index2 = vertices.get(v2);

        if (index1 != null && index2 != null) {
            graph.get(index1).add(index2);
        } else {
            throw new IllegalArgumentException("One or both of the vertices do not exist");
        }
    }

    @Override
    public void removeEdge(T v1, T v2) {
        Integer index1 = vertices.get(v1);
        Integer index2 = vertices.get(v2);

        if (index1 != null && index2 != null) {
            graph.get(index1).remove(index2);
        } else {
            throw new IllegalArgumentException("One or both of the vertices do not exist");
        }
    }

    @Override
    public List<T> neighbors(T vertex) {
        Integer vertexIndex = vertices.get(vertex);
        if (vertexIndex == null) {
            throw new IllegalArgumentException("Vertex not found");
        }

        List<T> neighborList = new ArrayList<>();
        Set<Integer> neighbors = graph.get(vertexIndex);

        for (Integer neighborIndex : neighbors) {
            for (Map.Entry<T, Integer> entry : vertices.entrySet()) {
                if (entry.getValue().equals(neighborIndex)) {
                    neighborList.add(entry.getKey());
                    break;
                }
            }
        }
        return neighborList;
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

        for (Map.Entry<T, Integer> entry : vertices.entrySet()) {
            T vertex = entry.getKey();
            int vertexIndex = entry.getValue();

            sb.append("Vertex (").append(vertex).append(") : [");

            Set<Integer> neighbors = graph.get(vertexIndex);
            if (neighbors != null) {
                for (int neighborIndex : neighbors) {
                    for (Map.Entry<T, Integer> innerEntry : vertices.entrySet()) {
                        if (innerEntry.getValue().equals(neighborIndex)) {
                            sb.append(innerEntry.getKey()).append(", ");
                            break;
                        }
                    }
                }
                if (!neighbors.isEmpty()) {
                    sb.delete(sb.length() - 2, sb.length());
                }
            }
            sb.append("]\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }


}