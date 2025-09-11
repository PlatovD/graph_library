package io.github.PlatovD;

import java.util.Collection;
import java.util.List;

public interface Graph<W extends Comparable<W>, T> {
    boolean addVertex(T value);

    boolean containsVertex(T value);

    List<T> getAllVertices();

    List<T> getAdjacentVertices(T value);

    Collection<GraphEntry<W, T>> getAdjacentVerticesWithWeights(T value);

    boolean removeVertex(T value);

    void addEdge(T from, T to, W weight);

    boolean containsEdge(T from, T to);

    W getWeight(T from, T to);

    void setWeight(T from, T to, W weight);

    boolean removeEdge(T from, T to);

    void removeIncomingEdges(T value);

    int size();

    void clear();

    GraphType type();

    public interface GraphEntry<W, T> {
        T getVertex();

        W getWeight();
    }
}
