package DFS;

import java.io.*;
import java.util.*;

public class DFS_BFS2 {
    //함수에서 사용할 변수들
    static int[][] graph; //간선 연결상태
    static boolean[] checked; //확인 여부
    static int n; //정점개수
    static int m; //간선개수
    static int start; //시작정점

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        start = sc.nextInt();

        graph = new int[n+1][n+1]; //좌표를 그대로 받아들이기 위해 +1해서 선언
        checked = new boolean[n+1]; //초기값 False

        //간선 연결상태 저장
        for(int i = 1; i <= m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            graph[x][y] = y;
            graph[y][x] = x;
        }

        dfs(start); //dfs호출

        checked = new boolean[1001]; //확인상태 초기화
        System.out.println(); //줄바꿈

        bfs(start); //bfs호출
    }


    //시작점을 변수로 받아 확인, 출력 후 다음 연결점을 찾아 시작점을 변경하여 재호출
    public static void dfs(int start) {
        checked[start] = true;
        System.out.print(start+" ");

        for(int i : graph[start]){
            if (checked[i]==false && i !=0){
                dfs(i);
            }
        }

    }


    static void bfs(int start){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        // 현재 노드를 방문 처리
        checked[start] = true;
        System.out.print(start+" ");

        // 큐가 빌때까지 반복
        while(!queue.isEmpty()){
            // 큐에서 하나의 원소를 뽑아 출력
            int v = queue.poll();

            // 인접한 노드 중 아직 방문하지 않은 원소들을 큐에 삽입
            for (int i : graph[v]){
                if (checked[i] == false && i !=0){
                    System.out.print(i + " ");
                    queue.add(i);
                    checked[i] = true;
                }
            }
        }
    }
}
