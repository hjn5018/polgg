# 기술 스택 (Tech Stack)

본 프로젝트는 서비스의 안정성과 확장성, 그리고 최신 AI 기술 도입을 고려하여 다음과 같은 기술 스택을 사용합니다.

## 1. Frontend
- **Framework**: React.js
- **State Management**: Redux Toolkit or Recoil
- **Styling**: Vanilla CSS or Styled Components
- **HTTP Client**: Axios
- **Build Tool**: Vite

## 2. Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **Build Tool**: Gradle
- **Authentication**: Spring Security, JWT (JSON Web Token)
- **Email Service**: Spring Boot Starter Mail (SMTP)

## 3. Database
- **Main RDBMS**: MySQL (또는 PostgreSQL)
- **Cache**: Redis (인증 번호 저장 및 세션 관리용)
- **Vector DB**: Pinecone or Chroma (RAG 엔진용)

## 4. AI & Cloud
- **LLM API**: OpenAI GPT-4o or Claude 3.5 Sonnet
- **Framework**: LangChain or LlamaIndex
- **RAG**: Python 기반 서버 구성 (FastAPI) 또는 Spring AI 활용
- **Infrastructure**: AWS (EC2, RDS, S3 for media uploads)

## 5. Collaboration & DevOps
- **VCS**: Git, GitHub
- **Management**: GitHub Projects, Issue, PR
- **Communication**: Slack / Discord
- **Documentation**: Swagger (OpenAPI 3.0)
