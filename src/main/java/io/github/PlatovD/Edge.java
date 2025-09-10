package io.github.PlatovD;

public interface Edge<W, T> {
    Vertex<T> getSource();

    Vertex<T> getTarget();

    W getWeight();
}
