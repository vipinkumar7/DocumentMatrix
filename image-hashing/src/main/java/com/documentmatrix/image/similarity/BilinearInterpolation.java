package com.documentmatrix.image.similarity;



/**
 *
 * This class is dedicated for implementation of Bilinear  Interpolation
 * Created by Vipin Kumar on 21/9/15.
 */
public class BilinearInterpolation {

    /**
     * Bilinear interpolation
     * @param pixels
     * @param w  original width
     * @param h  original height
     * @param w2  scaled width
     * @param h2  scaled height
     * @return
     */
    public static int[] resizeBilinear(int[] pixels, int w, int h, int w2, int h2) {
        int[] temp = new int[w2 * h2];
        int a, b, c, d, x, y, index;
        float x_ratio = ((float) (w - 1)) / w2;
        float y_ratio = ((float) (h - 1)) / h2;
        float x_diff, y_diff, blue, red, green;
        int offset = 0;
        for (int i = 0; i < h2; i++) {
            for (int j = 0; j < w2; j++) {
                x = (int) (x_ratio * j);
                y = (int) (y_ratio * i);
                x_diff = (x_ratio * j) - x;
                y_diff = (y_ratio * i) - y;
                index = (y * w + x);

                a = pixels[index];
                b = pixels[index + 1];
                c = pixels[index + w];
                d = pixels[index + w + 1];

                // blue element
                // Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
                blue = (a & 0xff) * (1 - x_diff) * (1 - y_diff) + (b & 0xff) * (x_diff) * (1 - y_diff) +
                        (c & 0xff) * (y_diff) * (1 - x_diff) + (d & 0xff) * (x_diff * y_diff);

                // green element
                // Yg = Ag(1-w)(1-h) + Bg(w)(1-h) + Cg(h)(1-w) + Dg(wh)
                green = ((a >> 8) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 8) & 0xff) * (x_diff) * (1 - y_diff) +
                        ((c >> 8) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 8) & 0xff) * (x_diff * y_diff);

                // red element
                // Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
                red = ((a >> 16) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 16) & 0xff) * (x_diff) * (1 - y_diff) +
                        ((c >> 16) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 16) & 0xff) * (x_diff * y_diff);

                temp[offset++] =
                        0xff000000 | // hardcode alpha
                                ((((int) red) << 16) & 0xff0000) |
                                ((((int) green) << 8) & 0xff00) |
                                ((int) blue);
            }
        }
        return temp;
    }


    public int[] getResizePixels(int[] pixels, int width, int height) {

        int r[][] = new int[pixels.length][pixels.length];
        double xscale = (width + 0.0) / width;
        double yscale = (height + 0.0) / height;
        double threshold = 0.5 / (xscale * yscale);
        double yend = 0.0;
        for (int f = 0; f < height; f++) // y on output
        {
            double ystart = yend;
            yend = (f + 1) / yscale;
            if (yend >= height) yend = height - 0.000001;
            double xend = 0.0;
            for (int g = 0; g < width; g++) // x on output
            {
                double xstart = xend;
                xend = (g + 1) / xscale;
                if (xend >= width) xend = width - 0.000001;
                double sum = 0.0;
                for (int y = (int) ystart; y <= (int) yend; ++y) {
                    double yportion = 1.0;
                    if (y == (int) ystart) yportion -= ystart - y;
                    if (y == (int) yend) yportion -= y + 1 - yend;
                    for (int x = (int) xstart; x <= (int) xend; ++x) {
                        double xportion = 1.0;
                        if (x == (int) xstart) xportion -= xstart - x;
                        if (x == (int) xend) xportion -= x + 1 - xend;
                        sum += r[y][x] * yportion * xportion;
                    }
                }
                r[f][g] = (sum > threshold) ? 1 : 0;
            }


        }
        return r[0];
    }

    public static int[] resizePixels(int[] pixels,int w1,int h1,int w2,int h2) {
        int[] temp = new int[w2*h2] ;
        // EDIT: added +1 to account for an early rounding problem
        int x_ratio = (int)((w1<<16)/w2) +1;
        int y_ratio = (int)((h1<<16)/h2) +1;
        //int x_ratio = (int)((w1<<16)/w2) ;
        //int y_ratio = (int)((h1<<16)/h2) ;
        int x2, y2 ;
        for (int i=0;i<h2;i++) {
            for (int j=0;j<w2;j++) {
                x2 = ((j*x_ratio)>>16) ;
                y2 = ((i*y_ratio)>>16) ;
                temp[(i*w2)+j] = pixels[(y2*w1)+x2] ;
            }
        }
        return temp ;
    }
}
