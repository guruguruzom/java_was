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
## 스펙 및 수행 정보
         
* **config관리**
    + config.json에서 suffix 처리
    + config.json에서 포트 처리
    + url-mapping.json에서 에러페이지 관리(403, 404, 500만 처리)


* **잘못된 접근 처리 403처리**
    + getCanonicalPath().startsWith()를 통한 상위 root접근
    + 상위폴더접근 /..과 restful 규칙에 위배되는 .exe 파일 접근


* **http://logback.qos.ch/를 통한 로그 관리**
    + .\logs\logback에 날짜별 저장


* **파라미터 처리**
    + hello페이지에서 ?test=aa등으로 파라미터값이 넘
