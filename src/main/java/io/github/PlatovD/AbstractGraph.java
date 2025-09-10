package io.github.PlatovD;

import io.github.PlatovD.exception.EdgeNotFoundException;
import io.github.PlatovD.exception.VertexNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractGraph<W extends Comparable<W>, T> implements Graph<W, T> {
    protected final Map<Vertex<T>, Map<Vertex<T>, Edge<W, T>>> adjacencyList;
    protected final Map<T, Vertex<T>> vertices;

    public AbstractGraph() {
        adjacencyList = new HashMap<>();
        vertices = new HashMap<>();
    }

    @Override
    public boolean containsVertex(T value) {
        return vertices.containsKey(value);
    }

    @Override
    public Collection<T> getAllVertices() {
        return Collections.unmodifiableSet(vertices.keySet());
    }

    @Override
    public Collection<T> getAdjacentVertices(T value) {
        if (!containsVertex(value)) throw new VertexNotFoundException("Vertex " + value + " not exists");
        return getAdjacentMapForVertex(vertices.get(value)).keySet()
                .stream().map(Vertex::getData).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Collection<GraphEntry<W, T>> getAdjacentVerticesWithWeights(T value) {
        if (!containsVertex(value)) throw new VertexNotFoundException("Vertex " + value + " not exists");
        return getAdjacentMapForVertex(vertices.get(value)).entrySet()
                .stream().map(e -> createEntry(e.getKey().getData(), e.getValue().getWeight()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean removeVertex(T value) {
        if (!containsVertex(value)) return false;

        removeIncomingEdges(value);
        adjacencyList.remove(vertices.get(value));
        vertices.remove(value);
        return true;
    }


    @Override
    public W getWeight(T from, T to) {
        checkVerticesExist(from, to);

        Vertex<T> vertexFrom = vertices.get(from);
        Vertex<T> vertexTo = vertices.get(to);
        Map<Vertex<T>, Edge<W, T>> adjacentVertices = getAdjacentMapForVertex(vertexFrom);
        if (adjacentVertices == null) throw new VertexNotFoundException("Vertex " + from + " doesn't have edges");
        Edge<W, T> edge = adjacentVertices.get(vertexTo);
        if (edge == null)
            throw new EdgeNotFoundException("Edge from " + from + " to " + to + " not exists");
        return edge.getWeight();
    }

    @Override
    public void setWeight(T from, T to, W weight) {
        checkVerticesExist(from, to);

        removeEdge(from, to);
        addEdge(from, to, weight);
    }

    @Override
    public int size() {
        return vertices.size();
    }

    @Override
    public void clear() {
        vertices.clear();
        adjacencyList.clear();
    }

    protected abstract Vertex<T> createVertex(T value);

    protected abstract Edge<W, T> createEdge(Vertex<T> from, Vertex<T> to, W weight);

    protected abstract GraphEntry<W, T> createEntry(T vertex, W weight);

    @SafeVarargs
    protected final void checkVerticesExist(T... verticesValues) {
        for (T vertexValue : verticesValues)
            if (!containsVertex(vertexValue))
                throw new VertexNotFoundException("Vertex " + vertexValue + " not exists");
    }

    protected Map<Vertex<T>, Edge<W, T>> getAdjacentMapForVertex(Vertex<T> vertex) {
        return adjacencyList.get(vertex);
    }
}
