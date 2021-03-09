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

    public static int getTotal(int[] purchasesByAgeGroup) {

        int datasetLength = purchasesByAgeGroup.length;

        int total = 0;

        for (int a = 0; a < datasetLength; a++) {
            total = purchasesByAgeGroup[a] + total;
            System.out.println("total: " + total);
        }

        System.out.println("total: " + total);

        return total;

    }

    public static float[] getPercentages(int total, int[] purchasesByAgeGroup) {

        int datasetLength = purchasesByAgeGroup.length;
        float[] percentages = new float[datasetLength];

        for (int a = 0; a < datasetLength; a++) {
            percentages[a] = ((float) purchasesByAgeGroup[a] / (float) total) * 100;
            System.out.println("percentages[a]: " + percentages[a]);
            System.out.println("purchasesByAgeGroup: " + purchasesByAgeGroup[a]);
        }

        System.out.println("total: " + total);

        return percentages;

    }

    public static float[] getArcExtents(float[] percentages) {
        int datasetLength = percentages.length;
        float[] arcExtents = new float[datasetLength];

        for (int a = 0; a < datasetLength; a++) {
            arcExtents[a] = percentages[a] * 360;
        }

        return arcExtents;

    }

    public void drawNewPieChart(int w, int h, String[] ageGroups, int[] purchasesByAgeGroup, Color[] pieColours) {

        int datasetLength = purchasesByAgeGroup.length;
        int total = getTotal(purchasesByAgeGroup);
        System.out.println("total: " + total);

        float[] percentages = getPercentages(total, purchasesByAgeGroup);
        float[] arcExtents = getArcExtents(percentages);


        double x = w + (w/4);
        double y = h/4;

        double width = (w-50) / 2;
        double height = (h-50) / 2;

        double startAngle = 0.0;
        double arcExtent = 0.0;

        for (int counter = 0; counter < datasetLength; counter++) {

            arcExtent = arcExtents[counter];

            gc.setFill(pieColours[counter]);
            gc.fillArc(x, y, width, height, startAngle, arcExtent, ArcType.ROUND);

            System.out.println("x: " + x);
            System.out.println("y: " + y);
            System.out.println("width: " + width);
            System.out.println("height: " + height);
            System.out.println("startAngle: " + startAngle);
            System.out.println("arcExtent: " + arcExtent);
            System.out.println("----------------------------------------------");

            startAngle = startAngle + arcExtent;

        }

    //gc.fillArc(670, 360, 100.0, 100.0, 0.0, 90.0, ArcType.ROUND);




    }
}
