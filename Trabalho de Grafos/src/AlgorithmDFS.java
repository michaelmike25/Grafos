import java.util.Iterator;
import java.util.LinkedList;

public class AlgorithmDFS {
    private LinkedList<Integer> adjLists[];
    private boolean visited[];
    public static String outPutMessageTest = "";
    public static int[][] matrix;
    AlgorithmDFS(){

    }

    AlgorithmDFS(int vertices) {
        adjLists = new LinkedList[vertices];
        visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++)
            adjLists[i] = new LinkedList<Integer>();
    }
    void addEdge(int src, int dest) {
        adjLists[src].add(dest);
    }

    void DFS(int vertex) {
        visited[vertex] = true;
        Iterator<Integer> ite = adjLists[vertex].listIterator();
        while (ite.hasNext()) {
            int adj = ite.next();
            if (!visited[adj]) {
                DFS(adj);
                matrix[vertex][adj] = 1;
                outPutMessageTest += "\n" +  vertex + " -> " + adj + " ; ";
            }
        }
    }

    public int[][] start(int graph[][]){
        int nV = graph.length;
        matrix = new int[nV][nV];
        AlgorithmDFS g = new AlgorithmDFS(nV);
        for (int x= 0; x < nV; x++) {
            for (int y = 0; y < nV; y++) {
                if(graph[x][y] > 0)
                    g.addEdge(x, y);
            }
        }
        g.DFS(0);

        return matrix;
    }

}
