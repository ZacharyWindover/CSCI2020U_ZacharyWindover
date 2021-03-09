package Lab06;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Color;

public class DoubleBarGraph {

    private static GraphicsContext gc;

    // get the graphics context from the controller class
    DoubleBarGraph (GraphicsContext gc) {
        this.gc = gc;
    }

    // calculates the horizontal width of the bars
    public static double calculateHorizontalWidth(int w, int horizontalBuffer, int horizontalSpacer, int datasetOneLength, int datasetTwoLength) {
        double horizontalWidth = w - (2 * horizontalBuffer);
        horizontalWidth = horizontalWidth - (7 * horizontalSpacer);
        horizontalWidth = horizontalWidth / (datasetOneLength + datasetTwoLength);

        return horizontalWidth;
    }

    // calculate the multiplier of the height to the value height (for the bars to be scaled to fit the page)
    public static double calculateHeightMultiplier(double datasetOneMaxValue, double datasetTwoMaxValue, int h, int verticalBuffer) {
        double heightMultiplier = 0.00;

        if (datasetOneMaxValue > datasetTwoMaxValue) {
            heightMultiplier = datasetOneMaxValue / (h - (2 * verticalBuffer));
        }

        if (datasetTwoMaxValue > datasetOneMaxValue) {
            heightMultiplier = datasetTwoMaxValue / (h - (2 * verticalBuffer));
        }

        return heightMultiplier;
    }

    // get max value of the dataset
    public static double getMaxValue(double[] dataset) {
        double maxValue = Double.NEGATIVE_INFINITY;


        for (double value : dataset) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        return maxValue;

    }

    public static void drawDoubleBarGraph(int w, int h, double[] datasetOne, double[] datasetTwo, Color datasetColorOne, Color datasetColorTwo) {

        int verticalBuffer = 50; // distance from top / bottom of window
        int horizontalBuffer = 50; // distance from side of window
        int horizontalSpacer = 15; // space between each bar pair

        // obtain the horizontalWidth of the bars for the graph
        double horizontalWidth = calculateHorizontalWidth(w, horizontalBuffer, horizontalSpacer, datasetOne.length, datasetTwo.length);

        // calculate the horizontal increment from previous bar pair to next bar pair
        double horizontalIncrement = horizontalWidth + horizontalSpacer;

        // Getting min and max of data for dataset one
        double datasetOneMaxValue = getMaxValue(datasetOne);

        // Getting min and max of data for dataset two
        double datasetTwoMaxValue = getMaxValue(datasetTwo);

        // the multiplier used to calculate how many pixels each value is
        double heightMultiplier = calculateHeightMultiplier(datasetOneMaxValue, datasetTwoMaxValue, h, verticalBuffer);

        // Plotting the bars of data
        double x = horizontalBuffer;

        for (int loopCounter = 0; loopCounter < datasetTwo.length; loopCounter++) {

            double datasetOneValue = datasetOne[loopCounter];

            double datasetOneHeight = datasetOneValue / heightMultiplier;

            gc.setFill(datasetColorOne);
            gc.fillRect(x, (h - (verticalBuffer + datasetOneHeight)), horizontalWidth, datasetOneHeight); // x coordinate, y coordinate, width, height

            x = x + horizontalWidth;

            double datasetTwoValue = datasetTwo[loopCounter];
            double datasetTwoHeight = datasetTwoValue / heightMultiplier;

            gc.setFill(datasetColorTwo);
            gc.fillRect(x, (h - (verticalBuffer + datasetTwoHeight)), horizontalWidth, datasetTwoHeight);

            x = x + horizontalIncrement;
        }

    }
}
