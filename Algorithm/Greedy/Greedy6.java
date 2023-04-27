package greedy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
  준규가 가지고 있는 동전은 총 N종류이고, 각각의 동전을 매우 많이 가지고 있다.
  동전을 적절히 사용해서 그 가치의 합을 K로 만들려고 한다.
  이때 필요한 동전 개수의 최솟값을 구하는 프로그램을 작성하시오.
*/

public class Greedy6 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        int n =  sc.nextInt();
        int k =  sc.nextInt();

        List<Integer> list = new ArrayList<>();

        for(int i = 0  ; i <n ; i++){
            int value = sc.nextInt();
            if(value<=k){
                list.add(value);
            }
        }

        Collections.sort(list,Collections.reverseOrder());
        int result = 0;

        for(int i : list){
           while (k>=i){
               k = k-i;
               result++;
           }
        }
        System.out.println(result);
    }
}
