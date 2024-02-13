핵심 url에 변수를 잘 가져와 db에서 꺼내 잘 로직시킨
application.property에 목적에 맞게 커스텀 설정


컨트롤러 get,post,patch,delete 등 있음
리턴으로 뷰페이지 혹은 restapi 보내줌
모델이란 변수를 받음 Model moel, model.AddAtribute 뷰 템플릿에 변수 전달 

헤더와 풋터 DOM 분리

DTO 클래스 생성
폼데이터 주고 받기 post 쪽에 받을 DTO의 속성을 네임 name=''으로 설정해

jpa
엔터티 생성-DTO와 관련
DTO를 엔터티로 전환시켜서 레파지토리 DB에 저장한다.

레파지토리 생성 extends CRUD 생성/ 관리할 클래스 명
필요에 따라 오버라이딩
해당 컨트롤러에 등

DB는 의존성에서 직접 설정

getmapping (8000/{value})
function(@pathvariable long value)  변수 가져오기
모델에 엔티티 등록 엔티티의 칼럼들 사용가능

리다이렉트
return 'redirect:8000/~'+getter or function

Responsentity로 통신

모든계층 분리 컨트롤러-서비스-레파지토

트랜잭션
