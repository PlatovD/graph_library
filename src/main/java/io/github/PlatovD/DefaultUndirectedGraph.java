package io.github.PlatovD;

import io.github.PlatovD.exception.EdgeAlreadyExistsException;

import java.util.Map;

public class DefaultUndirectedGraph<W extends Comparable<W>, T> extends AbstractGraph<W, T> {
    @Override
    protected Vertex<T> createVertex(T value) {
        return new DefaultVertex<>(value);
    }

    @Override
    protected Edge<W, T> createEdge(Vertex<T> from, Vertex<T> to, W weight) {
        return new DefaultEdge<>(from, to, weight);
    }

    @Override
    protected GraphEntry<W, T> createEntry(T vertex, W weight) {
        return new io.github.PlatovD.GraphEntry<>(vertex, weight);
    }

    @Override
    public void addEdge(T from, T to, W weight) {
        checkVerticesExist(from, to);

        Vertex<T> vertexFrom = vertices.get(from);
        Vertex<T> vertexTo = vertices.get(to);
        Map<Vertex<T>, Edge<W, T>> vertexFromAdjacencyMap = getAdjacentMapForVertex(vertexFrom);
        Map<Vertex<T>, Edge<W, T>> vertexToAdjacencyMap = getAdjacentMapForVertex(vertexTo);
        if (vertexFromAdjacencyMap.containsKey(vertexTo) || vertexToAdjacencyMap.containsKey(vertexFrom))
            throw new EdgeAlreadyExistsException("Edge already exists. Use set weight instead");

        Edge<W, T> edge = new DefaultEdge<>(vertexFrom, vertexTo, weight);
        vertexFromAdjacencyMap.put(vertexTo, edge);
        vertexToAdjacencyMap.put(vertexFrom, edge);
    }

    @Override
    public boolean removeEdge(T from, T to) {
        checkVerticesExist(from, to);

        Vertex<T> vertexFrom = vertices.get(from);
        Vertex<T> vertexTo = vertices.get(to);
        Map<Vertex<T>, Edge<W, T>> vertexFromAdjacencyMap = getAdjacentMapForVertex(vertexFrom);
        Map<Vertex<T>, Edge<W, T>> vertexToAdjacencyMap = getAdjacentMapForVertex(vertexTo);
        boolean deletedFrom = vertexFromAdjacencyMap.remove(vertexTo) != null;
        boolean deletedTo = vertexToAdjacencyMap.remove(vertexFrom) != null;
        return deletedFrom || deletedTo;
    }

    @Override
    public GraphType type() {
        return GraphType.UNDIRECTED;
    }
}
