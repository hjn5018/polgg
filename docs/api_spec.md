# API 명세 (API Specification)

## 1. Authentication (인증)
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| POST | `/api/auth/email-verify` | 이메일 인증 코드 발송 | No |
| POST | `/api/auth/signup` | 학번/이메일 기반 회원가입 | No |
| POST | `/api/auth/login` | 학번/비밀번호 로그인 (JWT 반환) | No |

## 2. User & My Page (마이페이지)
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| GET | `/api/user/me` | 내 프로필 정보 조회 | Yes |
| GET | `/api/portfolios` | 내 포트폴리오 목록 조회 | Yes |
| POST | `/api/portfolios` | 새 포트폴리오 생성 | Yes |
| GET | `/api/portfolios/{id}` | 포트폴리오 상세 조회 | Yes/Partial |
| PUT | `/api/portfolios/{id}` | 포트폴리오 수정 | Yes |
| DELETE | `/api/portfolios/{id}` | 포트폴리오 삭제 | Yes |
| PATCH | `/api/portfolios/{id}/visibility` | 포트폴리오 공개 여부 토글 | Yes |

## 3. Application Registry (지원 기록)
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| GET | `/api/applications` | 지원 내역 리스트 조회 | Yes |
| POST | `/api/applications` | 신규 지원 내역 추가 | Yes |
| PUT | `/api/applications/{id}` | 지원 상태 업데이트 | Yes |

## 4. Community Board (자격증 게시판)
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| GET | `/api/posts?category={c}` | 게시글 목록 필터링 조회 | No |
| POST | `/api/posts` | 게시글 작성 | Yes |
| GET | `/api/posts/{id}` | 게시글 상세 조회 | No |
| PUT | `/api/posts/{id}` | 게시글 수정 | Yes |
| DELETE | `/api/posts/{id}` | 게시글 삭제 | Yes |

## 5. Main Dashboard & Chatbot
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| GET | `/api/dashboard/latest` | 최신 공개 포트폴리오 목록 | No |
| POST | `/api/chatbot/ask` | AI 포트폴리오 상담 챗봇 | No/Yes |
