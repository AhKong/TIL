package greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * 한 개의 회의실이 있는데 이를 사용하고자 하는 N개의 회의에 대하여 회의실 사용표를 만들려고 한다.
 * 각 회의 I에 대해 시작시간과 끝나는 시간이 주어져 있고, 각 회의가 겹치지 않게 하면서 회의실을 사용할 수 있는 회의의 최대 개수를 찾아보자.
 * 단, 회의는 한번 시작하면 중간에 중단될 수 없으며 한 회의가 끝나는 것과 동시에 다음 회의가 시작될 수 있다.
 * 회의의 시작시간과 끝나는 시간이 같을 수도 있다. 이 경우에는 시작하자마자 끝나는 것으로 생각하면 된다.
 *
 */

public class Greedy7 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] list = new int[n][2];

        for(int i = 0 ; i < n ; i++){
            list[i][0] = sc.nextInt();
            list[i][1] = sc.nextInt();
        }

        // 회의가 끝나는 시간을 기준으로 졍렬 
        Arrays.sort(list, (o1, o2) -> {
            if(o1[1]==o2[1]){
                return o1[0]-o2[0];
            }
            return o1[1]-o2[1];
        });

        int result = 0;
        int end = 0;

        for(int i = 0 ; i < n ; i++){
            if(end<=list[i][0]){
                end = list[i][1];
                result++;
            }
        }

        System.out.println(result);
    }

}
