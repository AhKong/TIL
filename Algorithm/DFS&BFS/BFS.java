package DFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BFS {
    static int[][] graph; //간선 연결상태
    static boolean[] checked; //확인 여부
    static int n; //정점개수
    static int m; //간선개수
    static int[][] map;
    static int[] dx = { -1, 1, 0, 0 }; //x방향배열-상하
    static int[] dy = { 0, 0, -1, 1 }; //y방향배열-좌우
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        n = Integer.parseInt(st.nextToken()); // 세로
        m = Integer.parseInt(st.nextToken()); // 가로

        visited = new boolean[n][m];
        visited[0][0] = true;

        map = new int[n][m];

        for(int i=0; i<n; i++) {
            String str = br.readLine();
            for(int j=0; j<m; j++) {
                char ch = str.charAt(j);
                map[i][j] = Character.getNumericValue(ch);
            }
        }
        bfs(0,0);
        System.out.println(map[n-1][m-1]);
    }

    public static void bfs(int x, int y){
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x,y});

        while(!q.isEmpty()){
            int now[] = q.poll();
            int nowX = now[0];
            int nowY = now[1];

            for(int i = 0 ; i<4;i++){
                int nextX = nowX + dx[i];
                int nextY = nowY + dy[i];

                if(nextX<0 || nextY<0 || nextX>=n || nextY >=m) continue;
                if(visited[nextX][nextY] || map[nextX][nextY] == 0) continue;;

                q.add(new int[] {nextX,nextY});
                map[nextX][nextY] = map[nowX][nowY]+1;
                visited[nextX][nextY] = true;
            }
        }
    }

}

