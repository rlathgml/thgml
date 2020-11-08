# Setting

각 부분 별 Install 및 Setting에 대한 설명을 첨부한 파일



## DataBase (MySQL)

> Django의 default DB인 sqlite를 사용하지 않고 mysql을 사용

1. cmd를 사용해서 Docker에 `mysql` 생성 및 접속

   ```cmd
   docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=비번 -d mysql
   
   docker exec -it mysql mysql -u root -p
   ```

2. cmd에서 테이블 생성

   ```sql
   create database HDMD;
   use HDMD;
   ```

   이후 [sql query txt 파일](../Database/query.txt) 복붙해서 Table 생성

3. Django - migrate 하기

   ```bash
   $ python manage.py makemigrations
   $ python manage.py migrate
   ```

### Modify DB

- 해당 App의 `migrations` 폴더 아래에 있는 `0001_initial.py` 파일 삭제
- 3. migrate 과정을 통해 DB 연결 완료



## Back (Django)

> Python을 기반으로 한 Web Frame Work
>
> Backend 디렉토리 내에서 가상환경을 사용

1. 가상환경 생성

   ```bash
   $ cd lastmeal/
   $ python -m venv venv
   ```

2. 가상 환경 들어가기

   ```bash
   $ source venv/Scripts/activate
   ```

3. 패키지 인스톨

   ```bash
   $ pip install -r requirements.txt
   ```

4. Django 실행

   ```bash
   $ python manage.py runserver
   ```

5. 가상 환경 나오기

   ```bash
   $ deactivate
   ```



## Front (Vue.js)

> npm이나 yarn을 사용해서 실행한다. npm만 기입

1. 패키지 설치

   ```bash
   $ cd lastmeal-front/
   $ npm i
   ```

2. Vue.js 실행

   ```bash
   $ npm run serve
   ```



## MySQL - Django 연결

1. Django 연동 설정 (기본 sqlite -> mysql)

   - `settings.py`에서

     ```python
     DATABASES = {
         'default': {
             'ENGINE': 'django.db.backends.mysql',
             'NAME': 'HDMD',  # DB이름
             'USER': 'root',  # 사용자 이름
             'PASSWORD': '',  # 비밀번호
             'HOST': 'localhost',
             'PORT': '3306',
             'OPTIONS': {
                 'init_command': 'SET sql_mode="STRICT_TRANS_TABLES"',
             }
         }
     }
     ```

2. Model 만들기

   - 이미 MySQL로 구현된 모델을 가져오는 과정

     ```bash
     $ python manage.py inspectdb > ./{APP이름}/models.py
     ```

     을 사용해서 해당 `models.py`에서 전체 Model 코드를 자동 생성

   - Migrate 과정

     ```bash
     $ python manage.py makemigrations
     $ python manage.py migrate
     ```



## Server (AWS)

> Putty.exe 사용

1. HostName

   ```
   ubuntu@j3d205.p.ssafy.io
   ```

2. Connection - SSH - Auth 에서 `J3D205T.ppk` 불러오기

3. 실행

4. 서버 키기

   ```bash
   # 가상환경 들어가기 (Linux)
   $ source venv/bin/activate
   
   # 실행
   $ python manage.py runserver 0.0.0.0:8000
   
   # ssl 적용 이후 실행
   $ python manage.py runsslserver 0.0.0.0.:8000
   ```

