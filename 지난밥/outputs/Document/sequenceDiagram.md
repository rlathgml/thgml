# Sequence Diagram of 지난밥 Service 

## 1. 회원

### 1. 로그인

``` mermaid
sequenceDiagram

FrontPage->>KakaoServer:인증 코드 요청
KakaoServer-->>FrontPage:인증 코드 전달
FrontPage->>KakaoServer:인증 코드로 토큰 요청
KakaoServer-->>FrontPage:토큰 전달
FrontPage->>KakaoServer:토큰으로 API 호출
KakaoServer->>KakaoServer:토큰 유효성 확인
KakaoServer-->>FrontPage:응답 전달
FrontPage->>FrontPage:로그인 성공
```

### 2. 회원가입

``` mermaid
sequenceDiagram

FrontPage->>KakaoServer:인증 코드 요청
KakaoServer-->>FrontPage:인증 코드 전달
FrontPage->>KakaoServer:인증 코드로 토큰 요청
KakaoServer-->>FrontPage:토큰 전달
FrontPage->>KakaoServer:토큰으로 API 호출
KakaoServer->>KakaoServer:토큰 유효성 확인
KakaoServer-->>FrontPage:응답 전달

FrontPage->>DjangoServer:사용자 정보 전달
DjangoServer->>DB:데이터 저장
DjangoServer-->>FrontPage:생성 완료 응답
FrontPage->>FrontPage:페이지 전환
```

### 3. 회원 정보 수정

``` mermaid
sequenceDiagram

FrontPage->>DjangoServer:사용자 정보 입력
DjangoServer->>DjangoServer:사용자 정보 수정
DjangoServer->>DB:데이터 저장

DjangoServer-->>FrontPage:생성 완료 응답
FrontPage->>FrontPage:페이지 전환
```

### 4. 회원 탈퇴

``` mermaid
sequenceDiagram

FrontPage->>DjangoServer:사용자 정보 삭제 요청(+pk)
DjangoServer->>DjangoServer:사용자 정보 삭제
DjangoServer->>DB:데이터 삭제

DjangoServer-->>FrontPage:삭제 완료 응답
FrontPage->>FrontPage:페이지 전환
```



## 2. 식단

### 1. 식단 등록

``` mermaid
sequenceDiagram

FrontPage->>DjangoServer:식단 사진 전송
DjangoServer->>DjangoServer:사진 캡셔닝
DjangoServer-->>FrontPage:캡셔닝 데이터 전송

FrontPage->>DjangoServer:식단 메뉴 전달
DjangoServer->>DB:식단 데이터 저장
DB-->>DjangoServer:식단 pk값 전달
DjangoServer-->>FrontPage:생성 완료 응답 (+pk)
FrontPage->>FrontPage:페이지 전환
```

### 2. 식단 확인 

``` mermaid
sequenceDiagram

FrontPage->>DjangoServer:확인 요청
DjangoServer->>DB:식단 데이터 요청 (하루)
DB-->>DjangoServer:식단 데이터 불러오기 (하루)
DjangoServer-->>FrontPage:식단데이터 전송 (하루)
FrontPage->>FrontPage:페이지 전환
```

### 3. 통계정보 확인

``` mermaid
sequenceDiagram

FrontEnd ->> DjangoServer: 사용자 식단 기록 요청
DjangoServer ->> DataBase: 식단 기록, 영양소 정보 받아오기
DataBase -->> DjangoServer: 데이터 전송
DjangoServer -->> FrontEnd: 데이터 전송
FrontEnd ->> FrontEnd: 데이터 가공 & 표시
```

### 4. 식단 수정

``` mermaid
sequenceDiagram

FrontPage->>DjangoServer:수정 요청
DjangoServer->>DB:식단 데이터 요청
DB-->>DjangoServer:식단 데이터 불러오기
DjangoServer-->>FrontPage:식단 데이터 전송 
FrontPage->>DjangoServer:수정된 식단 데이터 전송
DjangoServer->>DB:식단 데이터 저장
DjangoServer-->>FrontPage:수정 완료 응답
FrontPage->>FrontPage:수정 데이터 전달 및 페이지 전환
```

### 5. 식단 삭제

``` mermaid
sequenceDiagram

FrontPage->>DjangoServer:삭제 요청
DjangoServer->>DB:식단 데이터 삭제
DjangoServer-->>FrontPage:삭제 완료 응답
FrontPage->>FrontPage:페이지 전환
```



## 3. Image classification

### 1. object detection

```mermaid
sequenceDiagram

server ->> Google Vision API: 이미지 전송
Google Vision API -->> server: Normalized location 반환
server ->> ImageDirectory: location에 맞춰 이미지 잘라서 저장
```



### 2. model training 

``` mermaid
sequenceDiagram

DataSet ->> django: 데이터셋 전처리 및 로드 
django ->> train.py: 모델 불러오기 
train.py ->> model: 데이터셋 전달
model ->> model: 50 epoch 반복학습
model ->> savedmodel Directory:학습된 모델 저장
```



### 3. predict

``` mermaid
sequenceDiagram

savedmodel Directory ->> django: 학습된 모델 로드
django ->> predict.py: 모델 불러오기
predict.py ->> model: 테스트 이미지 전송
model ->> model: 이미지 분석
model -->> predict.py: 분석 데이터 전달
predict.py -->> django: 분석 데이터 전달
django ->> Vue: 분석 데이터 전달
```
