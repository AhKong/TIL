package greedy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// 실전문제 1. 큰 수의 법칙

public class Greedy1 {
    public static void main(String[] args) {
        int m = 8;
        int k = 3;
        int n = 5;
        int result = 0;


        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(3);
        list.add(4);
        list.add(3);

        Collections.sort(list,Collections.reverseOrder());

        int old = -99;
        int flag = 0;
        int now = 0;

        // 내 방
        for(int i = 0; i <m ; i++){

            if(old==now){
                flag++;
            }

            if(flag==k){
                now = old+1;
            }

            result = result+list.get(now);

        }
        System.out.println(result);

        // 반복되는 수열임을 이용해서 규칙을 수식화 한 방법

        int first = list.get(0);
        int second = list.get(1);

        int count = (m/(k+1))*k + m%(k+1) ;
        
        result = 0;
        result += count*first;
        result += (m-count)*second;

        System.out.println(result);
    }
}
