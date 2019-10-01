package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.stream.IntStream;

public class Driver extends Application {

    static double m = 0;
    static double b = 0;
    static int[][][] data = Perceptron.andData;

    public static void main(String[] args) {
        double[] weights = Perceptron.INITIAL_WEIGHTS;
        Driver driver = new Driver();
        Perceptron perceptron = new Perceptron();

        int epochNumber = 0;
        boolean errorFlag = true;
        double error = 0;
        double[] adjustedWeights = null;

        while (errorFlag) {
            driver.printHeading(epochNumber++);
            errorFlag = false;
            error = 0;
            for (final int[][] datum : data) {
                double weightedSum = perceptron.calculateWeightedSum(datum[0], weights);
                int result = perceptron.applyActivationFunction(weightedSum);
                error = datum[1][0] - result;
                if (error != 0) {
                    errorFlag = true;
                }
                adjustedWeights = perceptron.adjustWeights(datum[0], weights, error);
                driver.printVector(datum, weights, result, error, weightedSum, adjustedWeights);
                weights = adjustedWeights;
            }
        }

        m = -weights[2] / weights[1];
        b = (Perceptron.THRESHOLD / weights[1]) - (weights[0]/weights[1]);
        System.out.println("\ny =" + String.format("%.2f", m) + " x = " + String.format("%.2f", b));
        launch(args);
    }

    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("NeuralNetworkTest");
        final XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
        series1.setName("zero");
        final XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
        series2.setName("one");
        IntStream.range(0, Perceptron.andData.length).forEach(i -> {
            int x = Perceptron.andData[i][0][1], y = Perceptron.andData[i][0][2];
            if (Perceptron.andData[i][1][0] == 0) series1.getData().add(new XYChart.Data<Number, Number>(x, y));
            else series2.getData().add(new XYChart.Data<Number, Number>(x, y));
        });
        double x1 = 0, y1 = b, x2 = -(b / m), y2 = 0;
        String title = new String("y = " + String.format("%.2f", m) + "x = " + String.format("%.2f", b)
                + " | (0, " + String.format("%.2f", b) + ") | (" + String.format("%.2f", -(b / m)) + ", 0)");
        NumberAxis xAxis = new NumberAxis(0, 4, 1);
        NumberAxis yAxis = new NumberAxis(0, 4, 1);
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle(title);
        scatterChart.getData().add(series1);
        scatterChart.getData().add(series2);

        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.getData().add(new XYChart.Data<>(x1, y1));
        series3.getData().add(new XYChart.Data<>(x2, y2));

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.getData().add(series3);
        lineChart.setOpacity(0.1);

        Pane pane = new Pane();
        pane.getChildren().addAll(scatterChart, lineChart);
        primaryStage.setScene(new Scene(pane, 500, 400));
        primaryStage.show();
    }

    private void printHeading(int epochNumber) {
        System.out.println("\n========================================== Epoch # " + epochNumber + " ===============================================");
        System.out.println("  w0   |  w1   |  w2   |  x0  |  x1  |  x2  | Target result | Result | error | Weighted sum | adjusted w1 | adjusted w2");
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    private void printVector(int[][] data, double[] weights, int result, double error, double weightedSum, double[] adjustedWeights) {
        String w0 = String.format("%.2f", weights[0]);
        if(weights[0] >= 0) w0 = " " + w0;
        String w1 = String.format("%.2f", weights[1]);
        if(weights[1] >= 0) w1 = " " + w1;
        String w2 = String.format("%.2f", weights[2]);
        if(weights[2] >= 0) w2 = " " + w2;
        String x0 = Integer.toString(data[0][0]);
        String x1 = Integer.toString(data[0][1]);
        String x2 = Integer.toString(data[0][2]);
        String stringError = Double.toString(error);
        if(error >= 0) stringError = " " + stringError;
        String stringWeightedSum = String.format("%.2f", weightedSum);
        if(weightedSum >= 0) stringWeightedSum = " " + stringWeightedSum;
        String adjustedw0 = String.format("%.2f", adjustedWeights[0]);
        if(adjustedWeights[0] >= 0) adjustedw0 = " " + adjustedw0;
        String adjustedw1 = String.format("%.2f", adjustedWeights[1]);
        if(adjustedWeights[1] >= 0) adjustedw1 = " " + adjustedw1;
        String adjustedw2 = String.format("%.2f", adjustedWeights[2]);
        if(adjustedWeights[2] >= 0) adjustedw2 = " " + adjustedw2;
        System.out.println(
                " " + w0 +
                        " | " + w1 +
                        " | " + w2 +
                        " |  " + x0 +
                        "  |  " + x1 +
                        "  |  " + x2 +
                        "  |       " + data[1][0] +
                        "       |   " + result +
                        "    |  " + stringError +
                        "  |     " + stringWeightedSum +
                        "     |     " + adjustedw0 +
                        "     |     " + adjustedw1 +
                        "    | " + adjustedw2
        );
    }
}
