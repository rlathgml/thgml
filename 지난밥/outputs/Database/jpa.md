# JPA (Java Persistence API)

## 1. 왜 JPA 씀 ? 
### 기존 문제점 
```
1. 대부분 관계형DB 사용
 - sql 중심의 개발 =>  객체를 sql로 변환 => 무한반복
2. 엔티티 신뢰문제
 - 계층형 아키텍처 진정한 의미의 논리적 계층 분할이 어려움
  (VO, controller, service, repository 같은)
3. SQL 의존적인 개발 피하기 힘듦

→ 객체를 자바 컬렉 션에 저장하듯이 디비에 저장하고 싶다
```

### 그래서 왜 씀 (특징)
```
- sql 중심적인 개발에서 객체중심으로 개발
- 생산성 (쿼리 짤 필요 없음)
- 유지보수 
- 패러다임의 불일치 해결
- 신뢰할 수 있는 엔티티, 계층 
  동일한 트랜잭션에서 조회한 엔티티는 같음을 보장<br>
    -> 마치 자바 컬렉션에 넣었다 뺐다 하는 것 처럼
- 성능 
    1. 1차 캐시와 동일성 보장
       같은 트랜잭션 안에서 처음 부를땐 sql 호출, 두번째 부터는 캐시에서 불러옴
    2. 트랜잭션을 지원하는 쓰기 지연 
       옵션 쓰면 버퍼역할 => 모아서 한번에 insert 가능한 〰
- 데이터 접근 추상화와 벤더 독립성
- 표준
- 지연 로딩 & 즉시 로딩
    - 지연로딩 : 객체가 사용될 때 로딩
    - 즉시로딩 : JOIN SQL로 한번에 연관된 객체까지 미리 조회 (옵션)
    - 현업에서는 보통 다 지연로딩 해놓고 성능테스트해봤는데 별로면 즉시로딩으로 바꾸면 댐 
```

<br>

### orm 
- object-relational mapping(객체 관계 매핑)<br>
- 객체는 객체대로 설계, 관계db는 관계db대로 설계
- orm 프레임워크가 중간에서 매핑
- 객체를 자바 컬렉션에 넣듯이 디비에 넣는 것이 가능해짐
### jpa 
- 자바 진영의 orm 기술 표준
- 자바 어플리케이션과 JDBC API 사이에서 연결해 줌
- 엔티티 분석
- SQL 자동 생성
- JDBC API 사용
- ResultSet 매핑
- 패러다임 불일치 해결 <br>
ex) item 클래스를 상속받는 album 객체가 있을 때, 새 앨범을 디비에 저장할 경우 item, album 에 둘 다 넣어줘야함 (insert 2번). jpa 쓰면  알아서 insert 쿼리 두번 만들어서 넣어줌. 
조회할 때 알아서 join 해서 앨범객체 반환해줌
<br>
<br>

### jpa 는 표준 명세
- jpa는 인터페이스의 모음
- 실질적으로 구현체 '하이버네이트' 사용 (jpa 구현체 : 하이버네이트, EclipseLink, DataNucleus)



orm 은 객체와 rdb 두 기둥위에 있는 기술<br>
둘 다 잘 알아야 한다 ~!~!

---
## 2. JPA 기초와 매핑
<p style="font-size:10px;">
 요거 알면 jpa 기본은 되는 것 ~! ! !<BR>
 1. 객체와 관계디비 사이의 매핑 과정/설계<BR>
 2. 어떤식으로 동작하는지 (영속성 컨텍스트)<BR>
</p>

<br>
###### 메이븐 설정 : 자바 라이브러리 관리, 빌드관리, 의존성 

### ■ 객체 매핑하기
@Entity : JPA가 관리할 객체<br>
@Id : DB PK와 매핑할 필드 

persistence.xml<br>
:  jpa 설정파일 <br>
db 접속정보, sql 실제 보여줄지 정보 등 설정<br>
※ 데이터베이스 방언 : SQL 표준을 지키지 않거나 특정 데이터베이스만의 고유한 기능/문법<br>
=> 설정에서 방언을 바꾸면 디비에 종속적이지 않고 개발 가능

### ■ JPA를 사용한 애플리케이션 개발
1. 엔티티 매니저 팩토리 설정 // 
2. 엔티티 매니저 설정 /
3. 트랜잭션
4. 비즈니스 로직 (CRUD)<br>

<주의사항><br>
- 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유해야함 !!! 
- ★★★엔티티 매니저는 쓰레드간 공유하면 안됨<br>(사용자 요청이 오면 비즈니스 로직이 돌고 버려야 함. 다른 유저가 오면 새 엔티티 매니저를 만들어 써야함 왜냐면 데이터베이스 커넥션 당 엔티티 매니저가 묶여서 다른 사용자랑 공유될 수 있음)
- jpa의 모든 데이터 변경은 트랜잭션 안에서 실행해야 함<br>(스프링이라면 @Transaction 안에서 처리해야 함)
<br>
---
## 3. 필드와 컬럼 매핑

