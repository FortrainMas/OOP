package ru.nsu.shebanov.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyListGraphTest {

    @Test
    public void basicGraphImplementationTest() {
        Graph<Integer> graph = new AdjacencyListGraph<Integer>();
        graph.addVertex(8);
        graph.addVertex(4);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(8, 4);
        graph.addEdge(4, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 8);

        graph.removeVertex(4);
        graph.addEdge(8, 1);
        graph.addEdge(8, 2);
        graph.removeEdge(1, 2);


        String expectedGraph = "Vertex (1) : []\n" +
                "Vertex (2) : [8]\n" +
                "Vertex (8) : [1, 2]";
        assertEquals(graph.toString(), expectedGraph);
    }

    @Test public void complicatedGraph(){
        Graph<String> graph = new AdjacencyListGraph<>();

        graph.addVertex("ABC");
        graph.addVertex("AB");
        graph.addVertex("AC");
        graph.addVertex("BC");
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("ABC", "AB");
        graph.addEdge("ABC", "AC");
        graph.addEdge("ABC", "BC");
        graph.addEdge("ABC", "A");
        graph.addEdge("ABC", "B");
        graph.addEdge("ABC", "C");
        graph.addEdge("AB", "A");
        graph.addEdge("AB", "B");
        graph.addEdge("AC", "A");
        graph.addEdge("AC", "C");
        graph.addEdge("BC", "C");
        graph.addEdge("BC", "B");

        List<String> expected = new ArrayList<>();
        expected.add("ABC");
        expected.add("AB");
        expected.add("AC");
        expected.add("BC");
        expected.add("A");
        expected.add("B");
        expected.add("C");

        assertEquals(expected, graph.toposort());
    }
}