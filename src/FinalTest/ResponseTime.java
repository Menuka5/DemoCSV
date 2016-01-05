package FinalTest;

import democsv.DemoCSV;
import static democsv.DemoCSV.datastore;
import static democsv.DemoCSV.sNames;
import static democsv.DemoCSV.sections;
import static democsv.DemoCSV.switchNames;
import static democsv.DemoCSV.time;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Menuka
 */
public class ResponseTime {

    public static List<Object> sections = new ArrayList<>();
    public static String[] switchNames = null;
    public static ArrayList<String> time = new ArrayList<>();
    public static ArrayList<ArrayList<String>> datastore = new ArrayList<>();
//////////////////////////////////////////////////////    
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
        MyRenderer renderer = new MyRenderer(true, true, N);
        plot.setRenderer(renderer);
        renderer.setSeriesShape(0, circle);
        renderer.setSeriesPaint(0, line);
        renderer.setUseFillPaint(true);
        renderer.setSeriesShapesFilled(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setUseOutlinePaint(true);
        renderer.setSeriesOutlinePaint(0, line);
        ValueAxis range = plot.getRangeAxis();
        range.setLowerBound(0.5);
        return new ChartPanel(chart);
    }

    private static class MyRenderer extends XYLineAndShapeRenderer {

        private List<Color> clut;

        public MyRenderer(boolean lines, boolean shapes, int n) {
            super(lines, shapes);
            clut = new ArrayList<Color>(n);
            for (int i = 0; i < n; i++) {
                clut.add(Color.getHSBColor((float) i / n, 1, 1));
            }
        }

        @Override
        public Paint getItemFillPaint(int row, int column) {
            return clut.get(column);
        }
    }

    private XYDataset createDataset() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries series = new XYSeries("Series 1");
        for (double x = 0; x < N - 1; x++) {
            series.add(x, f(x));
        }
        series.add(25, 1.75); // outlier
        result.addSeries(series);
        return result;
    }

    private double f(double x) {
        double y = 0.004 * x + .75;
        return y + random.nextGaussian() * y / 10;
    }

    public void display() {
        JFrame f = new JFrame(title);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createPanel());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void dataTake() throws IOException{
//        super(applicationTitle);
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
//        System.out.println(Arrays.toString(switchNames));

        cnt = 0;

        int dsize = switchNames.length;

        while (datastore.size() < dsize) {
            datastore.add(new ArrayList<>());
        }

//        System.out.println(datastore.size());
        while ((line2 = read.readLine()) != null) {
            String[] dataIn2 = line2.split(",");

            if (cnt != 0) {
                for (int i = 1; i <= dsize; i++) {
                    datastore.get(i - 1).add(dataIn2[i]);
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
        System.out.println(switchNames.length);
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ResponseTime().display();
            }
        });
    }
}
