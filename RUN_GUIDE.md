# 🚀 POL.GG 실행 가이드 (Getting Started)

이 문서는 POL.GG 프로젝트를 로컬 환경에서 구동하기 위한 순차적인 지침을 제공합니다.

---

## 📋 사전 준비 사항 (Prerequisites)
- **Docker & Docker Compose**
- **Java 17 (JDK)**
- **Node.js (v18 이상)**
- **IDE** (IntelliJ IDEA 또는 VS Code 추천)

---

## 1단계: 인프라 서비스 실행 (Docker)
애플리케이션 운영에 필요한 DB, 캐시, AI 인프라를 실행합니다.

```bash
# 프로젝트 루트 디렉토리에서 실행
docker-compose up -d
```

### AI 모델 다운로드 (선택 사항)
챗봇 기능을 사용하려면 Docker 컨테이너 내부에 모델 설치가 필요합니다.
```bash
docker exec -it polgg-ollama ollama pull llama3
docker exec -it polgg-ollama ollama pull nomic-embed-text
```

---

## 2단계: 백엔드(Spring Boot) 실행
데이터베이스 연결 및 API 서버를 구동합니다.

```bash
cd backend
# 윈도우 환경
./gradlew bootRun
# Mac/Linux 환경
./gradlew bootRun
```
- **기본 포트**: `http://localhost:8080`
- **H2 콘솔 (개발용)**: `http://localhost:8080/h2-console` (설정 시)

---

## 3단계: 프론트엔드(React) 실행
사용자 인터페이스를 구동합니다.

```bash
cd frontend
npm install
npm run dev
```
- **접속 주소**: `http://localhost:5173`

---

## 🛠 주요 기능 테스트 방법

### 1. 회원가입 및 로그인
- 학번(10자리)과 `@office.kopo.ac.kr` 이메일로 가입할 수 있습니다.
- 현재 테스트 환경에서는 메일 전송 대신 로그를 확인하거나 DB의 `is_verified` 컬럼을 수동으로 업데이트하여 인증을 완료할 수 있습니다.

### 2. AI 챗봇 사용
- 로그인 후 우측 하단 **Bot 아이콘**을 클릭합니다.
- 처음 사용 시, 챗봇 상단의 **새로고침(Refresh)** 버튼을 눌러 DB 데이터를 학습(RAG) 시켜야 정확한 답변이 가능합니다.

### 3. 포트폴리오 관리
- 마이페이지에서 포트폴리오를 작성하고 '공개'로 설정하면 메인 대시보드(Home)에 노출됩니다.

---

## 🆘 문제 해결 (Troubleshooting)
- **DB 접속 오류**: Docker 컨테이너가 정상적으로 `Up` 상태인지 `docker ps`로 확인하세요.
- **포트 충돌**: 3306, 6379, 11434 포트가 이미 사용 중인지 확인하세요.
- **Ollama 에러**: 챗봇 연결이 안 될 경우 `polgg-ollama` 컨테이너 로그를 확인하세요.

---
> © 2026 POL.GG Developement Team.
