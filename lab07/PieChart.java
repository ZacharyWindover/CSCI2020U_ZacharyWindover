package Lab07;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class PieChart {

    public static GraphicsContext gc;

    PieChart(GraphicsContext gc) {
        this.gc = gc;
    }

    public static int getTotal(int[] dataset) {

        int total = 0;

        for (int value : dataset) {
            total = total + value;
        }

        return total;

    }


    public static double getX(int w, double scale) {
        double denominator = 4 + (scale * 4);
        double edge = (scale * 100);
        return w + w/denominator - edge;
    }

    public static double getY(int h, double scale) {
        double denominator = 3 + (scale * 4);
        double edge = (scale * 35);
        return h/denominator - edge;
    }

    public static double getWidth(int w, double scale) {
        double denominator = 1.75 - scale;
        return ((w - 100) / denominator);
    }
    public static double getHeight(int w, double scale) {
        double denominator = 1.75 - scale;
        return ((w - 100) / denominator);
    }

    public static float[] getDegrees(int[] dataset, int total) {
        float[] degrees = new float[dataset.length];

        for (int x = 0; x < dataset.length; x++) {
            degrees[x] = (float)dataset[x] / total * 360;
            //System.out.println("Dataset[x]: " + dataset[x]);
            //System.out.println("total: " + total);
            System.out.println("Degrees: " + degrees[x]);
        }

        return degrees;

    }

    public static float[] getStartAngles(float[] degrees) {

        float[] startAngles = new float[degrees.length];

        startAngles[0] = 0.000f;

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

    public void drawOutline(int w, int h, double x, double y, double width, double height, float[] degrees, float[] startAngles, float[] arcExtents, double scale){
        double radius = width / 2;
        double circumference = 2 * Math.PI * radius;

        gc.strokeOval(x, y, width, height);             // draws outline of circle

        // get x1 (middle of pie chart)
        double x1 = x + radius;
        System.out.println("X1: " + x1);

        // get y1 (middle of pie chart)
        double y1 =  y + radius;
        System.out.println("Y1: " + y1);

        System.out.println("Scale: " + scale);

        // get x2
        double x2 = 1062.5;

        // get y2
        double y2 = 130.5;

        // get x3
        double x3 = x1 + 270;

        // get y3
        double y3 = y1;

        // get x4
        double x4 = 1062.5;

        // get y4
        double y4 = y1 + (radius/2);
        gc.strokeLine(x1, y1, x2, y2);
        gc.strokeLine(x1, y1, x3, y3);
        gc.strokeLine(x1, y1, x4, y4);




    }

    public void drawNewPieChart(int w, int h, int[] dataset, Color[] colours, double scale){
        int total = getTotal(dataset);

        double x = getX(w, scale);
        double y = getY(h, scale);

        double width = getWidth(w, scale);
        double height = getHeight(w, scale);

        float[] degrees = getDegrees(dataset, total);
        float[] startAngles = getStartAngles(degrees);
        float[] arcExtents = getArcExtents(degrees, startAngles);

        for (int a = 0; a < dataset.length; a++){
            gc.setFill(colours[a]);
            gc.fillArc(x, y, width, height, startAngles[a], arcExtents[a], ArcType.ROUND);

        }
    }

    public void drawNewPieChart(int w, int h, int[] dataset, String[] dataLabels, Color[] colours, double scale){
        int total = getTotal(dataset);

        int chunks = dataset.length;

        String label = dataLabels[0];

        double x = getX(w, scale);
        double y = getY(h, scale);

        double width = getWidth(w, scale);
        double height = getHeight(w, scale);

        float[] degrees = getDegrees(dataset, total);
        float[] startAngles = getStartAngles(degrees);
        float[] arcExtents = getArcExtents(degrees, startAngles);

        for (int a = 0; a < dataset.length; a++){
            gc.setFill(colours[a]);
            gc.fillArc(x, y, width, height, startAngles[a], arcExtents[a], ArcType.ROUND);
            System.out.println("x: " + x);
            System.out.println("y: " + y);

        }

        drawOutline(w, h, x, y, width, height, degrees, startAngles, arcExtents, scale);

    }

}
