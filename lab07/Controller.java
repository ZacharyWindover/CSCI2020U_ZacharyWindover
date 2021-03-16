package Lab07;

import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Controller {

    public Controller() {

    }

    @FXML private Canvas mainCanvas;
    @FXML private GraphicsContext gc;

    @FXML
    public void initialize() throws IOException {

        gc = mainCanvas.getGraphicsContext2D();

        FileLoader fl = new FileLoader("weatherwarnings-2015.CSV");
        fl.extractData();
        fl.outputNaturalDisasters(new File("NaturalDisasters.txt"));

        Map<String, Integer> WeatherWarnings = new TreeMap<>();
        WeatherWarnings = fl.getMap();

        int[] dataset = new int[WeatherWarnings.size()];
        String[] dataLabels = new String[WeatherWarnings.size()];

        int keyCounter = 0;

        for (Map.Entry<String, Integer> entry : WeatherWarnings.entrySet()) {
            dataLabels[keyCounter] = entry.getKey();
            dataset[keyCounter] = entry.getValue();

            keyCounter++;

        }

        int colourCounter = 0;

        Color[] pieColours = new Color[WeatherWarnings.size()];

        pieColours[0] = Color.DEEPSKYBLUE;
        pieColours[1] = Color.YELLOW;
        pieColours[2] = Color.ORANGE;
        pieColours[3] = Color.PINK;

        double scale = 0.75;

        PieChart weatherWarningPieChart = new PieChart(gc);
        weatherWarningPieChart.drawNewPieChart(640, 720, dataset, dataLabels, pieColours, scale);
        Legend pieChartLegend = new Legend(gc);
        pieChartLegend.createNewLegend(640, 720, pieColours, dataLabels);


    }



}
