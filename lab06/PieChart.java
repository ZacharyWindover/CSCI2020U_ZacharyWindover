package Lab06;

import javafx.scene.canvas.GraphicsContext;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class PieChart {

    public static GraphicsContext gc;

    PieChart (GraphicsContext gc) {
        this.gc = gc;
    }

    public static int getTotal(int[] dataset) {

        int total = 0;

        for (int value : dataset) {
            total = total + value;
        }

        return total;

    }


    public static double getX(int w) { return w + w/4; }
    public static double getY(int h) { return h/3; }

    public static double getWidth(int w) { return ((w - 100) / 1.75); }
    public static double getHeight(int w) { return ((w - 100) / 1.75); }

    public static float[] getDegrees(int[] dataset, int total) {
        float[] degrees = new float[dataset.length];

        for (int x = 0; x < dataset.length; x++) {
            degrees[x] = (float)dataset[x] / total * 360;
            System.out.println("Dataset[x]: " + dataset[x]);
            System.out.println("total: " + total);
            System.out.println("Degrees: " + degrees[x]);
        }

        return degrees;

    }

    public static float[] getStartAngles(float[] degrees) {

        float[] startAngles = new float[degrees.length];

        startAngles[0] = 0.000f;
        //double endAngle = degrees[0];

        for (int x = 1; x < degrees.length; x++) {
            startAngles[x] = startAngles[x-1] + degrees[x-1];
            System.out.println("Start Angle: " + startAngles[x]);
        }

        return startAngles;

    }

    public static float[] getArcExtents(float[] degrees, float[] startAngles) {

        float[] arcExtents = new float[degrees.length];

        for (int x = 0; x < degrees.length; x++) {
            float endAngle = startAngles[x] + degrees[x];
            arcExtents[x] = endAngle - startAngles[x];
            System.out.println("Arc Extents: "  + arcExtents[x]);
        }

        return arcExtents;

    }
    
    public void drawNewPieChart(int w, int h, int[] dataset, Color[] colours){
        int total = getTotal(dataset);

        double x = getX(w);
        double y = getY(h);

        double width = getWidth(w);
        double height = getHeight(w);

        float[] degrees = getDegrees(dataset, total);
        float[] startAngles = getStartAngles(degrees);
        float[] arcExtents = getArcExtents(degrees, startAngles);

        for (int a = 0; a < dataset.length; a++){
            gc.setFill(colours[a]);
            gc.fillArc(x, y, width, height, startAngles[a], arcExtents[a], ArcType.ROUND);

        }
    }

}
