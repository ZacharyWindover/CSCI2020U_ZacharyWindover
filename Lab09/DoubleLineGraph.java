package Lab09;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DoubleLineGraph {

    private static float[] dataset1;
    private static float[] dataset2;

    private GraphicsContext gc;

    private static int WIDTH;
    private static int HEIGHT;

    private static float maxValue = 0.0000f;

    private static double intervals;
    private static double horizontalSpacer;
    private static double verticalMultiplier;


    DoubleLineGraph() { }

    public void setGraphicsContext(GraphicsContext gc) { this.gc = gc; }
    public void setDisplaySize(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
    public void setDatasets(float[] dataset1, float[] dataset2) {
        this.dataset1 = dataset1;
        this.dataset2 = dataset2;
    }
    public void setVerticalMultiplier() { verticalMultiplier = (HEIGHT - 200) / maxValue; }
    public void setIntervals() {
        intervals = dataset1.length;
        horizontalSpacer = (WIDTH - 200) / (intervals + 1);
    }

    public void findMaxValue() {
        for (int x = 0; x < dataset1.length; x++) {
            System.out.println("dataset1[x]: " + dataset1[x]);
            if (dataset1[x] > maxValue) {
                maxValue = dataset1[x];
            }
        }

        for (int x = 0; x < dataset2.length; x++) {
            System.out.println("dataset2[x]: " + dataset2[x]);
            if (dataset2[x] > maxValue) {
                maxValue = dataset2[x];
            }
        }
    }

    public void drawLinePlot(float[] dataset, Color color) {

        System.out.println("verticalMultiplier: " + verticalMultiplier);

        double XVALUE = 100;

        gc.setStroke(color);

        for (int x = 0; x < dataset.length - 1; x++) {
            gc.strokeLine(XVALUE, ((HEIGHT - 100) - (dataset[x] * verticalMultiplier)), XVALUE + horizontalSpacer, (HEIGHT - 100) - (dataset[x+1] * verticalMultiplier));
            XVALUE = XVALUE + horizontalSpacer;
        }

    }

    public void plotLine() {
        gc.strokeLine(100, 100, 100, HEIGHT - 100);
        gc.strokeLine(100, HEIGHT - 100, WIDTH - 100, HEIGHT - 100);

        drawLinePlot(dataset1, Color.BLUE);
        drawLinePlot(dataset2, Color.RED);

    }




}
