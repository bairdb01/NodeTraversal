import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ben on 2016-11-03.
 */
public class Graph extends JPanel {
    static Node [] nodes;
    static Edge [] edges;
    private static int minX = 9999999;
    private static int minY = 9999999;
    private static int maxX = 0;
    private static int maxY = 0;
    private static ArrayList<Integer []> visitedNodes = new ArrayList<>();

    private static final int PREF_W = 800;
    private static final int PREF_H = 650;
    static FindPath searchPath;
    static JFrame frame;
    static JPanel toolbar;
    static Graph graph;
    static JLabel srcLabel;
    static JLabel sinkLabel;
    static JTextField srcNode;
    static JTextField sinkNode;
    static JButton loadBtn;


    public void addNodes (Node [] nodes) {
        this.nodes = nodes;
    }

    public void addEdges(Edge [] edges) {
        this.edges = edges;
    }

    private double normalize(double value,double min, double max){
        return ((value-min)/(max-min));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        int width = getWidth() - 10 ;
        int height = getHeight() - 10;

        if (edges == null || nodes == null) return;
        for(Node node:nodes) {
            int x1 = (int)(normalize((double)node.getXCoord(),(double)minX,(double)maxX) * ((double)width-10.) + 10.);
            int y1 = height - (int)(normalize((double)node.getYCoord(), (double)minY, (double)maxY) * ((double)height-10.) + 10.);
            // Offset a little bit to be able to draw
            if (y1 == 0)
                y1 += 10;
            g2.setColor(Color.black);
            g2.fillOval( x1, y1, 5, 5);
//            g.drawString(Integer.toString(node.getId()), x1, y1);

            // Draw edges to neighbouring nodes
            Iterator it = node.getEdges();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                int x2 = (int)(normalize((double)nodes[(Integer)pair.getKey()].getXCoord(), minX, maxX) * ((double)width-10.) + 10.);
                int y2 = height - (int)(normalize((double)nodes[(Integer)pair.getKey()].getYCoord(), minY, maxY) * ((double)height-10.) + 10.);
                if (y2 == 0)
                    y2 += 10;
                drawEdge(g, x1, y1, x2, y2, Color.red);
            }
        }

        // Draw visited nodes
        if (visitedNodes == null)
            return;
        g2.setStroke(new BasicStroke(1));
        for(Integer [] nodePath : visitedNodes) {
            Node startNode = nodes[nodePath[0]];
             Node endNode = nodes[nodePath[1]];
            int startX = (int)(normalize((double)startNode.getXCoord(), minX, maxX) * ((double)width-10.) + 10.);
            int startY = height - (int)(normalize((double)startNode.getYCoord(), minY, maxY) * ((double)height-10.) + 10.);
            if (startY == 0)
                startY += 10;
            int endX = (int)(normalize((double)endNode.getXCoord(), minX, maxX) * ((double)width-10.) + 10.);
            int endY = height - (int)(normalize((double)endNode.getYCoord(), minY, maxY) * ((double)height-10.) + 10.);
            if (endY == 0)
                endY += 10;

             drawEdge(g, startX, startY, endX, endY, Color.green);
         }

         // Colour the goal path
         String path = searchPath.getPath();
        g2.setStroke(new BasicStroke(5));
         String [] pathNodes = path.split(",");
        if (path.equals(""))
            return;

         for(int i = 1; i < pathNodes.length; i++){
             try {
                 Node startNode = nodes[Integer.parseInt(pathNodes[i-1])];
                 Node endNode = nodes[Integer.parseInt(pathNodes[i])];
                 int startX = (int) (normalize((double) startNode.getXCoord(), minX, maxX) * ((double) width - 10.) + 10.);
                 int startY = height - (int) (normalize((double) startNode.getYCoord(), minY, maxY) * ((double) height - 10.) + 10.);
                 if (startY == 0)
                     startY += 10;
                 int endX = (int) (normalize((double) endNode.getXCoord(), minX, maxX) * ((double) width - 10.) + 10.);
                 int endY = height - (int) (normalize((double) endNode.getYCoord(), minY, maxY) * ((double) height - 10.) + 10.);
                 if (endY == 0)
                     endY += 10;
                 drawEdge(g, startX, startY, endX, endY, Color.MAGENTA);

             } catch (NumberFormatException e) {
                 System.out.println("Error drawing goal path");
                 return;
             }
         }

