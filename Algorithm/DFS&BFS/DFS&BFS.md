## DFS / BFS 

### 1. 꼭 필요한 자료구조 기초 

- 탐색 (Search) : 많은 양의 데이터 중에서 원하는 데이터를 찾는 과정
- 자료구조(Data Structure) : 데이터를 표현하고 관리하고 처리하기 위한 구조
- 오버플로(Overflow) : 특정한 자로구조가 수용할 수 있는 데이터의 크기를 이미 가득 찬 상태에서 삽입 연산을 수행할 때 발생, 즉 저장 공간을 벗어나 데이터가 넘쳐흐를 때 발생
- 언더플로(Underflow) : 자료구조에 데이터가 전혀 들어 있지 않은 상태에서 삭제 연산을 수행하는 경우, 즉 데이터가 전혀 없는 상태애서 데이터를 제거 하려고 하는 경우

#### 재귀함수 (Recursice Function)
- DFS / BFS를 구현하려면 재귀 함수를 이해하고 있어야 함 (stack,queue 도 알고 있어야 함)
- 자기 자신을 다시 호출하는 함수
- 재귀 함수를 문제 풀이에 사용할 때는 재귀 함수가 언제 끝날지, 종료 조건을 꼭 명시해야함.
- 컴퓨터 내부에서 재귀 함수의수행은 스택 자료구조를 이용함
    -  함수를 계속 호출했을 때 가장 마지막에 호출한 함수가 먼저 수행을 끝내야 그 앞의 함수 호출이 종료되기 때문
    -  컴퓨터의 구조 측면에서 보자면 연속해서 호출되는 함수는 메인 메모리의 스택 공간에 적재되므로 재귀 함수는 스택 자료구조와 같다는 말은 틀린 말이 아님


#### 그래프(Graph)
- 노드(Node) 와 간선(Edge)으로 표현되며 이때 노드를 정점(Vertex)이라고도 말한다.
- 그래프 탐색이란 하나의 노드를 시작으로 다수의 노드를 방문하는 것을 의미함
- 두 노드가 간선으로 연결되어 있다면  `두 노드는 인접하다` 라고 표현함.
- 프로그래밍에서 그래프는 크게 2가지 방식으로 표현가능
    - 인접 행렬(Adjacency Matrix) :  2차원 배열로 그래프의 연결 관계를 표현하는 방식
    - 인접 리스트(Adjacency List) : 리스트로 그래프의 연결 관계를 표현하는 방식, 자바에서는 링크드 리스트 라이브러리 제공!


 <br>

 
## 2.DFS  (Depth-First Search)
- 깊이 우선 탐색, 그래프에서 깊은 부분을 우선적으로 탐색하는 알고리즘
- `스택 자료구조`를 이용
    1. 탐색 시작 노드에 스택을 삽입하고, 방문 처리한다.
    2. 스택의 최상단 노드에 방문하지 않은 인접 노드가 있으면 그 인접 노드를 스택에 넣고 방문 처리하고, 방문하지 않은 인접 노드가 없으면 스택에서 최상단 노드를 꺼낸다.
    3. 위의 1번과 2번 과정을 더이상 수행할 수 없을 때까지 반복한다

> 방문처리 ? 스택에 한 번 삽입되어 처리된 노드가 다시 삽입되지 않게 체크하는 것을 의미하며 이를 통해 각 노드를 한 번씩만 처리 할 수 있음 
- 스택 자료구조에 기초하므로, 실제 구현은 재귀 함수를 이용했을 때 간결하게 구현할 수 있음
- 소요시간  : 데이터의 개수가 N개인 경우, O(N)

#### dfs 구현 코드 샘플
```java
package DFS;

import java.util.Deque;
import java.util.LinkedList;

public class DFS1 {
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
        dfs2(start);
    }

    // 재귀함수를 이용
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
            for (int i : graph[now]) {
                if (!visited[i]) {
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

}

```


## 3.BFS (Breadth-First Search) 
- 너비 우선 탐색 알고리즘을 의미
- 즉, 가까운 노드부터 탐색하는 알고리즘
- 최대한 멀리 있는 노드를 우선으로 탐색하는 DFS와는 반대
- 선입선출 방식인 큐 자료구조를 이용하는 것이 정석
    - 인접한 노드를 반복적으로 큐에 넣도록 알고리즘을 작성하면 자연스럽게 먼저 들어온 것이 먼저 나가며, 가까운 노드부터 탐색하게 된다.
    1. 탐색 시작 노드를 큐에 삽입하고 방문 처리한다.
    2. 큐에서 노드를 꺼내 해당 노드의 인접 노드 중에서 방문하지 않은 노드를 모두 큐에 삽입하고 방문 처리한다.
    3. 위의 1번과 2번 과정을 더 이상 수행할 수 없을 때까지 반복한다.
- 구현할 때는 언어에서 제공하는 큐 라이브러리를 사용하자.
- 탐색 수행 시간은 O(N)의 시간이 소요되고, 일반적인 경우 실제 수행 시간은 DFS보다 좋음
    - 재귀 함수로 DFS를 구현하면 컴퓨터 시스템의 동작 특성상 실제 프로그램의수행 시간이 느려질 수 있기 때문.
 
```java
package DFS;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class BFS1 {
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

```   
  
