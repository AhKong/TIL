package implementation;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Implementation1 {

    public static void main(String[] args) {
        // 상하좌우
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int x = 1;
        int y = 1;


        List<String> map = new ArrayList<>();
        map.add("R");
        map.add("R");
        map.add("R");
        map.add("U");
        map.add("D");
        map.add("D");

        for(String s : map){

            if(s.equals("L")){
                if(x-1 >= 1){
                    x = x -1;
                }
            }
            if(s.equals("R")){
                if(x+1 <= n){
                    x = x+1;
                }
            }
            if(s.equals("U")){
                if(y-1 >= 1){
                    System.out.println("test");
                    y = y -1;
                }
            }
            if(s.equals("D")){
                if(y+1 <= n){
                    y = y + 1;
                }
            }
        }
        System.out.println(y + "," + x);
    }
}
