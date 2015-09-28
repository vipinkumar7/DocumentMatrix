package com.documentmatrix.image.similartiy;

import com.documentmatrix.image.similarity.DiscreteCosineTransForm;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by root on 28/9/15.
 */
public class DCTTest {


    @Test
    public void testDct() {

        double[][] f = new double[2][2];
        f[0][0] = 54.0;
        f[0][1] = 35.0;
        f[1][0] = 128.0;
        f[1][1] = 185.0;
        double d[][] = DiscreteCosineTransForm.applyDCT(f, 2);

        d = DiscreteCosineTransForm.applyDCT(d, 2);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Assert.assertTrue(f[i][j] == d[i][j]);
            }
        }
    }
}
