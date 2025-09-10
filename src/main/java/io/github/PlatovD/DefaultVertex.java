package io.github.PlatovD;

import java.util.Objects;

public class DefaultVertex<T> implements Vertex<T> {
    private final T data;

    public DefaultVertex(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultVertex<?> that = (DefaultVertex<?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }

    @Override
    public String toString() {
        return "DefaultVertex{" +
                "data=" + data +
                '}';
    }
}
