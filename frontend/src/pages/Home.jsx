import React from 'react';

const Home = () => {
    return (
        <div className="container home-page">
            <header className="hero">
                <h1 className="gradient-text">POLYTECH AI.GG</h1>
                <p className="subtitle">폴리텍 AI융합소프트웨어과 통합 포트폴리오 관리 플랫폼</p>
            </header>

            <section className="dashboard-grid glass">
                <div className="empty-state">
                    <h2>환영합니다!</h2>
                    <p>포트폴리오를 작성하고 커리어를 관리해보세요.</p>
                    <button className="cta-btn primary">지금 시작하기</button>
                </div>
            </section>

            <style>{`
        .home-page {
          text-align: center;
          padding: 80px 0;
        }
        .hero h1 {
          font-size: 3.5rem;
          font-weight: 800;
          margin-bottom: 16px;
        }
        .hero .subtitle {
          font-size: 1.25rem;
          color: var(--text-muted);
          margin-bottom: 48px;
        }
        .dashboard-grid {
          padding: 60px;
          border-radius: 20px;
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .cta-btn {
          margin-top: 24px;
          padding: 12px 32px;
          border-radius: 8px;
          border: none;
          background: var(--primary);
          color: white;
          font-weight: 700;
        }
      `}</style>
        </div>
    );
};

export default Home;