### ■ 데이터베이스 스키마 자동 생성하기
- JPA가 직접 DB 테이블 만들 수 있음
- DDL을 애플리케이션 실행 시점에 자동 생성
- 테이블 중심 => 객체 중심
- 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한 DDL 생성
- 이렇게 생성된 DDL은 ""개발"" 장비에서만 사용
- 생성된 DDL은 운영서버에서는 사용하지 않거나 적절히 다듬은 후 사용

#### hibernate.hbm2ddl.auto
```<property name="hibernate.hbm2ddl.auto" value="create" />```
- create : <u>기존 테이블 삭제 후</u> 다시 생성 
- create-drop : create와 같으나 종료 시점에 drop
- update : 변경분만 반영 (운영 디비에서는 사용하면 안됨)
- validate : 엔티티와 테이블이 정상 매핑되었는지만 확인
- none : 사용하지 않음
<br><br><br>
- 운영 장비에는 절대 create, create-drop, update 사용 금지★★★★<br> 운영에서 썼다가 기존 테이블 drop 되면 레전드
- 개발 초기 - create / update
- 테스트 서버 - update / validate
- 스테이징, 운영서버 - validate / none

### ■ 매핑 어노테이션
 : 철저히 DB에 어떤 식으로 적용할지 매핑정보, 자바 코드에 영향 X

- <strong>@Column</strong> : DB 컬럼명, 자바객체명 매핑
<pre>
@Column(name="USERNAME")  // DB 컬럼명
private String name;      //자바에서 사용할 이름
</pre>
속성<br>
-name : 필드와 매핑할 테이블의 컬럼 이름<br>
-insertable, updatable : 읽기전용<br>
-nullable : null 허용여부 결정, DDL 생성시 사용 <br>
-unique : 유니크 제약 조건, DDL 생성시 사용
-columnDefinition, length, predixion, scale (DDL)
-length : 글자길이 제한 (디폴트 255)
- <strong>@Temporal</strong> : 날짜 타입 매핑 

- <strong>@Enumerated</strong> : 열거형 매핑
<BR>EnumType.ORDINAL 하면 DB에 순서가 저장됨,, enum 클래스에 원소 하나 더 추가하면 순서 꼬이니께 망할수도 있음. 반드시 STRING 해주기 !!!
<pre>
//entity .java
  <strong>@Enumerated(EnumType.STRING) // 반드시 STRING(열거형 이름을 그대로 저장)
  private MemberType memberType;
</pre>
<pre>
//MemberType .java
public enum MemberType {
  USER, ADMIN 
  //0    1      //가운데에 새 원소 추가 시 순서 바뀜
} 
</pre>
- <strong>@Lob</strong>
 : CLOB, BLOB 매핑<BR>
컨텐츠 길이가 길때 바이너리 파일로 디비에 바로 넣어야됨 <BR>
CLOB : Char LOB : String, char, java.sql.CLOB<br>
BLOB : Binary LOB : byte[], java.sql.BLOB<br>

- <strong>@Transient</strong> : 임시필드<BR>
이 필드는 매핑하지 않는다<br>
애플리케이션에서 DB에 저장하지 않는 필드<br>

### ■ 식별자 매핑
- <strong>@Id</strong> : 직접매핑<br>
- <strong>@GeneratedValue</strong>
옵션 <BR>
IDENTITY : DB에 위임, MYSQL <BR>
SEQUENCE : DB 시퀀스 오브젝트 사용, ORACLE , @SequenceGenerator 필요 <BR>
TABLE : 키 생성용 테이블, 모든 DB에서 사용, @TableGenerator 필요 <BR>
AUTO : 방언에 따라 위 세개 중 자동 지정, 기본값 <BR>

권장하는 식별자 전략
 - 기본키 제약 조건 : not null, 유일, 변하면 안됨 
- 권장 : Long 타입, 대체키(데이터와 아무 관련 없는 것), 키 생성전략 사용 
---
## 4. 연관관계 매핑

객체를 테이블에 맞춰 데이터 중심으로 모델링할 경우 협력 관계를 만들 수 없다. 외부에서 나를 찾을 수 없음 

ex) 외래키 매핑하지않고 멤버변수로만 갖고있을 경우




---

참조 : [t academy jpa 강의](https://www.youtube.com/watch?v=WfrSN9Z7MiA&list=PL9mhQYIlKEhfpMVndI23RwWTL9-VL-B7U)

안시조인으로 짜야 여러 디비 호환가능


쿼리를 직접 짰는데 jpa를 사용하면 작업량  줄어듦