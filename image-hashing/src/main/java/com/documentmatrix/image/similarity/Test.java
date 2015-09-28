package com.documentmatrix.image.similarity;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vipin Kumar on 21/9/15.
 */
public class Test {

    private static Map<String, String> imageHistory = new HashMap<>();

    public static void main(String[] args) throws Exception {

        saveTestImageAndGetHash("/home/vipin/in/");
        checkImageHash("/home/vipin/wall.png");
    }

    public static void saveTestImageAndGetHash(String folder) throws Exception {

        File root = new File(folder);
        File[] files = root.listFiles();

        for (File file : files) {
            BufferedImage bufferedImage = ImageHash.getResizedImage(file.getAbsolutePath());
            bufferedImage = ImageHash.getGrayScale(bufferedImage);
            int m[][] = ImageHash.getMatrix(bufferedImage);
            String t = ImageHash.getHash(m, 64, 64, ImageHash.AVERAGE);
            imageHistory.put(t, file.getName());
            ImageIO.write(bufferedImage, "png", new File("/home/vipin/out" + File.separator + file.getName().split("//.")[0] + ".png"));
        }

    }

    public static void checkImageHash(String file) throws Exception {
        BufferedImage bufferedImage = ImageHash.getResizedImage(file);
        bufferedImage = ImageHash.getGrayScale(bufferedImage);
        int m[][] = ImageHash.getMatrix(bufferedImage);
        String t = ImageHash.getHash(m, 64, 64, ImageHash.AVERAGE);

        Set<String> keys = imageHistory.keySet();

        int min = Integer.MAX_VALUE;
        String out = "";
        for (String key : keys) {
            int c = ImageHash.getHammingDistance(key, t);
            if (c < min) {
                out = imageHistory.get(key);
                min=c;
            }
        }
        System.out.println(out);
    }

    public static void firs(String[] args) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("/home/vipin/wall.png"));
            int x = bufferedImage.getWidth();
            int y = bufferedImage.getHeight();
            int[] pixels = null;

            BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
            pixels = bufferedImage.getRaster().getPixels(0, 0, x, y, pixels);
            pixels = Resize.resizeBilinear(pixels, x, y, x, y);

            // byte[] buffer = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
            final int[] a = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
            System.arraycopy(pixels, 0, a, 0, pixels.length);

            //img.getRaster().setPixels(0, 0, x, y, pixels);
            ImageIO.write(img, "png", new File("/home/vipin/wall1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getData() throws Exception {
        BufferedImage bufferedImage = ImageIO.read(new File("/home/vipin/wall.png"));
        int x = bufferedImage.getWidth();
        int y = bufferedImage.getHeight();
        BufferedImage img = new BufferedImage(x, y, bufferedImage.getType());
        byte[] buffer1 = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        // final int[] a1 = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        WritableRaster writableRaster = bufferedImage.getRaster();
        DataBufferByte dataBufferByte = (DataBufferByte) writableRaster.getDataBuffer();

        byte[] buffer = dataBufferByte.getData();

        for (int i = 0; i < buffer.length; i++) {
            int a = (buffer[i]) >> 24;
            int r = (buffer[i]) >> 16;
            int g = (buffer[i]) >> 8;
            int b = (buffer[i]);
            int val = (a + r + g + b) / 4;
            buffer1[i] = (byte) val;
        }
        ImageIO.write(img, "png", new File("/home/vipin/wall1.png"));
    }
}
