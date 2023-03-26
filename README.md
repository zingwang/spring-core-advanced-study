# spring-core-advanced-study
인프런 스프링 핵심 원리 - 고급편

#### 쓰레드 로컬 (ThreadLocal)
- 싱글톤으로 등록된 스프링 빈의 경우 여러 스레드가 동시에 접근하는 경우 동시성 이슈 발생 > 이를 막기 위해 ThreadLocal 사용

#### 템플릿 메서드 패턴 
- 단일 책임 원칙(SRP)으로 변경 지점을 하나로 모아서 변경에 쉽게 대처할 수 있는 구조
- 자식 클래스는 부모 클래스의 기능 사용불가 , 부모클래스에 의존
- 상속의 단점을 개선하는 것이 전략 패턴

#### 전략패턴
- 인터페이스를 사용하여 위임
- 필드: 선 조립, 후 실행
- 파라미터 방식: 실행 시 마다 전략을 유연하게 변경

#### 프록시 패턴 데코레이터 패턴 
- 모양이 비슷한 두 패턴은 만든 의도에 따라 구분
- 프록시패턴(접근제어):  다른 개체에 대한 접근을 제어하기 위한 대리자 제공
- 데코레이터(추가책임 동적추가): 객체에 추가 책임을 동적으로 추가, 기능 확장을 유연하게 제공

#### 인터페이스 기반 프록시 패턴
- client > 컨트롤러proxy > 컨트롤러 > 서비스 proxy > 서비스> ... <br>
- 스프링 컨테이너에 프록시 객체 등록 <br>
- 스프링 컨테이너가 실제 객체를 관리하지않음.<br>
- component 항상 호출 

#### 인터페이스 기반 프록시 vs 클래스 기반 프록시
- 클래스기반 : 해당클래스, 부모 클래스 생성자 호출, final 상속 불가, 메서드 fianl 오버라이딩 불가 
- 인터페이스 기반: 인터페이스만 같으면 모든 곳 사용 가능(상속이라는 제약에서 자유로움)<br>
 인터페이스를 나누면 구현체를 편리하게 변경 가능하지만 구현을 변경할 가능성이 없는 것은 실용적이지 않음

#### 동적 프록시 기술
- 리플렉션 기술: 메타정보를 동적으로 획득하고, 코드도 동적으로 호출 
- JDK 동적프록시:  인터페이스에서만 사용 가능
- CGLIB: 구체클래스로만 동적프록시 사용, final로 선언 시 상속이나 오버라이딩 불가
