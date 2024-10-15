package ru.nsu.shebanov.graph;

import java.util.List;

public interface Graph<T> {
    void addVertex(T vertex);
    void removeVertex(T vertex);

    void addEdge(T v1, T v2);
    void removeEdge(T v1, T v2);

    List<T> neighbors(T vertex);

    List<T> toposort();
}
