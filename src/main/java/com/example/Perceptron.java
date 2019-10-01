package com.example;

class Perceptron {
    public static final double THRESHOLD = 0.0;
    static final int[][][] TRAINING_DATA = {
            {{1, 1, 1, 1}, {1}},
            {{1, 2, 2, 2}, {1}},
            {{1, 1, -1, -1}, {0}},
            {{1, 2, -2, -2}, {0}}
    };
    private static final double LEARNING_RATE = 0.05;
    static final double[] INITIAL_WEIGHTS = {Math.random(), Math.random(), Math.random(), Math.random()};

    double calculateWeightedSum(int[] data, double[] weights) {
        double weightedSUm = 0;
        for (int x = 0; x < data.length; x++) {
            weightedSUm += data[x] * weights[x];
        }
        return weightedSUm;
    }

    int applyActivationFunction(double weightedSum) {
        return (weightedSum > THRESHOLD) ? 1 : 0;
    }

    double[] adjustWeights(int[] data, double[] weights, double error) {
        double[] adjustedWeights = new double[weights.length];
        for (int x = 0; x < weights.length; x++) {
            adjustedWeights[x] = LEARNING_RATE * error * data[x] + weights[x];
        }
        return adjustedWeights;
    }

}
