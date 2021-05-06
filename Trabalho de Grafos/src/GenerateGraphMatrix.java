import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import scala.util.parsing.combinator.testing.Str;

import javax.swing.*;
import java.awt.*;

public class GenerateGraphMatrix {
    private static Graph g;
    private static String matriz_adj;
    private static int[][] matrix_fixed;
    public static String outPutMessage = "";
    public static int vertices = 0;
    public static int arestas = 0;
    public static boolean erro = false;
    GenerateGraphMatrix(){
        g = new DefaultGraph("Gráfico");
        g.setStrict(false);
        g.addAttribute("ui.stylesheet","" +
                "edge.breath2 {  " +
                "   fill-color: rgb(44,44,44);\n" +
                "   size: 3px;" +
                "}");

        g.addAttribute("ui.stylesheet","" +
                "edge.breath {  " +
                "   fill-color: rgb(255,90,73);\n" +
                "   size: 3px;" +
                "}");
        Viewer viewer = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        JFrame myJFrame = new JFrame("Gráfico");


        myJFrame.setPreferredSize(new Dimension(400, 470));
        DefaultView view = (DefaultView) viewer.addDefaultView(false);
        view.setPreferredSize(new Dimension(400, 400));
        myJFrame.setLayout(new FlowLayout());
        myJFrame.add(view);
        JSlider slider = new JSlider();
        slider.addChangeListener(e -> view.getCamera().setViewPercent(slider.getValue() / 10.0));
        myJFrame.add(slider);
        myJFrame.pack();
        myJFrame.setVisible(true);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewer.enableAutoLayout();
        ViewerPipe vp = viewer.newViewerPipe();
        vp.addViewerListener(new ViewerListener() {
            @Override
            public void viewClosed(String viewName) {
                // dont care
            }

            @Override
            public void buttonPushed(String id) {
                Node n = g.getNode(id);
                String attributes[] = n.getAttributeKeySet().toArray(new String[n.getAttributeKeySet().size()]);

                String attributeToChange = (String) JOptionPane.showInputDialog(null, "Select attibute to modify", "Attribute for " + id, JOptionPane.QUESTION_MESSAGE, null, attributes, attributes[0]);
                String curValue = n.getAttribute(attributeToChange);
                String newValue
                        = JOptionPane.showInputDialog("New Value", curValue);
                n.setAttribute(attributeToChange, newValue);
            }

            @Override
            public void buttonReleased(String id) {
                // don't care
            }
        });

    }

    public boolean transformMatrix(String MatrixString){
        String[] split = MatrixString.split("\n");
        matrix_fixed = new int[split.length][split.length];
        System.out.println();
        erro = false;
        for (int x = 0; x < split.length; x++) {
            String[] split_2 = (split[x].replaceAll("[^\\d,]", "")).split(",");
            for(int y = 0; y < split_2.length; y++){
                if(split_2.length == split.length){
                    matrix_fixed[x][y] = Integer.parseInt(split_2[y]);
                }else{
                    outPutMessage += "\nErro na linha : [" + x  + "]";
                    erro = true;
                }
            }
            System.out.println();
        }
        return !erro;
    }


    public  void setValueMatrix(String valor){
        matriz_adj = valor;
        outPutMessage = "";
        erro = false;
        arestas = 0;
        vertices = 0;
    }



    public void graphNormal(){
        if(transformMatrix(matriz_adj)) {
            learnMatrix(matrix_fixed);
            erro = false;
        }else
            erro = true;
    }

    public void graphBFS(){
        if(transformMatrix(matriz_adj)){
            AlgorithmBFS bfs = new AlgorithmBFS();
            erro = false;
            learnMatrix(bfs.fordFulkerson(matrix_fixed, 0, matrix_fixed.length-1,matrix_fixed.length));
            outPutMessage = "Max flow: " + bfs.max_flow +  "\n" + outPutMessage;
        }else {
            erro = true;
        }
    }

    public void graphDFS() {
        if (transformMatrix(matriz_adj)) {
            AlgorithmDFS dfs = new AlgorithmDFS(matrix_fixed.length);
            learnMatrix(dfs.start(matrix_fixed));
            outPutMessage = "Sequencia: " + dfs.outPutMessageTest +  "\n" + outPutMessage;
            erro = false;
        } else {
            erro = true;
        }
    }

    public void graphPrim(){
        if(transformMatrix(matriz_adj)){
            AlgorithmPrim prim = new AlgorithmPrim();
            learnMatrix(prim.Prim(matrix_fixed,matrix_fixed.length));
            erro = false;
        }else{
            erro = true;
        }
    }

    public void graphRoy(){
        if(transformMatrix(matriz_adj)){
            AlgorithmRoy roy = new AlgorithmRoy(matrix_fixed.length);
            learnMatrix(roy.start(matrix_fixed));
            erro = false;
        }else{
            erro = true;
        }
    }




    public void learnMatrix(int[][] matrix){
        g.clear();
        outPutMessage = "";
        for (int x = 0; x < matrix.length; x++){
            g.addNode("" + x);

        }
        for (int x = 0; x < matrix.length; x++){
            vertices++;
            outPutMessage += "\n[";
            for (int y = 0; y < matrix[x].length;y++){
                outPutMessage += "  " + matrix[x][y] + (y == matrix[x].length ? "" : "  ,");
                if(matrix[x][y] > 0){
                    g.addEdge(x+""+y, "" + x, ""+ y);
                    arestas++;
                }
            }
            outPutMessage += "]";
        }
        for (Node node : g) {
            node.addAttribute("ui.label", node.getId());
        }
    }


    public void start() {
    }
}
