import dsa.Inversions;
import stdlib.StdArrayIO;
import stdlib.StdOut;

import java.util.Arrays;

public class Sample {
    public static void main(String[] args){
//        int a[][] = StdArrayIO.readInt2D();
//        int h = 0, m = 0;
//        int k;
//        int n = a[0].length;
//        for (int i = 0; i < a.length; i++){
//            for (int j = 0; j < a[0].length; j++){
//                if (a[i][j] == 0)
//                    continue;
//                else{
//                    k = a[i][j] - 1;
//                    h += (Math.abs((k / n) - i) + Math.abs((k % n) - j));
//                    int l = n * i + (j + 1);
//                    if (a[i][j] != l)
//                        m += 1;
//                }
//
//            }
//
        int[][] c = StdArrayIO.readInt2D();
        int[] d = isSolvable(c);
        StdOut.println(Arrays.toString(d));
        long inv = Inversions.count(d);
        int p = c[0].length, k = 0;
        for (int i = 0; i < c.length; i++){
            for (int j = 0; j < p; j++){
                if (c[i][j] == 0)
                    k = p * i + j;
            }
        }
        int i = (k) / p;
        long sum = i + inv;
        StdOut.println(i + " " + sum);
        StdOut.println(c.length);
    }
    public static int[] isSolvable(int[][] b) {
        int n = b.length;
        int[] a = new int[n * n - 1];
        int k, p = b[0].length, v = 0;
        //long inv;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                k = p * i + j;
                if (b[i][j] == 0) {
                    v = 1;
                } else if (v > 0)
                    a[k - 1] = b[i][j];
                else
                    a[k] = b[i][j];
            }

        }
        return a;
    }
}
