package com.example;

class Perceptron {
    static final int[][][] andData = {
            {{0, 0}, {0}},
            {{0, 1}, {0}},
            {{1, 0}, {0}},
            {{1, 1}, {1}}
    };
    private static final double LEARNING_RATE = 0.05;
    static final double[] INITIAL_WEIGHTS = {Math.random(), Math.random()};

    double calculateWeightedSum(int[] data, double[] weights) {
        double weightedSUm = 0;
        for (int x = 0; x < data.length; x++) {
            weightedSUm += data[x] * weights[x];
        }
        return weightedSUm;
    }

    int applyActivationFunction(double weightedSum) {
        int result = 0;
        if (weightedSum > 1) {
            result = 1;
        }
        return result;
    }

    double[] adjustWeights(int[] data, double[] weights, double error) {
        double[] adjustedWeights = new double[weights.length];
        for (int x = 0; x < weights.length; x++) {
            adjustedWeights[x] = LEARNING_RATE * error * data[x] + weights[x];
        }
        return adjustedWeights;
    }
}