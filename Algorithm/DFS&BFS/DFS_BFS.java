import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class DFS_BFS {
    public static boolean [] visited = {false, false, false ,false ,false ,false ,false ,false, false};
    public static int[][] graph = {{},
            {2, 3, 8},
            {1, 7},
            {1, 4, 5},
            {3, 5},
            {3, 4},
            {7},
            {2, 6, 8},
            {1, 7}};

    public static void main(String[] args) {
        int start = 1; // 시작 노드
        bfs(start);
    }

    // 재귀함수를 이
    public static void dfs(int v){
        // 현재 노드 방문 처리
        visited[v] = true;
        // 방문 노드 출력
        System.out.print(v + " ");

        // 인접 노드 탐색
        for (int i : graph[v]){
            // 방문하지 않은 인접 노드 중 가장 작은 노드를 스택에 넣기
            System.out.println(i);
            if (visited[i]==false){
                dfs(i);
            }
        }
    }

    // 스택 이용
    public static void dfs2(int start) {
        //시작 노드를 방문 처리
        visited[start] = true;
        System.out.print(start + " ");//방문 노드 출력

        Deque<Integer> stack = new LinkedList<>();
        stack.push(start); //시작 노드를 스택에 입력

        while (!stack.isEmpty()) {
            int now = stack.peek();

            boolean hasNearNode = false; // 방문하지 않은 인접 노드가 있는지 확인
            //인접한 노드를 방문하지 않았을 경우 스택에 넣고 방문처리
            for (int i : graph[now]) { // 노드 수 만큼 반복
                if (!visited[i]) {
                    System.out.println(i);
                    hasNearNode = true;
                    stack.push(i);
                    visited[i] = true;
                    System.out.println(i + " ");//방문 노드 출력
                    break;
                }
            }
            //반문하지 않은 인접 노드가 없는 경우 해당 노드 꺼내기
            if (hasNearNode == false)
                stack.pop();
        }
    }

    static void bfs(int start){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        // 현재 노드를 방문 처리
        visited[start] = true;

        // 큐가 빌때까지 반복
        while(!queue.isEmpty()){
            // 큐에서 하나의 원소를 뽑아 출력
            int v = queue.poll();
           // System.out.println(v + " ");

            // 인접한 노드 중 아직 방문하지 않은 원소들을 큐에 삽입
            for (int i : graph[v]){
                if (visited[i] == false){
                    System.out.print(i + " ");
                    queue.add(i);
                    visited[i] = true;
                }
            }

            System.out.println("====");
        }
    }

}
