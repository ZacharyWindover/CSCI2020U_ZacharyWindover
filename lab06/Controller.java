package Lab06;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Controller {

    @FXML
    private Canvas mainCanvas;

    @FXML
    private GraphicsContext gc;

    private static double[] avgHousingPricesByYear = {
            247381.0, 264171.4, 287715.3, 294736.1,
            308431.4, 322635.9, 340253.0, 363153.7
    };

    private static double[] avgCommercialPricesByYear = {
            1121585.3, 1219479.5, 1246354.2, 1295364.8,
            1335932.6, 1472362.0, 1583521.9, 1613246.3
    };


    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };

    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();

        DoubleBarGraph doubleBarGraph = new DoubleBarGraph(gc);
        doubleBarGraph.drawDoubleBarGraph(640, 720, avgHousingPricesByYear, avgCommercialPricesByYear, Color.RED, Color.BLUE);

        PieChart pieChart = new PieChart(gc);
        pieChart.drawNewPieChart(640, 720, ageGroups, purchasesByAgeGroup, pieColours);

    }

}