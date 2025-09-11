package io.github.PlatovD.algorithm;

import io.github.PlatovD.Graph;
import io.github.PlatovD.GraphType;

import java.util.*;

public final class Algorithms {
    private Algorithms() {
        throw new AssertionError("Can't initialize util class");
    }

    public static <T> List<T> DFS(Graph<?, T> graph, T startVertex) {
        List<T> path = new ArrayList<>();
        Set<T> viewed = new HashSet<>();
        DFS(graph, startVertex, path, viewed);
        return Collections.unmodifiableList(path);
    }

    private static <T> void DFS(Graph<?, T> graph, T vertex, List<T> path, Set<T> viewed) {
        path.add(vertex);
        viewed.add(vertex);
        for (T adj : graph.getAdjacentVertices(vertex)) {
            if (!viewed.contains(adj)) {
                DFS(graph, adj, path, viewed);
            }
        }
    }

    public static <T> List<T> BFS(Graph<?, T> graph, T startVertex) {
        List<T> path = new ArrayList<>();
        Set<T> viewed = new HashSet<>();
        BFS(graph, startVertex, path, viewed);
        return Collections.unmodifiableList(path);
    }

    private static <T> void BFS(Graph<?, T> graph, T vertex, List<T> path, Set<T> viewed) {
        Queue<T> queue = new LinkedList<>();
        queue.add(vertex);
        viewed.add(vertex);
        while (!queue.isEmpty()) {
            T current = queue.poll();
            path.add(current);
            for (T adj : graph.getAdjacentVertices(current)) {
                if (!viewed.contains(adj)) {
                    queue.add(adj);
                    viewed.add(adj);
                }
            }
        }
    }

    public static <W extends Number & Comparable<W>, T> Map<T, Double> dijkstra(Graph<W, T> graph, T startVertex) {
        Map<T, Double> shortest = new HashMap<>();
        Set<T> viewed = new HashSet<>();
        dijkstra(graph, startVertex, shortest, viewed);
        return Collections.unmodifiableMap(shortest);
    }

    private static <W extends Number & Comparable<W>, T> void dijkstra(
            Graph<W, T> graph, T startVertex, Map<T, Double> shortest, Set<T> viewed
    ) {
        PriorityQueue<T> priorityQueue = new PriorityQueue<>(Comparator.comparing(shortest::get));
        priorityQueue.add(startVertex);
        shortest.put(startVertex, 0d);
        while (!priorityQueue.isEmpty()) {
            T current = priorityQueue.poll();
            if (viewed.contains(current)) continue;
            viewed.add(current);
            for (Graph.GraphEntry<W, T> entry : graph.getAdjacentVerticesWithWeights(current)) {
                T update = entry.getVertex();

                if (!viewed.contains(update)) {
                    W weight = entry.getWeight();
                    Double newDist = shortest.get(current) + weight.doubleValue();
                    if (shortest.getOrDefault(update, Double.MAX_VALUE).compareTo(newDist) > 0) {
                        shortest.put(update, newDist);
                        priorityQueue.add(update);
                    }
                }
            }
        }
    }

    public static <T> int countConnectivityComponent(Graph<?, T> graph) {
        int cnt = 0;
        Set<T> viewed = new HashSet<>();
        List<T> plug = new ArrayList<>();
        for (T vertex : graph.getAllVertices()) {
            if (!viewed.contains(vertex)) {
                cnt++;
                DFS(graph, vertex, plug, viewed);
                plug.clear();
            }
        }
        return cnt;
    }

    public static <T> boolean hasCycle(Graph<?, T> graph) {
        if (graph.type().equals(GraphType.UNDIRECTED)) return hasCycleUndirect(graph);
        else return hasCycleDirect(graph);
    }

    public static <T> boolean hasCycleUndirect(Graph<?, T> graph) {
        if (!graph.type().equals(GraphType.UNDIRECTED))
            throw new IllegalArgumentException("hasCycleUndirect work with undirect graph. Use hasCycleDirect instead");
        Set<T> viewed = new HashSet<>();
        for (T vertex : graph.getAllVertices()) {
            if (!viewed.contains(vertex)) {
                if (hasCycleUndirect(graph, vertex, null, viewed)) return true;
            }
        }
        return false;
    }

    private static <T> boolean hasCycleUndirect(Graph<?, T> graph, T vertex, T prevVertex, Set<T> visited) {
        if (visited.contains(vertex)) return true;
        visited.add(vertex);
        for (T adj : graph.getAdjacentVertices(vertex)) {
            if (!adj.equals(prevVertex) && hasCycleUndirect(graph, adj, vertex, visited)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean hasCycleDirect(Graph<?, T> graph) {
        if (!graph.type().equals(GraphType.DIRECTED))
            throw new IllegalArgumentException("hasCycleDirect work with direct graph. Use hasCycleUnDirect instead");
        Set<T> viewed = new HashSet<>();
        Set<T> active = new HashSet<>();
        for (T vertex : graph.getAllVertices()) {
            if (!viewed.contains(vertex)) {
                if (hasCycleDirect(graph, vertex, active, viewed)) return true;
                active.clear();
            }
        }
        return false;
    }

    private static <T> boolean hasCycleDirect(Graph<?, T> graph, T vertex, Set<T> active, Set<T> visited) {
        if (active.contains(vertex)) return true;
        if (visited.contains(vertex)) return false;
        active.add(vertex);
        for (T adj : graph.getAdjacentVertices(vertex)) {
            if (hasCycleDirect(graph, adj, active, visited)) return true;
        }
        active.remove(vertex);
        visited.add(vertex);
        return false;
    }

    public static <W extends Number & Comparable<W>, T> Map<T, Map<T, Double>> floydWarshall(Graph<W, T> graph) {
        Map<T, Map<T, Double>> distances = new HashMap<>();
        List<T> vertices = graph.getAllVertices();
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            T vertex = vertices.get(i);
            distances.put(vertex, new HashMap<>());
            for (int j = 0; j < n; j++) {
                T insideVertex = vertices.get(j);
                if (i == j) {
                    distances.get(vertex).put(insideVertex, 0d);
                } else {
                    Double directWeight = graph.containsEdge(vertex, insideVertex) ?
                            graph.getWeight(vertex, insideVertex).doubleValue() : Double.POSITIVE_INFINITY;
                    distances.get(vertex).put(insideVertex, directWeight);
                }
            }
        }


        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    T thought = vertices.get(k);
                    T from = vertices.get(i);
                    T to = vertices.get(j);
                    Double oldDist = distances.get(from).get(to);
                    Double newDist = distances.get(from).get(thought) + distances.get(thought).get(to);
                    if (oldDist > newDist)
                        distances.get(from).put(to, newDist);
                }
            }
        }

        return distances;
    }
}
