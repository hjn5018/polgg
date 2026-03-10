# ✅ POL.GG 프로젝트 개발 태스크 리스트 (Task List)

본 리스트는 16주 학기 프로젝트 진행 상황을 추적하기 위한 체크리스트입니다.

---

## 1. 프로젝트 설계 및 환경 구축 (Week 1-4)
- [x] 요구사항 분석 및 기능 명세 확정
- [x] DB 모델링 및 ERD 작성
- [x] UI/UX 와이어프레임 및 목업 설계
- [x] API 개발 명세서 작성
- [x] Backend: Spring Boot 프로젝트 초기화 및 Gradle 설정
- [x] Frontend: Vite + React 프로젝트 초기화
- [x] DB: MySQL 및 Redis 환경 구축
- [x] CI/CD: GitHub Actions 배포 파이프라인 구축

---

## 2. 핵심 기능 개발 1: 인증 및 인프라 (Week 5-8)
- [x] 사용자 엔티티 및 Repository 구현 (Member, MemberRepository)
- [x] @office.kopo.ac.kr 이메일 인증 발송 로직 구현 (SMTP & Redis)
- [x] 학번/이메일 기반 회원가입 기능 (10자리 학번 검증)
- [x] JWT 기반 로그인 시스템 및 Spring Security 설정 (비밀번호 암호화 포함)
- [x] S3 이미지/비디오 파일 업로드 유틸리티 개발
- [x] 마이페이지 기본 프로필 조회/수정 API

---

## 3. 핵심 기능 개발 2: 포트폴리오 및 지원 관리 (Week 9-11)
- [x] **Portfolio CRUD**: 메인/기타 포트폴리오 생성 및 관리
- [x] **Project Management**: 프로젝트명, 기간, 시연 영상, 역할 등 등록 기능
- [x] **Job Application Tracker**: 지원 기업 정보, 경로, 결과(합불) 기록 기능 (마이페이지 최상단 배치)
- [ ] **Dashboard**: 메인 페이지 Hacker News 스타일 텍스트 리스트 구현
- [ ] **Search & Filter**: 포트폴리오 및 이력서 공개 여부에 따른 탐색 기능

---

## 4. 커뮤니티 및 부가 기능 (Week 11-12)
- [ ] **Certification Board**: 자격증 정보 공유 게시판 (카테고리: 합격수기, 정보 등)
- [ ] **Admin Notice**: 관리자 전용 자격증 권장/공지 작성 기능
- [ ] **Comment System**: 게시글 댓글 및 대댓글 기능
- [ ] **Role-based Access**: 학생 및 관리자 권한 분리 적용

---

## 5. AI 고도화 및 안정화 (Week 12-14)
- [ ] 공개 포트폴리오 및 지원 데이터 수집 (RAG용)
- [ ] Vector DB 연결 및 데이터 임베딩 로직 개발
- [ ] LLM(GPT-4o 등) 연동 챗봇 API 구현
- [ ] 챗봇 UI 컴포넌트 개발 및 통합 테스트
- [ ] 시스템 성능 최적화 및 보안 점검

---

## 6. 테스트 및 최종 배포 (Week 15-16)
- [ ] 단위 테스트 및 통합 테스트 수행
- [ ] UI/UX 디테일 수정 및 반응형 디자인 보완
- [ ] 최종 버그 리포트 작성 및 수정
- [ ] 서비스 운영 환경 최종 배포
- [ ] 최종 발표 자료 및 산출물 정리
