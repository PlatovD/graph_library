package io.github.PlatovD.generator;

import io.github.PlatovD.Graph;
import io.github.PlatovD.GraphType;

public interface GraphGenerator {
    Graph<Double, String> generateStringGraph(GraphType graphType, Integer vertexCnt, Integer edgeCnt);

    Graph<Double, Integer> generateIntegerGraph(GraphType graphType, Integer vertexCnt, Integer edgeCnt);
}
