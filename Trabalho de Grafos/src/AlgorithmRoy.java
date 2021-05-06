public class AlgorithmRoy {
    static int INF = 9999, nV = 4; // numero de vertices / variavel infinita

    void reviewInfinite(int matrix[][]){ // atribui a const Infinito on nao ha ligacao i/j
        for (int x= 0; x < nV; x++) {
            for (int y = 0; y < nV; y++) {
                if(matrix[x][y] == 0 && (x != y)) {
                    matrix[x][y] = INF;
                }
            }
        }
    }

    int[][] start(int graph[][]) {

        int matrix[][] = new int[nV][nV];
        reviewInfinite(graph);
        int i, j, k; // variaveis de controle

        for (i = 0; i < nV; i++)
            for (j = 0; j < nV; j++)
                matrix[i][j] = graph[i][j];

        // Adicionando vertice individualmente
        for (k = 0; k < nV; k++) {
            for (i = 0; i < nV; i++) {
                for (j = 0; j < nV; j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                        System.out.println(matrix[i][j]);
                    }
                }
            }
        }

        return matrix;
    }

    void printMatrix(int matrix[][]) { // imprime matriz
        for (int i = 0; i < nV; ++i) {
            for (int j = 0; j < nV; ++j) {
                if (matrix[i][j] == INF)
                    System.out.print(0 + "  "); // adiciona zero a escrita se INF
                else
                    System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    AlgorithmRoy(int tamanho){
        nV = tamanho;
    }

}
