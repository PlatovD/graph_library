package io.github.PlatovD;

import java.util.Objects;

public class DefaultEdge<W, T> implements Edge<W, T> {
    private final W weight;
    private final Vertex<T> from;
    private final Vertex<T> to;

    public DefaultEdge(Vertex<T> from, Vertex<T> to, W weight) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    @Override
    public Vertex<T> getSource() {
        return from;
    }

    @Override
    public Vertex<T> getTarget() {
        return to;
    }

    @Override
    public W getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultEdge<?, ?> that = (DefaultEdge<?, ?>) o;
        return Objects.equals(weight, that.weight) && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, from, to);
    }

    @Override
    public String toString() {
        return "DefaultEdge{" +
                "weight=" + weight +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
