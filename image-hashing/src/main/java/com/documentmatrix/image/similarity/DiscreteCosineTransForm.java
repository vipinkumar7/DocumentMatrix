package com.documentmatrix.image.similarity;

/**
 * Created by Vipin  Kumar on 28/9/15.
 */
public class DiscreteCosineTransForm {


    /**
     * DCT separates he image into collection of frequencies and scalars
     * convert to @size x @size DCT
     * @param matrix
     * @param size
     * @return
     */
    public static double[][] applyDCT(double[][] matrix, int size) {

        double[] A = new double[size];

        for (int i = 1; i < size; i++) {
            A[i] = 1;
        }
        A[0] = 1 / Math.sqrt(2.0);
        double[][] value = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double sum = 0;
                for (int u = 0; u < size; u++) {
                    for (int v = 0; v < size; v++) {
                        sum += (2*( A[u] * A[v]) / Math.sqrt(size*size) )*
                                Math.cos(((2 * i + 1) / (2.0 * size)) * u * Math.PI) *
                                Math.cos(((2 * j + 1) / (2.0 * size)) * v * Math.PI) *
                                matrix[u][v];
                    }
                }
                value[i][j] = Math.round(sum);
            }

        }

        return value;

    }
}
