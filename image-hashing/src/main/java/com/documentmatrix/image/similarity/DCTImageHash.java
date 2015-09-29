
package com.documentmatrix.image.similarity;

import java.awt.image.BufferedImage;

/**
 * Created by Vipin Kumar on 29/9/15.
 */
public class DCTImageHash extends ImageHash {

    /**
     * Reduce the DCT.
     * This is the magic step. While the DCT is 32x32, just keep the
     * top-left 8x8. Those represent the lowest frequencies in the
     * picture.
     */

    /*  Compute the average value.
     * Like the Average Hash, compute the mean DCT value (using only
     * the 8x8 DCT low-frequency values and excluding the first term
     * since the DC coefficient can be significantly different from
     * the other values and will throw off the average).
     */

    /**
     * The 64 bit hash  result
     * will not vary as long as the overall structure of the image
     * remains the same; this can survive gamma and color histogram
     * adjustments without a problem.
     */

    /*
     * @param matrix
     * @return
     */
    public String getHashForDCT(double[][] matrix) {

        double dctValues[][] = DiscreteCosineTransForm.applyDCT(matrix, 64);

        double total = 0;

        for (int i = 0; i < RESIZED_SIZE; i++) {
            for (int j = 0; j < RESIZED_SIZE; j++) {
                total += dctValues[i][j];
            }
        }

        total -= dctValues[0][0];

        double avg = total / (double) (RESIZED_SIZE * RESIZED_SIZE - 1);

        String hash = "";
        for (int i = 0; i < RESIZED_SIZE; i++) {
            for (int j = 0; j < RESIZED_SIZE; j++) {
                hash += matrix[i][j] > avg ? 1 : 0;
            }
        }
        return hash;
    }

    public  int [][] getMatrix(BufferedImage image) {

        long total=0,average=0;
        int [][] values=new int[image.getWidth()][image.getHeight()];
        for (int w = 0; w < image.getWidth(); w++) {
            for (int h = 0; h < image.getHeight(); h++) {
                values[w][h]=(image.getRGB(w,h)&0xff);
                average+=values[w][h];
                total++;
            }
        }
        AVERAGE/=total;
        return values;
    }
}
