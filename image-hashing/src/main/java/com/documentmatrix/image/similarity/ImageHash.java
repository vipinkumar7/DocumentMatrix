package com.documentmatrix.image.similarity;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

/**
 * Created by Vipin Kumar on 29/9/15.
 */
public abstract class ImageHash {

    protected   int  AVERAGE =0;

    protected int RESIZED_SIZE=64;

    /**
     * Resizing image to  half till it reached near @RESIZED_SIZE
     * @param imageFile
     * @return @BufferedImage
     * @throws IOException
     */
    public BufferedImage getResizedImage(String imageFile) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(new File(imageFile));
        int w_factor = bufferedImage.getWidth() / 2;
        int h_factor = bufferedImage.getHeight() / 2;
        BufferedImage img = null;
        while (w_factor > RESIZED_SIZE && h_factor > RESIZED_SIZE) {
            img = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT
                    , w_factor, h_factor, Scalr.OP_ANTIALIAS);
            w_factor /= 2;
            h_factor /= 2;
        }
        //convert to 64x64 image
        img = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT
                , RESIZED_SIZE, RESIZED_SIZE, Scalr.OP_ANTIALIAS);
        return img;
    }


    /**
     * Convert Image to gray scale
     * @param image
     * @return
     */
    public  BufferedImage getGrayScale(BufferedImage image) {

        for (int w = 0; w < image.getWidth(); w++) {
            for (int h = 0; h < image.getHeight(); h++) {
                int rgb = image.getRGB(w, h);
                int a = rgb >> 32 & 0xff;
                int r = rgb >> 16 & 0xff;
                int g = rgb >> 8 & 0xff;
                int b = rgb & 0xff;
                int gray = (int) (0.21 * r + 0.72 * g + 0.07 * b);
                Color newColor = new Color(gray, gray, gray);
                image.setRGB(w, h, newColor.getRGB());
            }
        }
        return image;
    }

    public  double lum(ColorModel color, int pixel) {
        int r = color.getRed(pixel);
        int g = color.getGreen(pixel);
        int b = color.getBlue(pixel);
        return .299 * r + .587 * g + .114 * b;
    }

    // return a gray version of this Color
    public  int toGray(ColorModel color, int pixel) {
        int y = (int) (Math.round(lum(color, pixel)));   // round to nearest int
        return y;
    }

    /**
     * hash for 8x8 image
     * @param image
     * @return
     */
    public  long getHash(BufferedImage image) {

        long average = 0, total = 0;
        long hash = 0L, temp = 63;
        for (int w = 0; w < image.getWidth (); w++) {
            for (int h = 0; h < image.getHeight(); h++) {
                int rgb = image.getRGB(w, h);
                average += rgb;
                total++;
            }
        }
        average /= total;
        for (int w = 0; w < image.getWidth(); w++) {
            for (int h = 0; h < image.getHeight(); h++) {
                int rgb = image.getRGB(w, h);
                rgb = (rgb > average) ? 1 : 0;
                if (temp >= 1) hash |= hash << temp;
                else
                    hash |= rgb;
            }
        }
        return hash;
    }

    /**
     * Convert image to RESIZED_SIZE*RESIZED_SIZE matrix and calculate average of all pixels
     * @param image
     * @return
     */
    public abstract  int [][] getMatrix(BufferedImage image);


    public  String getHash(int[][] matrix, int width, int height, int average) {
        String hash = "";
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                hash += matrix[i][j] > average ? 1 : 0;
            }
        }
        return hash;
    }

    public   int getHammingDistance(String one, String two) {
        if (one.length() != two.length()) {
            return -1;
        }
        int counter = 0;
        for (int i = 0; i < one.length(); i++) {
            if (one.charAt(i) != two.charAt(i)) counter++;
        }
        return counter;
    }

    public abstract String getHashForDCT(double[][] matrix);

    public int getAVERAGE() {
        return AVERAGE;
    }

    public void setAVERAGE(int AVERAGE) {
        this.AVERAGE = AVERAGE;
    }
}
