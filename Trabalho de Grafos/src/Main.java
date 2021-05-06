import org.graphstream.graph.*;

public class Main {
    public static Graph g;



    public static void dfs_algorithm() throws InterruptedException {
        try {
            DepthFirstIterator it2 = new DepthFirstIterator(g.getNode(0));

            while(it2.hasNext()){
                Node n = it2.next();
                for(Edge e: n.getEachEdge()){
                    e.addAttribute("ui.class","breath2");
                    Thread.sleep(1000);
                }
            }
            DepthFirstIterator it3 = new DepthFirstIterator(g.getNode(0));

            while(it3.hasNext()){
                Node n = it3.next();
                for(Edge e: n.getEachEdge()){
                    e.addAttribute("ui.class","breath");
                    Thread.sleep(1000);
                }
            }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }

    }


    public static void bfs_algorithm() throws InterruptedException {
        try {
            BreadthFirstIterator it2 = new BreadthFirstIterator(g.getNode(0));

            while(it2.hasNext()){
                Node n = it2.next();
                for(Edge e: n.getEachEdge()){
                    e.addAttribute("ui.class","breath2");
                    Thread.sleep(1000);
                }
            }
            BreadthFirstIterator it3 = new BreadthFirstIterator(g.getNode(0));

            while(it3.hasNext()){
                Node n = it3.next();
                for(Edge e: n.getEachEdge()){
                    e.addAttribute("ui.class","breath");
                    Thread.sleep(1000);
                }
            }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }

    }



    public static void main(String args[]) throws InterruptedException {

        int V = 7;

        // create a 2d array of size 5x5
        // for adjacency matrix to represent graph
        int[][] G = new int[][] {
                {0,28,0,0,0,10,0},
                {0,0,15,0,0,0,14},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,24},
                {0,0,0,0,25,0,0},
                {0,0,0,18,0,0,0}
        };

        int[][] royvetor = new int[][]{
                {0,1,1,0},
                {1,0,0,1},
                {1,1,0,1},
                {1,1,1,0}
        };

        AlgorithmPrim prim = new AlgorithmPrim();
        AlgorithmBFS bfs = new AlgorithmBFS();
        AlgorithmDFS dfs = new AlgorithmDFS();
        //AlgorithmRoy roy = new AlgorithmRoy(4);
        GenerateGraphMatrix matriz = new GenerateGraphMatrix();
        matriz.learnMatrix(royvetor);
        matriz.start();
        //prim.Prim(G,V);
        //dfs.start(G);


        //roy.printMatrix(roy.start(royvetor));
        //matriz.learnMatrix(roy.start(royvetor));
        //matriz.start();
        //bfs.fordFulkerson(G, 0, V-1,V);
        //matriz.learnMatrix(bfs.fordFulkerson(G, 0, V-1,V));
        //matriz.learnMatrix(prim.Prim(G,V));
        //matriz.start();


/*
        g = new DefaultGraph("my beautiful graph");
        g.setStrict(false);
        Viewer viewer = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        JFrame myJFrame = new JFrame();
        myJFrame.setPreferredSize(new Dimension(600, 600));
        DefaultView view = (DefaultView) viewer.addDefaultView(false);   // false indicates "no JFrame".
        view.setPreferredSize(new Dimension(400, 400));
        myJFrame.setLayout(new FlowLayout());
        myJFrame.add(view);
        JButton myButton = new JButton("MyButton");
        myButton.addActionListener(e -> {
            try {
                bfs_algorithm();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        myJFrame.add(myButton);
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
        g.addNode("0");
        g.addNode("1");
        g.addNode("2");
        g.addNode("3");
        g.addNode("4");
        g.addNode("5");
        g.addNode("6");

        g.addEdge("01", "0", "1");
        g.getEdge("01").setAttribute("weight",28);
        g.addEdge("05", "0", "5");
        g.getEdge("05").setAttribute("weight",10);
        g.addEdge("54", "5", "4");
        g.getEdge("54").setAttribute("weight",25);
        g.addEdge("12", "1", "2");
        g.getEdge("12").setAttribute("weight",16);
        g.addEdge("16", "1", "6");
        g.getEdge("16").setAttribute("weight",14);
        g.addEdge("63", "6", "3");
        g.getEdge("63").setAttribute("weight",18);
        g.addEdge("46", "4", "6");
        g.getEdge("46").setAttribute("weight",24);

        g.getNode("0").setAttribute("size", "big");
        g.getNode("1").setAttribute("size", "medium");
        g.getNode("2").setAttribute("size", "small");
        g.getNode("3").setAttribute("ui:color", "red");
        g.getNode("4").setAttribute("ui:color", "blue");
        g.getNode("5").setAttribute("ui:color", "green");
        for (Node node : g) {
            node.addAttribute("ui.label", node.getId());
        }
        dfs_algorithm();*/

    }
}
