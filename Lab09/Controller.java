package Lab09;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Controller {

    @FXML private Canvas mainCanvas;
    @FXML private GraphicsContext gc;

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();

        StockPrices apple = new StockPrices("AAPL");
        apple.downloadStockPrices();
        float[] dataset1 = apple.getClosingValuesFloats();

        StockPrices google = new StockPrices("GOOGL");
        google.downloadStockPrices();
        float[] dataset2 = google.getClosingValuesFloats();

        DoubleLineGraph stonks = new DoubleLineGraph();
        stonks.setGraphicsContext(gc);
        stonks.setDisplaySize(1280, 720);
        stonks.setDatasets(dataset1, dataset2);


        stonks.findMaxValue();
        stonks.setIntervals();
        stonks.setVerticalMultiplier();

        stonks.plotLine();



    }



}
