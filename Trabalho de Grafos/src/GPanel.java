import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GPanel {
    private JPanel panel1;
    private JToolBar bar_icon;
    private JTextArea code;
    private JTextField lineColumn;
    private JScrollPane scroll_bar_code;
    private JScrollPane scroll_bar_build;
    private JTextArea build;
    private static JFrame frame;
    private static JTextArea lines;
    private static GenerateGraphMatrix graphic_panel;
    private static int columnnum = 0;
    private static int linenum = 1;
    private static GPanel graphInterface;
    private static File selectedFile = null;
    private static Boolean changed = false;
    private static String compiladorResult = "   Você ainda não compilou o arquivo do gráfico.";
    private static String type_compile = "Normal";



    private static JMenu createEdicaoMenu() {
        JMenu edicaoMenu = new JMenu("Edição");

        JMenuItem copiar = new JMenuItem("Copiar");
        copiar.addActionListener(new DefaultEditorKit.CopyAction());
        copiar.setBackground(new Color(60,63,65));
        copiar.setForeground(new Color(187,187,187));
        copiar.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        edicaoMenu.add(copiar);


        JMenuItem colar = new JMenuItem("Colar");
        colar.addActionListener(new DefaultEditorKit.PasteAction());
        colar.setBackground(new Color(60,63,65));
        colar.setForeground(new Color(187,187,187));
        colar.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        edicaoMenu.add(colar);

        JMenuItem recortar = new JMenuItem("Recortar");
        recortar.addActionListener(new DefaultEditorKit.CutAction());
        recortar.setBackground(new Color(60,63,65));
        recortar.setForeground(new Color(187,187,187));
        recortar.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        edicaoMenu.add(recortar);
        edicaoMenu.setBackground(new Color(60,63,65));
        edicaoMenu.setForeground(new Color(187,187,187));
        edicaoMenu.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        return edicaoMenu;
    }

    private static JMenu createCompilacaoMenu() {
        JMenu compilacaoMenu = new JMenu("Compilação");
        JMenuItem executar = new JMenuItem("Executar");
        executar.setBackground(new Color(60,63,65));
        executar.setForeground(new Color(187,187,187));
        executar.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        executar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                run();
            }
        });



        JMenuItem normal = new JMenuItem("Definir G. Normal");
        normal.setBackground(new Color(60,63,65));
        normal.setForeground(new Color(187,187,187));
        normal.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        normal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                defineType("Normal");
            }
        });


        JMenuItem bfs = new JMenuItem("Definir BFS");
        bfs.setBackground(new Color(60,63,65));
        bfs.setForeground(new Color(187,187,187));
        bfs.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        bfs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                defineType("BFS");
            }
        });


        JMenuItem dfs = new JMenuItem("Definir DFS");
        dfs.setBackground(new Color(60,63,65));
        dfs.setForeground(new Color(187,187,187));
        dfs.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        dfs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                defineType("DFS");
            }
        });


        JMenuItem roy = new JMenuItem("Definir Roy");
        roy.setBackground(new Color(60,63,65));
        roy.setForeground(new Color(187,187,187));
        roy.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        roy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                defineType("Roy");
            }
        });


        JMenuItem prim = new JMenuItem("Definir Prim");
        prim.setBackground(new Color(60,63,65));
        prim.setForeground(new Color(187,187,187));
        prim.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        prim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                defineType("Prim");
            }
        });
        compilacaoMenu.add(executar);
        compilacaoMenu.add(normal);
        compilacaoMenu.add(bfs);
        compilacaoMenu.add(dfs);
        compilacaoMenu.add(prim);
        compilacaoMenu.add(roy);

        compilacaoMenu.setBackground(new Color(60,63,65));
        compilacaoMenu.setForeground(new Color(187,187,187));

        return compilacaoMenu;
    }

    private static JMenu createArquivoMenu() {
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenuItem arquivo_novo = new JMenuItem("Novo");
        arquivo_novo.setBackground(new Color(60,63,65));
        arquivo_novo.setForeground(new Color(187,187,187));
        arquivo_novo.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        arquivoMenu.add(arquivo_novo);
        arquivo_novo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                newFile();
            }
        });

        JMenuItem arquivo_abrir = new JMenuItem("Abrir");
        arquivo_abrir.setBackground(new Color(60,63,65));
        arquivo_abrir.setForeground(new Color(187,187,187));
        arquivo_abrir.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        arquivoMenu.add(arquivo_abrir);
        arquivo_abrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                openFile();
            }
        });


        JMenuItem arquivo_salvar = new JMenuItem("Salvar");
        arquivo_salvar.setBackground(new Color(60,63,65));
        arquivo_salvar.setForeground(new Color(187,187,187));
        arquivo_salvar.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        arquivoMenu.add(arquivo_salvar);

        arquivo_salvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(selectedFile != null)
                    saveFile();
                else
                    saveAsFile();
            }
        });

        JMenuItem arquivo_salvar_como = new JMenuItem("Salvar como");
        arquivo_salvar_como.setBackground(new Color(60,63,65));
        arquivo_salvar_como.setForeground(new Color(187,187,187));
        arquivo_salvar_como.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        arquivoMenu.add(arquivo_salvar_como);
        arquivo_salvar_como.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                saveAsFile();
            }
        });


        JMenuItem arquivo_sair = new JMenuItem("Sair");
        arquivo_sair.setBackground(new Color(60,63,65));
        arquivo_sair.setForeground(new Color(187,187,187));
        arquivo_sair.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        arquivoMenu.add(arquivo_sair);
        arquivo_sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                exit();
            }
        });
        arquivoMenu.setBackground(new Color(60,63,65));
        arquivoMenu.setForeground(new Color(187,187,187));
        arquivoMenu.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        return arquivoMenu;
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createArquivoMenu());
        menuBar.add(createEdicaoMenu());
        menuBar.add(createCompilacaoMenu());
        menuBar.setBackground(new Color(60,63,65));
        menuBar.setForeground(new Color(187,187,187));

        return menuBar;
    }

    private static boolean allowedExit(){
        String[] options = {"Sim", "Não","Cancel"};
        int x = JOptionPane.showOptionDialog(null, "Você possui alterações não salvas, você deseja salvar?",
                "Selecione uma opção",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        System.out.println(x);
        if(x == 0) {
            if(selectedFile != null)
                return saveFile();
            else
                return saveAsFile();
        }else if(x == 1){
            return true;
        }
        return false;
    }

    private static void newFile(){

        if(!changed) {
            frame.setTitle("Gráfico - Novo arquivo");
            selectedFile = null;
            graphInterface.code.setText("");
        }else{
            if(allowedExit()){
                frame.setTitle("Gráfico - Novo arquivo");
                selectedFile = null;
                graphInterface.code.setText("");
            }
        }
    }

    private static void exit() {
        if (!changed){
            System.exit(0);
        }else{
            if(allowedExit())
                System.exit(0);
        }
    }

    private static void run(){
        graphic_panel.setValueMatrix(graphInterface.code.getText());
        if(type_compile == "Normal")
            graphic_panel.graphNormal();
        else if(type_compile == "DFS") {
            graphic_panel.graphDFS();
        }else if(type_compile == "BFS")
            graphic_panel.graphBFS();
        else if(type_compile == "Prim")
            graphic_panel.graphPrim();
        else
            graphic_panel.graphRoy();

        if(!graphic_panel.erro){
            graphInterface.build.setForeground(new Color(203, 102, 45));
            graphInterface.lineColumn.setForeground(new Color(0, 255, 0));
            compiladorResult = "   Gráfico compilado com sucesso.";
            graphInterface.build.setText("Vertices:" + graphic_panel.vertices + "\nArestas:" + graphic_panel.arestas  + "\n=====MATRIZ=====\n" + graphic_panel.outPutMessage);

        }else{
            graphInterface.build.setForeground(new Color(255,96,74));
            graphInterface.lineColumn.setForeground(new Color(255,96,74));
            graphInterface.build.setText("Verifique os erros na linha abaixo, nao foi possivel interpretar o grafico corretamente:"  + "\n" + graphic_panel.outPutMessage);
            compiladorResult = "   Não foi possível compilar o gráfico." ;
        }

        graphInterface.lineColumn.setText("Compilação: " + type_compile + " - " + compiladorResult);
    }

    private static void createToolBar() {
        ImageIcon new_file = new ImageIcon("src/icon/new-file.png");
        ImageIcon run = new ImageIcon("src/icon/run.png");
        ImageIcon build = new ImageIcon("src/icon/build.png");
        ImageIcon colar = new ImageIcon("src/icon/colar.png");
        ImageIcon copiar = new ImageIcon("src/icon/copiar.png");
        ImageIcon open_file = new ImageIcon("src/icon/open-file.png");
        ImageIcon recortar = new ImageIcon("src/icon/recortar.png");
        ImageIcon save_file = new ImageIcon("src/icon/save-file.png");

        JButton newFileButton = new JButton(new_file);
        newFileButton.setBackground(new Color(60,63,65));
        newFileButton.setBorderPainted(false);
        newFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                newFile();
            }
        });
        JButton runButton = new JButton(run);
        runButton.setBackground(new Color(60,63,65));
        runButton.setBorderPainted(false);
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                run();
            }
        });


        //Menu de edição
        JButton colarButton = new JButton(colar);
        colarButton.setBackground(new Color(60,63,65));
        colarButton.setBorderPainted(false);
        colarButton.addActionListener(new DefaultEditorKit.PasteAction());


        JButton copiarButton = new JButton(copiar);
        copiarButton.setBackground(new Color(60,63,65));
        copiarButton.setBorderPainted(false);
        copiarButton.addActionListener(new DefaultEditorKit.CopyAction());


        JButton recortarButton = new JButton(recortar);
        recortarButton.setBackground(new Color(60,63,65));
        recortarButton.setBorderPainted(false);
        recortarButton.addActionListener(new DefaultEditorKit.CutAction());

        JButton saveFileButton = new JButton(save_file);
        saveFileButton.setBackground(new Color(60,63,65));
        saveFileButton.setBorderPainted(false);

        saveFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(selectedFile != null)
                    saveFile();
                else
                    saveAsFile();
            }
        });


        JButton openFileButton = new JButton(open_file);
        openFileButton.setBackground(new Color(60,63,65));
        openFileButton.setBorderPainted(false);


        openFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                openFile();
            }
        });


        graphInterface.bar_icon.add(newFileButton);
        graphInterface.bar_icon.add(openFileButton);
        graphInterface.bar_icon.add(saveFileButton);

        graphInterface.bar_icon.addSeparator();

        graphInterface.bar_icon.add(copiarButton);
        graphInterface.bar_icon.add(colarButton);
        graphInterface.bar_icon.add(recortarButton);

        JSeparator sep = new JSeparator();
        sep.setForeground(Color.green); // top line color
        sep.setBackground(Color.green.brighter()); // bottom line color
        graphInterface.bar_icon.addSeparator();
        graphInterface.bar_icon.add(runButton);

        graphInterface.bar_icon.setBackground(new Color(60,63,65));
        graphInterface.bar_icon.setBorder(new LineBorder(new Color(81,81,81),0));

    }

    private static  boolean saveAsFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GRAPH", "graph");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(fileChooser.getParent());
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            frame.setTitle("Gráfico - " + selectedFile.getName());
            saveFile();
            changed = false;
            return true;
        }
        return false;
    }

    private static  boolean saveFile(){
        try{
            FileWriter fw = new FileWriter(selectedFile.getPath());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write((graphInterface.code.getText()));
            bw.flush();
            bw.close();
            changed = false;
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private static void openFile(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GRAPH", "graph");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(fileChooser.getParent());
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            frame.setTitle("Gráfico - " + selectedFile.getName());
            try{
                BufferedReader br = new BufferedReader(new FileReader(selectedFile.getPath()));
                String line = "", texto = "";
                while((line = br.readLine()) != null)
                    texto += line + "\n";
                graphInterface.code.setText(texto);
                if(br != null)
                    br.close();
                changed = false;
            }catch (Exception e){

            }
        }
    }


    private static void defineType(String valor){
        type_compile = valor;
        graphInterface.lineColumn.setText("Compilação: " + type_compile + " - " + compiladorResult + " - L:" + linenum + "/C:" + columnnum);
    }

    public static void main(String[] args){
        graphic_panel = new GenerateGraphMatrix();
        graphInterface = new GPanel();
        frame = new JFrame("Gerador de Gráficos");
        frame.setSize(500, 400);

        graphInterface.code.setText("{0,28,0,0,0,10,0}\n" +
                "{0,0,15,0,0,0,14}\n" +
                "{0,0,0,0,0,0,0}\n" +
                "{0,0,0,0,0,0,0}\n" +
                "{0,0,0,0,0,0,24}\n" +
                "{0,0,0,0,25,0,0}\n" +
                "{0,0,0,18,0,0,0}");

        lines = new JTextArea("1");
        lines.setEditable(false);
        lines.setBackground(new Color(49,51,53));
        lines.setForeground(new Color(91,94,97));
        lines.setMargin(new Insets(0,20,0,20));

        graphInterface.scroll_bar_build.setBackground(new Color(81,81,81));

        Font fontBold = new Font("Build",Font.BOLD,graphInterface.build.getFont().getSize());
        graphInterface.build.setFont(fontBold);
        graphInterface.build.setBackground(new Color(43,43,43));
        graphInterface.scroll_bar_build.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        graphInterface.scroll_bar_build.setBackground(new Color(81,81,81));
        graphInterface.build.setBackground(new Color(43,43,43));
        graphInterface.build.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        graphInterface.scroll_bar_build.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        graphInterface.scroll_bar_build.getVerticalScrollBar().setBackground(new Color(60,63,65));
        graphInterface.scroll_bar_build.getVerticalScrollBar().setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        graphInterface.scroll_bar_build.getVerticalScrollBar().setPreferredSize( new Dimension(8,1));
        graphInterface.scroll_bar_build.getHorizontalScrollBar().setBackground(new Color(60,63,65));
        graphInterface.scroll_bar_build.getHorizontalScrollBar().setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        graphInterface.scroll_bar_build.getHorizontalScrollBar().setPreferredSize( new Dimension(8,1));

        graphInterface.scroll_bar_code.setRowHeaderView(lines);
        graphInterface.scroll_bar_code.setBackground(new Color(81,81,81));
        graphInterface.code.setMargin(new Insets(0,10,0,0));
        graphInterface.code.setBackground(new Color(43,43,43));
        graphInterface.code.setForeground(new Color(169,183,198));
        graphInterface.code.setSelectionColor(new Color(33,66,131));
        graphInterface.code.setSelectedTextColor(new Color(169,183,198));
        graphInterface.panel1.setBackground(new Color(81,81,81));
        graphInterface.lineColumn.setBackground(new Color(60,63,65));

        graphInterface.lineColumn.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        graphInterface.code.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));

        graphInterface.scroll_bar_code.getVerticalScrollBar().setBackground(new Color(60,63,65));
        graphInterface.scroll_bar_code.getVerticalScrollBar().setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        graphInterface.scroll_bar_code.getVerticalScrollBar().setPreferredSize( new Dimension(8,1));
        graphInterface.scroll_bar_code.getHorizontalScrollBar().setBackground(new Color(60,63,65));
        graphInterface.scroll_bar_code.getHorizontalScrollBar().setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        graphInterface.scroll_bar_code.getHorizontalScrollBar().setPreferredSize( new Dimension(8,1));

        graphInterface.scroll_bar_code.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0)));
        graphInterface.lineColumn.setText("Compilação: " + type_compile + " - " + "Você ainda não compilou o arquivo do gráfico.");
        graphInterface.lineColumn.setForeground(new Color(203,102,45));
        graphInterface.code.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                JTextArea editArea = (JTextArea)e.getSource();
                linenum = 1;
                columnnum = 1;
                try {
                    int caretpos = editArea.getCaretPosition();
                    linenum = editArea.getLineOfOffset(caretpos);
                    columnnum = caretpos - editArea.getLineStartOffset(linenum);
                    linenum += 1;
                }
                catch(Exception ex) { }
                graphInterface.lineColumn.setText("Compilação: " + type_compile + " - " + compiladorResult + " - L:" + linenum + "/C:" + columnnum);
            }
        });
        graphInterface.code.getDocument().addDocumentListener(new DocumentListener() {


            public String getText(){
                int caretPosition = graphInterface.code.getDocument().getLength();
                Element root = graphInterface.code.getDocument().getDefaultRootElement();
                String text = "1" + System.getProperty("line.separator");
                for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
                    text += i  + System.getProperty("line.separator");
                }
                return text;
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                changed = true;
                lines.setText(getText());
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed = true;
                lines.setText(getText());
            }
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                changed = true;
                lines.setText(getText());
            }
        });

        createToolBar();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setContentPane(graphInterface.panel1);
        frame.setJMenuBar(createMenuBar());
        frame.setVisible(true);
    }

}
