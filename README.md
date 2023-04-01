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

### HTTP 2.0
HTTP2.0은 HTTP1.1의 프로토콜을 계승해 동일한 API 면서 성능 향상에 초점을 맞추었다.

HTTP 1.1 은 기본적으로 클라이언트의 요청이 올때만 서버가 응답을 하는 구조로 매 요청마다 connection 을 생성해야만 한다. 
따라서 RTT<sub>Round Trip Time</sub><sup>1</sup> 과 같은 문제가 발생하며, 
메타 정보들을 저장하는 무거운 header 가 요청마다 중복 전달되어 비효율적이며 느린 속도를 보여주었다.

HTTP 2.0 에서는 아래와 같은 방법을 통해 성능을 향상하였다.

- Multiplexed Streams   
  - 한 connection으로 동시에 여러개의 메시지를 주고 받을 수 있다.
  - Response는 순서에 상관없이 Steram 으로 받는다.
  - Stream Prioritization
    - 리소스 간 우선순위를 설정해 Client 가 먼저 필요한 리소스부터 보내줄 수 있다.
- Server Push
  - Server 는 Client 가 요청하지 않은 리소스를 마음대로 보내줄 수 있다.
- Header Compression
  - HPACK 압축방식을 통해 Header 를 압축하여 중복 제거 후 전달

> **외전**   
> HTTP 1.1 에서 동시전송 문제와 다수 리소스를 처리하기 위해 `Pipelining`이 제안되었다.   
> 
> HTTP 1.0 의 동작은 2개의 요청을 보낼 때, `요청(1)->응답(1)->요청(2)->응답(2)` 와 하나의 요청이 다 끝나야 다음 요청을 보낼 수 있었다.
>    
> 반면, Pipelining 을 사용할 경우 `요청(1)->요청(2)->응답(1)->응답(2)` 와 같은 형식으로 
> 요청에 대한 응답을 받지 않아도 여러개의 요청을 하나의 TCP/IP Pakcet 으로 Packing 할 수 있다.   
>    
> 하지만 이러한 Pipelining 에도 HOLB<sub>Head Of Line Blocking</sub> 등.. 과 같은 문제가 발생 한다.   
> ** HOLB<sub>Head Of Line Blocking</sub> : 만약 첫번째 응답이 지연되면, 이후 두번째, 세번째 응답도 같이 지연되는 문제이다.

> <sup>1</sup>RTT<sub>Round Trip Time</sub>   
> HTTP 1.1 은 하나의 Connection, 하나의 요청으로 작동하기 때문에 Connection 을 생성 할 때 마다 TCP 연결을 하게된다.
> TCP Connection 은 시작 시 3-way handshake, 종료 시 4-way handshake 를 진행하게되기 때문에 이로 인한 오버헤드가 발생한다.

<참고>
 - https://medium.com/naver-cloud-platform/nbp-기술-경험-시대의-흐름-grpc-깊게-파고들기-1-39e97cb3460
 - https://chacha95.github.io/2020-06-15-gRPC1/