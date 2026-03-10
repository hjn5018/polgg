# 📜 POL.GG 프로젝트 개발 문서 (Technical Documentation)

![POL.GG Banner](file:///C:/Users/USER/.gemini/antigravity/brain/c4d73184-f766-4604-9cfb-967373ca0828/polgg_banner_1773184303944.png)

## 1. 프로젝트 개요 (Overview)
**POL.GG**는 폴리텍대 AI융합소프트웨어과 학생들을 위한 **통합 커리어 관리 및 포트폴리오 플랫폼**입니다. 학생들은 자신의 포트폴리오를 관리하고, 실제 취업 지원 이력을 추적하며, AI 기반 커리어 컨설턴트로부터 조언을 받을 수 있습니다.

---

## 2. 사용된 기술 스택 (Tech Stack)

### 🖥 Backend
- **Framework**: Spring Boot 3.4.3 (Java 17)
- **Database**: 
  - **MySQL**: 관계형 데이터 저장 (회원, 포트폴리오, 게시글 등)
  - **Redis**: 이메일 인증 토큰 저장 및 벡터 데이터 캐싱
- **Security**: Spring Security & JWT (JSON Web Token)
- **File Storage**: AWS S3 (이미지, 비디오 파일 업로드)
- **AI/RAG**:
  - **LangChain4j**: AI 파이프라인 구조화
  - **Ollama**: 로컬 LLM (llama3, nomic-embed-text) 연동 (데이터 프라이버시 고려)

### 🎨 Frontend
- **Library**: React 19 (Vite 기반)
- **Router**: React Router 7
- **Styling**: Vanilla CSS (Modern Glassmorphism Design)
- **Icons**: Lucide React
- **API Client**: Axios

---

## 3. 핵심 구현 기능 (Implemented Features)

### 🔐 1단계: 인증 및 인프라
- **회원가입/로그인**: 대학교 이메일(@office.kopo.ac.kr) 인증 기반 가입.
- **JWT 보안**: 액세스 토큰 기반의 무상태(Stateless) 인증 시스템.
- **파일 업로드**: AWS SDK v2를 활용한 S3 클라우드 스토리지 연동.

### 💼 2단계: 포트폴리오 및 지원 관리
- **Portfolio CRUD**: 공개/비공개 설정이 가능한 통합 포트폴리오 시스템.
- **Project Tracking**: 프로젝트별 시연 영상, 역할, 기술 스택 관리.
- **Application Tracker**: 지원 기업명, 채널, 단계(서류/면접), 최종 합불 여부 기록.
- **Hacker News Dashboard**: 메인 페이지에서 우수 포트폴리오를 텍스트 리스트 형식으로 탐색.

### 🗣 3단계: 커뮤니티 및 소통
- **자격증 정보 게시판**: 합격 수기 및 정보 공유를 위한 계층형 댓글(대댓글) 시스템.
- **공지사항**: 관리자가 자격증 정보를 공지하고 상단에 고정하는 기능.

### 🤖 4단계: AI 고도화 (RAG)
- **Local RAG Pipeline**: 플랫폼 내 축적된 공개 포트폴리오와 합불 사례를 학습하여 실시간 조언 제공.
- **데이터 프라이버시**: Ollama를 통해 외부 유출 없이 서버 내부에서 LLM 추론 수행.

---

## 4. 필수 라이브러리 및 리소스 (Resources)

### Backend Dependencies (`build.gradle`)
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    implementation 'io.jsonwebtoken:jjwt-api:0.12.6'
    implementation 'software.amazon.awssdk:s3:2.25.27'
    implementation 'dev.langchain4j:langchain4j-ollama:0.36.2'
}
```

### Frontend Dependencies (`package.json`)
```json
"dependencies": {
    "axios": "^1.13.6",
    "lucide-react": "^0.577.0",
    "react-router-dom": "^7.13.1"
}
```

---

## 5. 실행 및 관리 명령 (Commands)

### ✅ Backend 실행
```bash
./gradlew bootRun
```

### ✅ Frontend 실행
```bash
npm install
npm run dev
```

### 🦙 로컬 AI (Ollama) 설정
```bash
ollama serve
ollama pull llama3
ollama pull nomic-embed-text
```

---

## 6. 환경 변수 설정 (Environment Variables)
`.yml` 또는 시스템 환경 변수에 다음 항목 설정이 필요합니다:
- `OPENAI_API_KEY`: (OpenAI 사용 시 필요)
- `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`, `AWS_S3_BUCKET`
- `MAIL_USERNAME`, `MAIL_PASSWORD` (G-Mail SMTP)

---

> [!NOTE]  
> 본 프로젝트는 보안과 데이터 익명화를 우선으로 설계되었으며, 모든 코드 변화는 GitHub 레포지토리에 기록되고 있습니다.