        int nodeStartX = (int) (normalize((double) nodes[Integer.parseInt(pathNodes[0])].getXCoord(), minX, maxX) * ((double) width - 10.) + 10.);
        int nodeStartY = height - (int) (normalize((double) nodes[Integer.parseInt(pathNodes[0])].getYCoord(), minY, maxY) * ((double) height - 10.) + 10.);
        if (nodeStartY == 0)
            nodeStartY += 10;
        int nodeEndX = (int) (normalize((double) nodes[Integer.parseInt(pathNodes[pathNodes.length - 1])].getXCoord(), minX, maxX) * ((double) width - 10.) + 10.);
        int nodeEndY = height - (int) (normalize((double) nodes[Integer.parseInt(pathNodes[pathNodes.length - 1])].getYCoord(), minY, maxY) * ((double) height - 10.) + 10.);
        if (nodeEndY == 0)
            nodeEndY += 10;

         // Draw start/end nodes last to not be overwritten
        g2.setColor(Color.white);
        g.fillOval( nodeStartX, nodeStartY, 8, 8);
        g2.setColor(Color.blue);
        g.fillOval( nodeEndX, nodeEndY, 8, 8);


    }

    public static void drawEdge(Graphics g, int x1, int y1, int x2, int y2, Color c){
        g.setColor(c);
        g.drawLine(x1, y1, x2, y2);
    }

    static ArrayList<Integer []> search(FindPath pathSearch, int src, int dest){
        if (src > nodes.length-1 || dest > nodes.length-1 || src < 0 || dest < 0)
            return null;
        return pathSearch.search(nodes, src,dest);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

     static void createAndShowGUI() {
         searchPath = new FindPath(nodes);
         frame = new JFrame("City Traveler");
         frame.getContentPane().setLayout(new BorderLayout());
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         toolbar = new JPanel();
         graph = new Graph();
         srcLabel = new JLabel("Source:");
         sinkLabel = new JLabel("Destination:");
         srcNode = new JTextField("0");
         sinkNode = new JTextField("0");
         sinkNode.setColumns(10);
         srcNode.setColumns(10);
         loadBtn = new JButton("Load File");
         loadBtn.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 System.out.println("load");
                 visitedNodes = null;
                 JFileChooser fc = new JFileChooser();
                 int returnVal = fc.showOpenDialog(toolbar);
                 if (returnVal == JFileChooser.APPROVE_OPTION) {
                     try {
                         // Load the file
                         FileInputStream fis = new FileInputStream(fc.getSelectedFile());
                         InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                         BufferedReader br = new BufferedReader(isr);
                         String line = br.readLine();
                         String[] line_toks;
                         Node[] nodes;
                         Edge[] edges;

                         // Read how many nodes are needed
                         if (line != null) {
                             line_toks = line.split(" ");
                             if (line_toks.length == 2) {
                                 nodes = new Node[Integer.parseInt(line_toks[0])];
                                 edges = new Edge[Integer.parseInt(line_toks[1])];
                             } else {
                                 System.out.println("1 Line has invalid number of tokens");
                                 return;
                             }
                         } else {
                             System.out.println("2 Line is null");
                             return;
                         }

                         // Read the first chunk for node positions
                         line = br.readLine();
                         for (int i = 0; i < nodes.length; i++) {
                             if (line == null)
                                 return;
                             line_toks = line.split(" ");
                             int x = Integer.parseInt(line_toks[1]);
                             int y = Integer.parseInt(line_toks[2]);
                             nodes[i] = new Node(i, x, y);
                             minX = Math.min(x, minX);
                             minY = Math.min(y, minY);
                             maxX = Math.max(x, maxX);
                             maxY = Math.max(y, maxY);
                             line = br.readLine();
                         }

                         line = br.readLine();   // Currently at Empty line, so read next
                         // Read Next Section for edges
                         for (int i = 0; i < edges.length; i++) {

                             if (line == null) {
                                 System.out.println("3 Line is null");
                                 return;
                             }
                             line_toks = line.split(" ");
                             edges[i] = new Edge();
                             if (line_toks.length == 2) {
                                 int nodeID1 = Integer.parseInt(line_toks[0]);
                                 int nodeID2 = Integer.parseInt(line_toks[1]);
                                 nodes[nodeID1].addEdge(edges[i], nodeID2);
                                 nodes[nodeID2].addEdge(edges[i], nodeID1);
                                 line = br.readLine();
                             } else {
                                 System.out.println("4 Line has invalid number of tokens");
                                 return;
                             }


                         }

                         // Read the src->dest nodes (Last line of file)
                         if (line == null) {
                             System.out.println("5 No src->dest nodes found");
                             srcNode.setText("0");
                             sinkNode.setText("0");
                         } else {
                             line_toks = line.split(" ");
                             if (line_toks.length == 2) {
                                 srcNode.setText(line_toks[0]);
                                 sinkNode.setText(line_toks[1]);
                             } else {
                                 System.out.println("6 line tokens");
                                 return;
                             }
                         }

                         graph.addNodes(nodes);
                         graph.addEdges(edges);
                         searchPath.createQueue(nodes);
                         frame.revalidate();
                         frame.repaint();
                     } catch (IOException err) {
                         err.printStackTrace();
                     }
                 }
             }
         });
         JButton searchBtn = new JButton("Find Path");


         searchBtn.setActionCommand("search");
         searchBtn.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 System.out.println("search");
                 try {
                     Graph.visitedNodes = search(searchPath, Integer.parseInt(srcNode.getText()), Integer.parseInt(sinkNode.getText()));
                     frame.revalidate();
                     frame.repaint();
//                     Iterator it = visitedNodes.entrySet().iterator();
//                     while(it.hasNext()) {
//                         Map.Entry pair = (Map.Entry)it.next();
//                         int startKey = (Integer)pair.getKey();
//                         Node endNode = (Node)pair.getValue();
//                     }
                 } catch (NumberFormatException numErr) {
//                     numErr.printStackTrace();
                 }
             }
         });

