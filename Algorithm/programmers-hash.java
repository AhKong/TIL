import java.util.HashMap;

// 프로그래머스 hashmap 1번 문제 
// 이 문제의 핵심은 "getOrDefault()" 
/* getOrDefault(key,defaultValue) => 해당 map에 키값이 존재하면 그 값을 반환 하고 아니면 기본 값을 반환! 
   getOrDefault() 이 메서드를 사용해야 하는 이유는 참가자 중에 동명이인이 존재 할 경우에 아주 효율적인 알고리즘임 
*/
class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        HashMap<String, Integer> hm = new HashMap<>();
        for (String player : participant) hm.put(player, hm.getOrDefault(player, 0) + 1);
        for (String player : completion) hm.put(player, hm.get(player) - 1);

        for (String key : hm.keySet()) {
            if (hm.get(key) != 0){
                answer = key;
            }
        }
        return answer;
    }
}


