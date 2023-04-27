void main(){
// List  
  var numbers = [1,2,3,4];
  numbers.last;
  numbers.first;

  
//  numbers.add("sisi");
  
  List<int> numbers2 = [1,2,3,4];
  
  numbers2.add(1);
  
 // numbers2.add("string") x
  
  // collection if 
  var giveMeFive = true;
  var numbers3 = 
    [1,
     2,
     3,
     4,
    if(giveMeFive) 5];
  
  print(numbers3);
  
  
// =======
  
 // String Interpolation - text에 변수를 추가하는 방법
  
  var name = 'ahreum';
  var age = 10;
  var greeting = 'hello everyone, my name is $name, and I am ${age+2} !nice to meet you!';
  
  print(greeting);
  
  // collection for 
  
  var oldFriends = ['nico', 'ahkong'];
  var newFriends = [
    'ahreum',
    'minsu',
    for(var freind in oldFriends) " 🤍$freind",
  ];
  
  print(newFriends);
  
  
  // Maps
  
   var player = {
     'name' : 'ahreum',
     "xp" : 19.9,
     "superpower" : false
   };
  
  
  Map<int,bool> player2 = {
    1 : true,
    2 : false
  };
  

  // sets
  // set과 리스트의 차이는 set에 속한 아이템은 모두 유니크 하다는 점임 
  var setNumbers = {1,2,3,4};
  setNumbers.add(1);
  setNumbers.add(1);
  setNumbers.add(1);
 //중복된 값이기 때문에 실제로 출력해보면 1,2,3,4 임 
  print(setNumbers);
  Set<int> setNumbers2 = {1,2,3,4};
  
  
  // Defining a Fucntion 
  print(sayHello('ahreum'));
  
  //named parameter
  print(sayHello3(
    age : 12,
    country : 'cuba',
    name : 'potato'
  ));
  
}


String sayHello(String name){
  return "Hello, $name nice to meet you!";
}
// allow fucntion 
String sayHello2(String name) => "Hello, $name nice to meet you!";

// default value 셋팅
String sayHello3({String name = 'default name', int age = 99, String country = 'korea'}){
  return "Hello, $name nice to meet you! you are $age. you come from $country";
}

// 필수 값이라고 명시 
String sayHello4({required String name , required int age, required String country}){
  return "Hello, $name nice to meet you! you are $age. you come from $country";
}

