package io.github.PlatovD.generator;

import io.github.PlatovD.DefaultDirectedGraph;
import io.github.PlatovD.DefaultUndirectedGraph;
import io.github.PlatovD.Graph;
import io.github.PlatovD.GraphType;
import io.github.PlatovD.exception.UnknowGraphTypeException;

import java.util.*;

public class SimpleGraphGenerator implements GraphGenerator {
    private static final Double MAX_EDGE_WEIGHT = 30d;

    private <T> Graph<Double, T> createGraph(GraphType graphType) {
        return switch (graphType) {
            case UNDIRECTED -> new DefaultUndirectedGraph<>();
            case DIRECTED -> new DefaultDirectedGraph<>();
            default -> throw new UnknowGraphTypeException("Graph type " + graphType + " is not supported");
        };
    }

    private <T> void setEdgesUndirected(Graph<Double, T> graph, List<T> vertices, Integer edgeCnt) {
        int n = vertices.size();
        if (n * (n - 1) / 2 < edgeCnt)
            throw new IllegalArgumentException("Edges too many (" + edgeCnt + ") for " + vertices + " vertices");

        createEdges(edgeCnt, vertices, graph);
    }

    private <T> void setEdgesDirected(Graph<Double, T> graph, List<T> vertices, Integer edgeCnt) {
        int n = vertices.size();
        if (n * (n - 1) < edgeCnt)
            throw new IllegalArgumentException("Edges too many (" + edgeCnt + ") for " + vertices + " vertices");

        createEdges(edgeCnt, vertices, graph);
    }

    private <T> void createEdges(Integer edgeCnt, List<T> vertices, Graph<Double, T> graph) {
        int n = vertices.size();
        Random random = new Random(System.currentTimeMillis());
        while (edgeCnt > 0) {
            int indexFrom = random.nextInt(n);
            int indexTo = random.nextInt(n);
            T from = vertices.get(indexFrom);
            T to = vertices.get(indexTo);
            if (!graph.containsEdge(from, to)) {
                graph.addEdge(from, to, random.nextDouble(MAX_EDGE_WEIGHT));
                edgeCnt--;
            }
        }
    }

    @Override
    public Graph<Double, String> generateStringGraph(GraphType graphType, Integer vertexCnt, Integer edgeCnt) {
        Graph<Double, String> graph = createGraph(graphType);
        List<String> vertices = generateVerticesSting(vertexCnt);
        for (String v : vertices) {
            graph.addVertex(v);
        }
        if (graphType.equals(GraphType.UNDIRECTED))
            setEdgesUndirected(graph, vertices, edgeCnt);
        else
            setEdgesDirected(graph, vertices, edgeCnt);
        return graph;
    }

    private List<String> generateVerticesSting(Integer vertexCnt) {
        List<String> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCnt; i++) {
            vertices.add("v" + i);
        }
        return vertices;
    }

    private List<Integer> generateVerticesInteger(Integer vertexCnt) {
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCnt; i++) {
            vertices.add(i);
        }
        return vertices;
    }

    @Override
    public Graph<Double, Integer> generateIntegerGraph(GraphType graphType, Integer vertexCnt, Integer edgeCnt) {
        Graph<Double, Integer> graph = createGraph(graphType);
        List<Integer> vertices = generateVerticesInteger(vertexCnt);
        for (Integer v : vertices) {
            graph.addVertex(v);
        }
        if (graphType.equals(GraphType.UNDIRECTED))
            setEdgesUndirected(graph, vertices, edgeCnt);
        else
            setEdgesDirected(graph, vertices, edgeCnt);
        return graph;
    }
}
