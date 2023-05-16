package implementation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*
* 정수 N이 입력되면 00시00분00초부터 N시 59분 59초까지 모든 시ㅣ각중에서 3이 하나라도 표함되는 모든 경우의 수를 구하는 프로그램을 작성하시오
* */
public class Implementation2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(n);
        String str = "000000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        LocalTime startTime = LocalTime.parse(str, formatter);
        int count = 0;

        LocalTime lastTime = startTime.plusHours(n+1).minusSeconds(1);
        while (startTime.isBefore(lastTime)){
            startTime = startTime.plusSeconds(1);
            if(startTime.toString().contains("3")){
                count++;
            }
        }
        System.out.println(count);
    }
}


