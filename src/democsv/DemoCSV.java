package democsv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class DemoCSV {

    public static List<Object> sections = new ArrayList<>();
    public static String[] switchNames = null;
    public static ArrayList<String> time = new ArrayList<>();
    public static ArrayList<ArrayList<String>> datastore = new ArrayList<>();

    public static String[] sNames(String[] names) {
        String[] arr = Arrays.copyOfRange(names, 1, names.length);
        return arr;

    }

    public static void DataClear() throws IOException, NullPointerException {

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

            if (cnt != 0) {
                for (int i = 1; i <= dsize; i++) {
                    datastore.get(i - 1).add(dataIn2[i]);
                }
            }
            cnt++;
        }

        for (int i = 0; i < switchNames.length; i++) {
            sections.add(new XYSeries(switchNames[i]));
        }
    }
    
    private XYDataset createDataset( ){
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        
        return dataset;
        
    }

    public static void main(String[] args) throws IOException {
        DataClear();
        
    }
}
