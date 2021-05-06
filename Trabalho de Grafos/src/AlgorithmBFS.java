import org.graphstream.algorithm.Algorithm;

import java.util.LinkedList;

public class AlgorithmBFS {
    static int V;
    static int Graph[][];
    public static int max_flow = 0;

    boolean bfs(int Graph[][], int s, int t, int p[], int Graph_2[][]) {
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        p[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();
            for (int v = 0; v < V; v++) { // percorre todos os vertices
                if (visited[v] == false && Graph[u][v] > 0) {
                    queue.add(v);
                    boolean insert = true;
                    for(int aux = 0; aux < Graph_2.length ;aux++){
                        if(Graph_2[aux][v] > 0)
                            insert = false;
                    }
                    System.out.println();
                    if(insert){
                        Graph_2[u][v] = 1;
                    }
                    p[v] = u;
                    visited[v] = true;
                }
            }
        }

        return (visited[t] == true);
    }

    int[][] fordFulkerson(int graph[][], int s, int t, int tamanho) {
        V = tamanho;
        int u, v;
        int Graph[][] = new int[V][V];
        int Graph_2[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                Graph[u][v] = graph[u][v];

        int p[] = new int[V];

        max_flow = 0;

        while (bfs(Graph, s, t, p,Graph_2)) {
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = p[v]) {
                u = p[v];
                path_flow = Math.min(path_flow, Graph[u][v]);
            }

            for (v = t; v != s; v = p[v]) {
                u = p[v];
                Graph[u][v] -= path_flow;
                Graph[v][u] += path_flow;
            }

            // Adicionando fluxo de caminho
            max_flow += path_flow;
        }

        return Graph_2;
    }
}
