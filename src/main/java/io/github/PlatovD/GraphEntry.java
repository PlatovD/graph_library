package io.github.PlatovD;

public class GraphEntry<W, T> implements Graph.GraphEntry<W, T> {
    private final T vertex;
    private final W weight;

    public GraphEntry(T vertex, W weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    @Override
    public T getVertex() {
        return vertex;
    }

    @Override
    public W getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "GraphEntry{" +
                "vertex=" + vertex +
                ", weight=" + weight +
                '}';
    }
}
