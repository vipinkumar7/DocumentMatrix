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

        Test t = new Test();
        DCTImageHash imageHash = new DCTImageHash();
        t.saveTestImageAndGetHash("/home/vipin/in/",imageHash);
        t.checkImageHash("/home/vipin/wall.png",imageHash);
    }

    public void saveTestImageAndGetHash(String folder,ImageHash imageHash) throws Exception {

        File root = new File(folder);
        File[] files = root.listFiles();

        for (File file : files) {
            BufferedImage bufferedImage = imageHash.getResizedImage(file.getAbsolutePath());
            bufferedImage = imageHash.getGrayScale(bufferedImage);
            int m[][] = imageHash.getMatrix(bufferedImage);
            String t = imageHash.getHash(m, 64, 64, imageHash.getAVERAGE());

            imageHistory.put(t, file.getName());
            ImageIO.write(bufferedImage, "png", new File("/home/vipin/out" + File.separator + file.getName().split("//.")[0] + ".png"));
        }

    }

    public void checkImageHash(String file,ImageHash imageHash) throws Exception {
        BufferedImage bufferedImage = imageHash.getResizedImage(file);
        bufferedImage = imageHash.getGrayScale(bufferedImage);
        int m[][] = imageHash.getMatrix(bufferedImage);
        String t = imageHash.getHash(m, 64, 64, imageHash.getAVERAGE());

        Set<String> keys = imageHistory.keySet();

        int min = Integer.MAX_VALUE;
        String out = "";
        for (String key : keys) {
            int c = imageHash.getHammingDistance(key, t);
            if (c < min) {
                out = imageHistory.get(key);
                min = c;
            }
        }
        System.out.println(out);
    }

    public void bilinearInterpolationImpl(String[] args) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("/home/vipin/wall.png"));
            int x = bufferedImage.getWidth();
            int y = bufferedImage.getHeight();
            int[] pixels = null;

            BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
            pixels = bufferedImage.getRaster().getPixels(0, 0, x, y, pixels);
            pixels = BilinearInterpolation.resizeBilinear(pixels, x, y, x, y);

            /* byte[] buffer = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); */
            final int[] a = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
            System.arraycopy(pixels, 0, a, 0, pixels.length);

            /* img.getRaster().setPixels(0, 0, x, y, pixels); */
            ImageIO.write(img, "png", new File("/home/vipin/wall1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
