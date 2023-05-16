package implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * 왕실의 나이트
 * 수평으로 두 칸 이동한 뒤에 수직으로 한 칸 이동하기
 * 수직으로 두 칸 이동한 뒤에 수평으로 한 칸 이동하기
 * */
public class Implementation3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String points = sc.next();
        int x = (char) points.split("")[0].charAt(0)-'a';
        int y = Integer.parseInt(points.split("")[1])-1;
        int[][] step = {{2,-1},{2,1},{-2,-1},{-2,1},{1,2},{-1,2},{1,-2},{-1,-2}};

        int count = 0;

        for(int i = 0 ; i<step.length;i++){
            if(x+step[i][0] >=0 && x+step[i][0] <=8  && y+step[i][1] >=0 &&y+step[i][1] <=8){
                count++;
            }
        }

        System.out.println(count);

    }


}
