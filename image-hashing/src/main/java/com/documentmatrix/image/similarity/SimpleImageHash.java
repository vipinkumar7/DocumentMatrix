package com.documentmatrix.image.similarity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;

/**
 *
 * Simple Implementation of creating Image hash to find similar images
 * Created by Vipin Kumar on 28/9/15.
 */
public class SimpleImageHash extends  ImageHash{

    public String getHashForDCT(double[][] matrix){
        return null;
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
