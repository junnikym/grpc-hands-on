# gRPC hands-on
gRPC<sub>Google Remote Procedure Call</sub> 는 Google 에서 만든 RPC 시스템이다. 

## 들어가기 전
gRPC 를 알기위해 `RPC`와 `HTTP 2.0`에 대한 이해가 필요하다.

### RPC <sub>Remote Procedure Call</sub>
 RPC는 말 그대로 네트워크 내부의 다른 서버의 프로시저를 원격으로 호출하는 기능이다. 
통신이나 call 방식에 신경쓰지 않고 원격지의 자원을 사용할 수 있다는 장점이 있다. 
또한 `IDL`<sup>1</sup> 기반으로 다양한 언어를 가진 환경에서도 쉽게 확장이 가능하다.   

 RPC의 핵심 개념은 `Stub`이라는 것이다. Server 와 Client 는 함수 호출에 사용된 매개변수를 변환해야한다.
이를 담당하는 것이 Stub 이다. Stub 의 동작 방식은 다음과 같다.
 ```
1. IDL 을 사용, 호출 규약 정의
  - IDL 파일을 rpcgen 으로 컴파일 시 Stub Code 가 생성됨.
2. Stub Code 에 명시된 함수는 원시코드로, 상세 기능은 Server 에서 구현
  - Stub Code 는 Client, Server 에 함께 빌드.
3. Client Stub 은 RPC runtime 을 통해 함수 호출.
5. Server 는 수신된 Procedure 처리 후 결과 값 반환.
6. Client 는 결과 수신, 함수를 Local에 있는 것 처럼 사용 가능.
 ```

 단, RPC 는 쉽게 찾아 볼 수 없다. RPC 는 상당히 획기적인 방법론이지만, 
 구현이 어렵고 지원 기능의 한계 등.. 이 있었기 때문에 활용이 제대로 되지 않았다.
대신 이 자리를 REST 가 차지하게된다.

> <sup>1</sup> IDL <sub>Interface Definication Language</sub>   
> Server 와 Client 가 서로 정보를 주고 받는 규칙이 프로토콜이라면, IDL 은 정보를 저장하는 규칙이다.   
> 어떤 언어로 작성 된 프로그램 또는 객체가, 다른 알려지지 않은 언어로 작성된 프로그램과 통신 살 수 있도록 해준다.   
>    
> <sub> * ref: <a> http://www.terms.co.kr/IDL.htm </a></sub>

<참고>
 - https://medium.com/naver-cloud-platform/nbp-기술-경험-시대의-흐름-grpc-깊게-파고들기-1-39e97cb3460
 - https://chacha95.github.io/2020-06-15-gRPC1/