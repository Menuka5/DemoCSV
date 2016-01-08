package FinalTest.FinalThird;

import democsv.DemoCSV;
import static democsv.DemoCSV.datastore;
import static democsv.DemoCSV.sNames;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Menuka
 */
public class ResponseTime {

    public static List<Object> sections = new ArrayList<>();
    public static String[] switchNames = null;
    public static ArrayList<String> time = new ArrayList<>();
    public static ArrayList<ArrayList<String>> datastore = new ArrayList<>();

    private static final int N = 600;
    private static final String title = "ResponseTime";
    private static final Random random = new Random();
    private static final Shape circle = new Ellipse2D.Double(-3, -3, 6, 6);
    private static final Color line = Color.gray;

    private ChartPanel createPanel() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "Elapsed Time (secs)", "Switch Names",
                createDataset(), PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = chart.getXYPlot();
        MyRenderer renderer = new MyRenderer(true, true, 5);
        plot.setRenderer(renderer);
        renderer.setSeriesShape(0, circle);
        renderer.setSeriesPaint(0, line);
        renderer.setUseFillPaint(true);
        renderer.setSeriesShapesFilled(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setUseOutlinePaint(true);
        renderer.setSeriesOutlinePaint(0, line);
        org.jfree.chart.axis.ValueAxis range = plot.getRangeAxis();
        range.setLowerBound(0.5);
        return new ChartPanel(chart);
    }

    private static class MyRenderer extends XYLineAndShapeRenderer {

        private List<Color> clut;

        public MyRenderer(boolean lines, boolean shapes, int n) {

            clut = new ArrayList<Color>(n);
            for (int i = 0; i < n; i++) {
                clut.add(Color.getHSBColor((float) i / n, 1, 1));
            }

        }

        @Override
        public Paint getItemFillPaint(int row, int column) {

            for (int i = 0; i < datastore.size(); i++) {
                for (int j = 0; j < (datastore.get(i)).size(); j++) {
                    String x = (datastore.get(i)).get(j);
                    System.out.print(x);
                    
                    if ("false".equals(x)) {
                        return Color.red;
                    }else if ("true".equals(x)) {
                        return Color.yellow;
                    }else if ("0".equals(x)) {
                        return Color.white;
                    }else if ("1".equals(x)) {
                        return Color.blue;
                    }else if ("2".equals(x)) {
                        return Color.black;
                    }
                    
                }
            }
            
            //////////////////
//            Random rand = new Random();
//            int min =1;
//            int max = 100;
//            for (int i = 0; i < time.size(); i++) {
//                int rn = rand.nextInt((max - min) + 1) + min;
//                if(rn%2 == 0){
//                    return Color.red;
//                }else if(rn%2 != 0){
//                    return Color.yellow;
//                }
//            }

//            for (int i = switchNames.length - 1; i >= 0; i--) {
//                ArrayList test = (datastore.get(i));
//                for (int j = 0; j < time.size(); j++) {
//                    String ts = (datastore.get(i)).get(j);
//                    System.out.println(ts);
//                
//                    if ("falses".equals(ts)) {
//                        return Color.red;
//                    }else if("true".equals(ts)){
//                        System.out.println("Test out yako");
//                        return Color.yellow;
//                    }else if("0".equals(ts)){
//                        return Color.WHITE;
//                    }else if("1".equals(ts)){
//                        return Color.GREEN;
//                    }
//                    return Color.blue;
//                }
//                        
//            }
//            return Color.yellow;
            return Color.black;
        }

//        @Override
//        public Paint getItemPaint(int row, int col) {
//            
//            //System.out.println(row + " " + col + " " + super.getItemPaint(row, col));
//            return Color.RED;
//        }
    }

    private XYDataset createDataset() {
        ArrayList<Object> xystore = new ArrayList<>();
        for (String sn : switchNames) {
            xystore.add(new XYSeries(sn));
        }
        for (int i = 1; i <= switchNames.length; i++) {
            for (String x : time) {
                double y = Double.parseDouble(x);
                ((XYSeries) xystore.get(i - 1)).add(y, i);

            }
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        for (Object xy : xystore) {
            dataset.addSeries((XYSeries) xy);
        }

        return dataset;
    }

    public void display() {
        JFrame f = new JFrame(title);
        Toolkit d = f.getToolkit();
        Dimension wSize = d.getScreenSize();
        f.setBounds(wSize.width / 4, wSize.height / 2, wSize.width, wSize.height);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createPanel());

        RefineryUtilities.centerFrameOnScreen(f);
        f.setVisible(true);
    }

    public static void dataTake() throws IOException {
        InputStream in = DemoCSV.class.getResourceAsStream("/files/Electrical.csv");
        InputStream in2 = DemoCSV.class.getResourceAsStream("/files/Electrical.csv");

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        BufferedReader read = new BufferedReader(new InputStreamReader(in2));
        String line, line2;
        int cnt = 0;

        while ((line = reader.readLine()) != null) {
            String[] dataIn = line.split(",");

            if (cnt == 0) {
                switchNames = sNames(dataIn);

            } else if (cnt != 0) {
                time.add(dataIn[0]);
            }
            cnt++;
        }

        cnt = 0;

        int dsize = switchNames.length;

        while (datastore.size() < dsize) {
            datastore.add(new ArrayList<>());
        }

        while ((line2 = read.readLine()) != null) {
            String[] dataIn2 = line2.split(",");
//            System.out.println(Arrays.toString(dataIn2));

            if (cnt != 0) {
                for (int i = 1; i <= dsize; i++) {
                    datastore.get(i - 1).add(dataIn2[i]);
//                    System.out.print(dataIn2[i]);
                }
            }
            cnt++;
        }

        for (String sn : switchNames) {
            sections.add(new XYSeries(sn));
        }

    }

    public static void main(String[] args) throws IOException {
        dataTake();
        EventQueue.invokeLater(() -> {
            new ResponseTime().display();
        });
    }
}
