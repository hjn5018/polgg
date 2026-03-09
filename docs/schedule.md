# 개발 일정 및 계획 (Schedule & Roadmap)

총 16주의 학기 프로젝트를 기준으로 설정한 마일스톤입니다.

## 1. 마일스톤 (Milestones)

| 주차 | 단계 | 주요 목표 |
| :--- | :--- | :--- |
| 1-2주 | 기획 및 분석 | 요구사항 정의, 기능 명세, UI/UX 설계, DB 모델링 |
| 3-4주 | 환경 구축 | 프레임워크 초기 설정, DB 연동, 배포 파이프라인(CI/CD) 구축 |
| 5-8주 | 핵심 개발 1 | 회원가입/인증, 이메일 연동, 포트폴리오 CRUD, 이미지 업로드 |
| 9-11주 | 핵심 개발 2 | 게시판 시스템, 지원 내역 관리, 대시보드 구현 |
| 12-14주 | AI 고도화 | RAG 시스템 구축, LLM 연동 챗봇 개발, 데이터 최적화 |
| 15주 | 안정화 | 통합 테스트, UI/UX 디테일링, 버그 수정 |
| 16주 | 최종 발표 | 최종 시연 및 산출물 정리 |

## 2. 역할 분담 (R&R)
4인 개발 기준 예시입니다.

- **Developer A (팀장/Backend)**: 시스템 아키텍처 설계, 인증 시스템, 데이터베이스 관리.
- **Developer B (Backend/AI)**: 게시판 및 지원 내역 API, AI 인터페이스(RAG), 챗봇 엔진 개발.
- **Developer C (Frontend)**: 포트폴리오/이력서 편집기 UI, 마이페이지 기능, 반응형 디자인.
- **Developer D (Frontend)**: 메인 대시보드, 커뮤니티 UI, 스타일 시스템 구축, QA.

## 3. GitHub Projects 관리 전략
- **Project Board**: `To Do`, `In Progress`, `Review`, `Done` 컬럼 사용.
- **Issue**: 기능 단위로 세분화하여 생성 (예: `Issue #21: JWT Login Implementation`).
- **PR Strategy**: 모든 코드는 Peer Review 후 Merge (최소 1인 승인).
