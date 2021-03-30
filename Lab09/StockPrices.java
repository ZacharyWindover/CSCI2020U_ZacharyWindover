package Lab09;

import sample.Dataset;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class StockPrices {

    private static String URLType1 = "https://query1.finance.yahoo.com/v7/finance/download/";
    private static String URLType2;
    private static String URLType3 = "?period1=1262322000&period2=1616817600&interval=1mo&events=history&includeAdjustedClose=true";

    private static String stockSymbol;
    private static String[][] dataset;
    private static String[] closingValues;
    private static float[] closingValuesFloats;
    private static int numIntervals;

    StockPrices (String stockSymbol) {
        this.stockSymbol = stockSymbol;
        this.URLType2 = stockSymbol;
    }

    public static String[][] getDataset() { return dataset; }
    public static String getStockSymbol() { return stockSymbol; }
    public static String[] getClosingValues() { return closingValues; }
    public static float[] getClosingValuesFloats() { return closingValuesFloats; }

    public static void downloadStockPrices() {
        String url = URLType1 + URLType2 + URLType3;

        System.out.println("URL: " + url);

        try {

            URL csvURL = new URL(url);
            URLConnection urlConnection = csvURL.openConnection();

            Scanner input = new Scanner(urlConnection.getInputStream());

            int numLines = 136;

            numIntervals = numLines;

            dataset = new String[numLines - 1][7];

            closingValues = new String[numLines - 1];
            closingValuesFloats = new float[numLines - 1];

            String line;

            int index = 0;

            line = input.nextLine();

            while(input.hasNextLine()) {

                line = input.nextLine();

                String[] values = line.split(",");

                System.out.println("Line: " + line);

                dataset[index][0] = values[0];
                dataset[index][1] = values[1];
                dataset[index][2] = values[2];
                dataset[index][3] = values[3];
                dataset[index][4] = values[4];
                dataset[index][5] = values[5];
                dataset[index][6] = values[6];

                closingValues[index] = dataset[index][3];
                closingValuesFloats[index] = Float.parseFloat(dataset[index][3]);

                System.out.println("close: " + dataset[index][3]);

                index++;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
