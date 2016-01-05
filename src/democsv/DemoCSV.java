package democsv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class DemoCSV extends ApplicationFrame {

    public static List<Object> sections = new ArrayList<>();
    public static String[] switchNames = null;
    public static ArrayList<String> time = new ArrayList<>();
    public static ArrayList<ArrayList<String>> datastore = new ArrayList<>();

    public DemoCSV(String applicationTitle, String chartTitle) throws IOException {
        super(applicationTitle);
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
        ////////////////////
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Category",
                "Score",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1366, 768));
        final XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesPaint(0, Color.RED);
//        renderer.setSeriesPaint(1, Color.GREEN);
//        renderer.setSeriesPaint(2, Color.YELLOW);
//        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
//        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
//        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);

    }

    private XYDataset createDataset() {

        ArrayList<Object> xystore = new ArrayList<>();
        
        for (String sn : switchNames) {
            xystore.add(new XYSeries(sn));
        }
        
        for (int i = 1; i <= switchNames.length; i++) {
            for (String x : time) {
                double y = Double.parseDouble(x);
                ((XYSeries)xystore.get(i-1)).add(y, i);
            }
        }
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        for (Object xy : xystore) {
            dataset.addSeries((XYSeries)xy);
        }
       
        System.out.println(dataset);
        return dataset;

    }

    public static String[] sNames(String[] names) {
        String[] arr = Arrays.copyOfRange(names, 1, names.length);
        return arr;

    }

    public static void DataClear() throws IOException, NullPointerException {

    }

    public static void main(String[] args) throws IOException {

        DemoCSV test = new DemoCSV("Test", "second");

        test.pack();
        RefineryUtilities.centerFrameOnScreen(test);
        test.setVisible(true);
    }
}