//         srcNode.addFocusListener(new FocusListener() {
//             @Override
//             public void focusGained(FocusEvent e) {
//                 srcNode.setText("");
//             }
//
//             @Override
//             public void focusLost(FocusEvent e) {
//             }
//         });

//         sinkNode.addFocusListener(new FocusListener() {
//             @Override
//             public void focusGained(FocusEvent e) {
//                 sinkNode.setText("");
//             }
//
//             @Override
//             public void focusLost(FocusEvent e) {
//             }
//         });
         frame.getContentPane().setBackground( Color.gray );
         toolbar.setLayout(new FlowLayout());
         JButton refreshBtn = new JButton("Refresh");
         refreshBtn.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 visitedNodes = null;
                 frame.revalidate();
                 frame.repaint();
             }
         });
         toolbar.add(refreshBtn);
         toolbar.add(loadBtn);
         toolbar.add(srcLabel);
         toolbar.add(srcNode);
         toolbar.add(sinkLabel);
         toolbar.add(sinkNode);
         toolbar.add(searchBtn);
         toolbar.setVisible(true);
         frame.add(toolbar, BorderLayout.NORTH);
         frame.add(graph, BorderLayout.CENTER);
         frame.pack();
         frame.setLocationByPlatform(true);
         frame.setLocationRelativeTo(null);
         frame.setResizable(true);
         frame.setVisible(true);
     }
}
