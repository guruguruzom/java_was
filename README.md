엄용진 WAS구현 프로젝트입니다.
---

## 제작 환경

```
OS : Windows10 Home
JAVA : 11
tool : STS 4.13.1
build : maven
project : WAS 구현
```
---
## 구동 시 주의사항
```
1. windows10 환경으로 제작 되었지만 cmd가 아닌 git bash로 실행해 주셔야합니다.
2. cmd로 clean package 사용 시 was.jar의 symbolic link 명령어의 문제로 빌드가 되지 않습니다.
3. git bash를 사용하실 땐 관리자 권한으로 실행 부탁드리겠습니다(java 실행 시 access문제).
``` 
---
+ 1. HTTP/1.1 의 Host 헤더를 해석하세요.
    - a.com 과 b.com 의 IP 가 같을지라도 설정에 따라 서버에서 다른 데이터를 제공할 수 있어야 합니다.
        * C:\Windows\System32\drivers\etc 파일 추가로 설정합니다.(127.0.0.1=a.com, 127.0.0.1=b.com)
 
>###2. 다음 사항을 설정 파일로 관리하세요.
>> 파일 포맷은 JSON 으로 자유롭게 구성하세요.
>>> config.json 파일로 제작했습니다.
>> 몇 번 포트에서 동작하는지
>>> config.json에서 port설정이 가능하도록 하였습니다.
>>  HTTP/1.1 의 Host 별로 
>>> url-mapping.json에서 관리하도록 되있습니다.
>>> 현재는 localhost=/src/main/resources/templates/home1, 127.0.0.1=/src/main/resources/templates/home2로 이동하도록 설정되어 있습니다.
>>> **home2에는 Hello.html를 제외시켜 404가 발생합니다.**

>###3. 403, 404, 500 오류를 처리합니다.
>> 해당 오류 발생 시 적절한 HTML 을 반환합니다.
>>> 각 에러별로 html 페이지를 호출하여 반환합니다.
>> 설정 파일에 적은 파일 이름을 이용합니다.
>>> url-mapping.json에서 관리하도록 되있습니다.

>###4. 다음과 같은 보안 규칙을 둡니다.
>> 다음 규칙에 걸리면 응답 코드 403 을 반환합니다.
>>> getCanonicalPath().startsWith()로 확인하여 현패 파일 패스가 root보다 상위인지 판단한 후 403을 반환합니다.(canRead 조건은 404발생 페이지를 보여드리기 위해 제외했습니다.)
>>> url.contains에 '.exe'와 '/..'가 존재 한다면 403을 반환합니다.

>###5. logback 프레임워크 http://logback.qos.ch/를 이용하여 다음의 로깅 작업을 합니다.
>> 로그 파일을 하루 단위로 분리합니다.
>>> .\logs\logback에 날짜별 저장하도록 작업했습니다.
>> 로그 내용에 따라 적절한 로그 레벨을 적용합니다.
>>> logback-spring.xml에 log level 정보가 표현되게 작업하였습니다.
>> 오류 발생 시, StackTrace 전체를 로그 파일에 남깁니다
>>> carch문에 error로그를 걸어 처리했습니다.

>###6. 간단한 WAS 를 구현합니다.
>> 다음과 같은 SimpleServlet 구현체가 동작해야 합니다.
>>> com.example.java.simple.impl로 interface 작업을 진행했습니다.
>>> parameter name ?name=eomyongjin 형태로 넣으시면 해당 문자가 출력되며 생략 시엔 {name}으로 출력되도록 처리했습니다.
>> URL 을 SimpleServlet 구현체로 매핑합니다. 규칙은 다음과 같습니다.
>>> http://localhost:8000/Hello --> Hello.java 로 매핑 완료했습니다.
>>> http://localhost:8000/service.Hello --> service 패키지의 Hello.java 로 매핑 완료했습니다.
>> 과제는 URL 을 바로 클래스 파일로 매핑하지만, 추후 설정 파일을 이용해서 매핑하는 것도 고려해서 개발하십시오.
>>> url-mapping.json으로 매핑이 가능하지만 과제 특성상 url-mapping.json에 지정하지 않더라도 클래스파일로 매핑이 가능하도록 제작했습니다.

>###7. 현재 시각을 출력하는 SimpleServlet 구현체를 작성하세요.
>> 앞서 구현한 WAS 를 이용합니다.
>>> com.example.java.simple.Time으로 작업되었습니다. - http://localhost:8000/Time 호출 시 반환됩니다.
>> WAS 와 SimpleServlet 인터페이스를 포함한 SimpleServlet 구현 객체가 하나의 JAR 에 있어도 괜찮습니다.
>>> 분리하지 못했습니다.
	
>###8. 앞에서 구현한 여러 스펙을 검증하는 테스트 케이스를 JUnit4 를 이용해서 작성하세요.
>>> src/test/java의 ApplicationTests 기본 파일로 JUnit을 사용하여 작성했습니다.
>>> JUnit이 개별 동작 가능하도록 Request Processing 반복문또한 쓰레드 처리하였습니다.
        
