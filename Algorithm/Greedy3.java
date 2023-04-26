package greedy;

public class Greedy3 {
    public static void main(String[] args) {
        int n = 25;
        int k = 3;
        int result = 0;

        while(n!=1){
            n = n%k==0 ? n/k : n-1;
            result ++;
        }

        System.out.println(result);

    }
}
