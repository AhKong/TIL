package greedy;

import java.util.Arrays;
import java.util.Collections;

public class Greedy2 {
    public static void main(String[] args) {
        int m = 2;
        int n = 4;
        int[][] list = {{7,3,1,8},{3,3,3,4}};

        int result = 0;
        for(int i = 0; i<m ; i++){
            int min = 9999;
            for(int j : list[i]) {
                if(j<=min){
                    min = j;
                }
            }

            if(result<=min){
                result = min;
            }
        }

        System.out.println(result);
    }
}
