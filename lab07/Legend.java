package Lab07;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Legend {

    public static GraphicsContext gc;

    Legend(GraphicsContext gc) { this.gc = gc; }

    public void createNewLegend(int w, int h, Color[] chartColours, String[] labels) {
        double x1 = 100;
        double y1 = 100;

        double width = 75;
        double height = 37.5;

        for (int x = 0; x < chartColours.length; x++) {
            gc.setFill(chartColours[x]);
            gc.fillRect(x1, y1, width, height);

            gc.setFill(Color.BLACK);
            gc.strokeRect(x1, y1, width, height);

            gc.fillText(labels[x], x1 + width + 25, y1 + (height / 2));

            y1 = y1 + (2 * height);

        }

    }


}
