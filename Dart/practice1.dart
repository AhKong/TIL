void main() {
  var name ; // 변수를 초기화 하지 않으면 다이나믹 타입이 default
  
  name = 1;
  name = 'ahkong';
  name = true;
  
  
  // 다이나믹 타입이 필요한 이유 ?
  /*
   * 변수가 어떤 타입일지 알기 어려운 경우가 있을 수 있기 때문
   *  -> 특히 flutter 와 json이랑 함께 작업하는 경우 그럴 수 있음 
   * */
  
  dynamic test ;
  if(test is String){
    // 여기에서는 test가 String 타입인게 확인이 됐음 
    // 그래서 test.찍어보면 스트링엣 ㅓ사용 할 수 있느 메서드가 다 뜸 ㄸ
    
  }
  
  
  //test2 라는 변수의 타입이 String or null 일 수 있음
  String? test2 = 'ahkong';
  
  test2 = null;
  
  if(test2 != null){
    test2.isNotEmpty;
  }
  
  print(test2?.length);
  
  // dart 내에서 null safety란 어떤 변수, 혹은 어떤 데이터가 null
   // 이  될수 있음을 명시하는  것을 의미함.
  
  
  //final : 수정이 불가능한 변수  
  final test3 = 'test222';
 // test3 =  '1234';
   
  
  //late  :  초기 데이터 없이 변수를 선언할 수 있게 해줌 
  late final String test4 ;
  // do something ex go to api. 
  // 값이 할당 되기 전ㄴ까지 Test4f라는 변수는 사용 불가능 ~
  test4 = 'ahkong';
  
  // const :  수정할 수 없는 변수인건 final 과 비슷하지만 const로 선언한 변수의 값은
  // 컴파일 시점에서 알 수 있는 값이어야 한다는 점에서 차이가 있음 
   
  const max_allowed_price  = 1230;
  
   
}
