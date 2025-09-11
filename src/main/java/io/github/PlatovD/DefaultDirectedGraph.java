package io.github.PlatovD;

import io.github.PlatovD.exception.EdgeAlreadyExistsException;

import java.util.Map;

public class DefaultDirectedGraph<W extends Comparable<W>, T> extends AbstractGraph<W, T> {
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
        Map<Vertex<T>, Edge<W, T>> adjacentMapForSource = getAdjacentMapForVertex(vertexFrom);
        if (adjacentMapForSource.get(vertexTo) != null)
            throw new EdgeAlreadyExistsException("Edge already exists. Use set weight instead");

        Edge<W, T> edge = createEdge(vertexFrom, vertexTo, weight);
        adjacentMapForSource.put(vertices.get(to), edge);
    }

    @Override
    public boolean removeEdge(T from, T to) {
        checkVerticesExist(from, to);

        Vertex<T> vertexFrom = vertices.get(from);
        Vertex<T> vertexTo = vertices.get(to);
        Map<Vertex<T>, Edge<W, T>> adjacentMapForSource = getAdjacentMapForVertex(vertexFrom);
        return adjacentMapForSource.remove(vertexTo) != null;
    }

    @Override
    public GraphType type() {
        return GraphType.DIRECTED;
    }
}
