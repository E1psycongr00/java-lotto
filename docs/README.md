# 3주차 미션 - 로또
로또를 산 후 당첨 금액과 보너스 번호를 입력해 수익을 얻는 시뮬레이션 게임입니다. <br>
1 ~ 45의 중복되지 않은 6개의 숫자 번호를 입력 및 보너스 번호를 통해 1등 부터 5등까지 받을 수 있는 상금과, <br>
산 로또 별로 맞춘 횟수 정보를 이용해 총 수익률을 알려줍니다. 총 수익률은 로또를 총 수익 / 구입 금액 입니다.

# 패키지 목록
``` text
lotto
├── domain
│     ├── Lotto.java
│     ├── LottoNumber.java
│     ├── Money.java
│     └── Rank.java
│ 
├── layer
│     ├── controller
│     │     └── LottoController.java
│     ├── db
│     │     └── Table.java
│     ├── service
│     │     └── LottoService.java
│     └── view
│           ├── View.java
│           ├── InputView.java
│           └── OutputView.java
│      
└──  Application.java


```

# 기능 목록

## 도메인

- [x] 로또 번호
    - [x] 1 ~ 45의 숫자 범위를 가짐
    - [x] 대소 기능을 지원해야 함

- [x] 로또
    - [x] 6개의 `로또 번호`로 이루어짐
    - [x] 6개의 `로또 번호`가 중복을 허용하지 않음
    - [x] 내부의 `로또 번호`가 오름차순으로 정렬되어야 함

- [x] 당첨(랭크)
    - [x] 1등부터 5등까지 존재함
    - [x] 1등과 5등 사이에 3가지 정보가 존재함
        - [x] 일반 번호 일치 갯수, 보너수 숫자 일치 여부, 상금
        - [x] 당첨 기준에 따른 정보
            - 1등: 6개 번호 일치 / 2,000,000,000원
            - 2등: 5개 번호 + 보너스 번호 일치 / 30,000,000원
            - 3등: 5개 번호 일치 / 1,500,000원
            - 4등: 4개 번호 일치 / 50,000원
            - 5등: 3개 번호 일치 / 5,000원
    - [x] 일반 매칭과 보너스 매칭 정보가 일치하는 당첨 객체 반환
- [x] 금액
    - [x] 1,000 단위로 이루어짐
    - [x] 0은 허용하지 않음
    - [x] 음수를 허용하지 않음
    - [x] 10,000 형태의 화페 형태 문자열 출력 기능

## Layer
- [x] Table
    - [x] 로또, 보너스 번호, 랜덤 생성된 로또 상태를 저장
    - [x] Write 기능
    - [x] Read 기능
- [x] Service
    - [x] 금액을 바탕으로 로또 생성
        - [x] 6개의 중복되지 않는 6개의 수를 랜덤으로 생성
        - [x] Table에 금액과 랜덤 생성된 로또를 리스트로 저장
    - [x] `당첨`의 빈도수 생성
        - [x] 생성된 로또와 당첨 번호(`로또`) 및 보너스번호(`로또 번호`)를 조회해서 `당첨`의 빈도수 카운트
        - [x] 당첨 빈도수에는 0이여도 모든 `당첨`이 key 값에 존재해야함
        - [x] 2등과 3등을 제외한 `당첨`은 bonusNumber 영향없이 commonMatch에만 영향을 받는다
        - [x] 두 입력 당첨 번호(`로또`) 안에 보너스 번호(`로또 번호`)가 들어있으면 안됨
    - [x] 수익률 계산
        - [x] Table 에 저장된 정보를 바탕으로 수익률을 계산한다
            - 수익률 공식 = sum(`당첨`의 `금액` * 당첨 갯수) / 구매 `금액`
- [x] Controller
    - [x] 입력 데이터 검증 및 변환
        - [x] 문자열에서 `금액`으로 변환
        - [x] 문자열에서 `로또`로 변환
        - [x] 문자열에서 `로또 번호`로 변환
    - [x] Viewe와 Service간의 데이터 전달
- [x] View
    - [x] InputView
        - 입력 이전에 글자 출력
        - "구입금액을 입력해 주세요."
        - "당첨 번호를 입력해 주세요."
        - "보너스 번호를 입력해 주세요."
    - [x] outputView
        - 데이터를 받아서 결과를 출력
        - "%d개를 구매했습니다."
        - "당첨 통계"
        - "---"
        - "%d개 일치 (%s원) - %d개"
        - %d개 일치, 보너스 볼 일치 (%s원) - %d개"
        - "총 수익률은 %.1f%%입니다."

# 사용 예시
```
구입금액을 입력해 주세요.
8000

8개를 구매했습니다.
[8, 21, 23, 41, 42, 43]
[3, 5, 11, 16, 32, 38]
[7, 11, 16, 35, 36, 44]
[1, 8, 11, 31, 41, 42]
[13, 14, 16, 38, 42, 45]
[7, 11, 30, 40, 42, 43]
[2, 13, 22, 32, 38, 45]
[1, 3, 5, 14, 22, 45]

당첨 번호를 입력해 주세요.
1,2,3,4,5,6

보너스 번호를 입력해 주세요.
7

당첨 통계
---
3개 일치 (5,000원) - 1개
4개 일치 (50,000원) - 0개
5개 일치 (1,500,000원) - 0개
5개 일치, 보너스 볼 일치 (30,000,000원) - 0개
6개 일치 (2,000,000,000원) - 0개
총 수익률은 62.5%입니다.
```