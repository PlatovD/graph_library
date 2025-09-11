package io.github.PlatovD.algorithm;

import io.github.PlatovD.DefaultDirectedGraph;
import io.github.PlatovD.DefaultUndirectedGraph;
import io.github.PlatovD.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class AlgorithmsTest {
    private static Graph<Integer, String> undirectedGraph;
    private static Graph<Integer, String> directedGraph;
    private static Graph<Integer, String> directedForFloyd;

    @BeforeAll
    public static void setUp() {
        undirectedGraph = new DefaultUndirectedGraph<>();
        undirectedGraph.addVertex("1");
        undirectedGraph.addVertex("2");
        undirectedGraph.addVertex("3");
        undirectedGraph.addVertex("4");
        undirectedGraph.addVertex("5");
        undirectedGraph.addVertex("6");

        undirectedGraph.addEdge("1", "2", 5);
        undirectedGraph.addEdge("2", "4", 4);
        undirectedGraph.addEdge("1", "3", 3);
        undirectedGraph.addEdge("3", "5", 6);
        undirectedGraph.addEdge("3", "6", 1);
        undirectedGraph.addEdge("6", "5", 2);


        directedGraph = new DefaultDirectedGraph<>();
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");
        directedGraph.addVertex("4");
        directedGraph.addVertex("5");
        directedGraph.addVertex("6");
        directedGraph.addVertex("7");
        directedGraph.addVertex("8");
        directedGraph.addVertex("9");

        directedGraph.addEdge("1", "2", 2);
        directedGraph.addEdge("1", "3", 3);
        directedGraph.addEdge("2", "4", 4);

        directedGraph.addEdge("5", "6", 6);
        directedGraph.addEdge("6", "7", 1);
        directedGraph.addEdge("6", "8", 5);
        directedGraph.addEdge("7", "8", 8);
        directedGraph.addEdge("6", "9", 4);


        directedForFloyd = new DefaultDirectedGraph<>();
        directedForFloyd.addVertex("1");
        directedForFloyd.addVertex("2");
        directedForFloyd.addVertex("3");
        directedForFloyd.addVertex("4");

        directedForFloyd.addEdge("1", "2", -2);
        directedForFloyd.addEdge("1", "3", 3);
        directedForFloyd.addEdge("2", "4", 4);
    }

    @Test
    void DFSUndirected() {
        List<String> expected = List.of("1", "2", "4", "3", "5", "6");
        Assertions.assertEquals(expected, Algorithms.DFS(undirectedGraph, "1"));
    }

    @Test
    void DFSDirected() {
        List<String> expected = List.of("1", "2", "4", "3");
        Assertions.assertEquals(expected, Algorithms.DFS(directedGraph, "1"));
    }

    @Test
    void BFSUndirected() {
        List<String> expected = List.of("1", "2", "3", "4", "5", "6");
        Assertions.assertEquals(expected, Algorithms.BFS(undirectedGraph, "1"));
    }

    @Test
    void BFSDirected() {
        List<String> expected = List.of("1", "2", "3", "4", "5", "6");
        Assertions.assertEquals(expected, Algorithms.BFS(undirectedGraph, "1"));
    }

    @Test
    void dijkstra() {
        Map<String, Double> expected1 = Map.of(
                "1", 0d,
                "2", 5d,
                "4", 9d,
                "3", 3d,
                "6", 4d,
                "5", 6d
        );
        Assertions.assertEquals(expected1, Algorithms.dijkstra(undirectedGraph, "1"));

        Map<String, Double> expected2 = Map.of(
                "5", 0d,
                "6", 6d,
                "7", 7d,
                "8", 11d,
                "9", 10d
        );
        Assertions.assertEquals(expected2, Algorithms.dijkstra(directedGraph, "5"));
    }

    @Test
    void countConnectivityComponent() {
        Assertions.assertEquals(2, Algorithms.countConnectivityComponent(directedGraph));
    }

    @Test
    void hasCycle() {
        Assertions.assertTrue(Algorithms.hasCycle(undirectedGraph));
        Assertions.assertFalse(Algorithms.hasCycle(directedGraph));
    }

    @Test
    void floydWarshall() {
        Map<String, Map<String, Double>> expected = Map.of(
                "1", Map.of(
                        "1", 0d,
                        "2", -2d,
                        "3", 3d,
                        "4", 2d
                ),
                "2", Map.of(
                        "1", Double.POSITIVE_INFINITY,
                        "2", 0d,
                        "3", Double.POSITIVE_INFINITY,
                        "4", 4d
                ),
                "3", Map.of(
                        "1", Double.POSITIVE_INFINITY,
                        "2", Double.POSITIVE_INFINITY,
                        "3", 0d,
                        "4", Double.POSITIVE_INFINITY
                ),
                "4", Map.of(
                        "1", Double.POSITIVE_INFINITY,
                        "2", Double.POSITIVE_INFINITY,
                        "3", Double.POSITIVE_INFINITY,
                        "4", 0d
                )
        );
        Assertions.assertEquals(expected, Algorithms.floydWarshall(directedForFloyd));
    }
}