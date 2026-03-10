import React, { useState, useEffect } from 'react';
import { Search, MapPin, Calendar, TrendingUp, User as UserIcon } from 'lucide-react';
import api from '../api/axios';

const Home = () => {
    const [portfolios, setPortfolios] = useState([]);
    const [search, setSearch] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchPortfolios();
    }, []);

    const fetchPortfolios = async () => {
        try {
            setLoading(true);
            const response = await api.get('/portfolios');
            setPortfolios(response.data);
        } catch (error) {
            console.error('Fetch portfolios error:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            const response = await api.get(`/portfolios/search?keyword=${search}`);
            setPortfolios(response.data);
        } catch (error) {
            console.error('Search error:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="home-container">
            <header className="home-hero">
                <div className="container">
                    <h1 className="gradient-text">POL.GG</h1>
                    <p className="subtitle">폴리텍 AI융합소프트웨어과 통합 포트폴리오 관리 플랫폼</p>
                    
                    <form className="search-box-wrapper glass" onSubmit={handleSearch}>
                        <Search size={20} className="search-icon" />
                        <input 
                            type="text" 
                            placeholder="기술 스택, 이름, 제목으로 검색..." 
                            value={search}
                            onChange={(e) => setSearch(e.target.value)}
                        />
                        <button type="submit" className="search-btn primary">검색</button>
                    </form>
                </div>
            </header>

            <main className="container dashboard-main">
                <div className="section-header">
                    <h2><TrendingUp size={24} className="icon" /> 취업 준비생 포트폴리오</h2>
                    <p>최근 공개된 우수 포트폴리오 리스트입니다.</p>
                </div>

                <div className="portfolio-hn-list">
                    {loading ? (
                        <div className="loading">데이터를 불러오는 중...</div>
                    ) : portfolios.length > 0 ? (
                        portfolios.map((item, index) => (
                            <div key={item.id} className="hn-item glass-hover">
                                <span className="rank">{index + 1}.</span>
                                <div className="hn-content">
                                    <h3 className="title">
                                        <a href={`/portfolio/${item.id}`}>{item.title}</a>
                                        <span className="tags">
                                            {item.githubUrl && <span className="tag github">GitHub</span>}
                                            {item.blogUrl && <span className="tag blog">Blog</span>}
                                        </span>
                                    </h3>
                                    <div className="meta">
                                        <span className="score">인기도: {Math.floor(Math.random() * 100)} points</span>
                                        <span className="divider">|</span>
                                        <span className="user">
                                            <UserIcon size={12} /> {item.contact || '익명'}
                                        </span>
                                        <span className="divider">|</span>
                                        <span className="time">{new Date(item.createdAt).toLocaleDateString()}</span>
                                    </div>
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="empty-state">아직 공개된 포트폴리오가 없습니다.</div>
                    )}
                </div>
            </main>

            <style>{`
        .home-container {
          padding-bottom: 100px;
        }
        .home-hero {
          padding: 100px 0 60px;
          text-align: center;
        }
        .home-hero h1 {
          font-size: 4rem;
          font-weight: 900;
          letter-spacing: -2px;
          margin-bottom: 10px;
        }
        .home-hero .subtitle {
          color: var(--text-muted);
          font-size: 1.2rem;
          margin-bottom: 40px;
        }
        .search-box-wrapper {
          max-width: 700px;
          margin: 0 auto;
          display: flex;
          align-items: center;
          padding: 8px 10px 8px 24px;
          border-radius: 40px;
          background: rgba(30, 41, 59, 0.5);
          border: 1px solid var(--border);
        }
        .search-icon {
          color: var(--text-muted);
        }
        .search-box-wrapper input {
          flex: 1;
          background: none;
          border: none;
          padding: 12px 16px;
          font-size: 16px;
          color: white;
        }
        .search-btn {
          padding: 10px 24px;
          border-radius: 30px;
          font-weight: 600;
        }
        
        .dashboard-main {
          margin-top: 40px;
        }
        .section-header {
          margin-bottom: 30px;
        }
        .section-header h2 {
          display: flex;
          align-items: center;
          gap: 12px;
          font-size: 24px;
          color: var(--text-main);
        }
        .section-header .icon {
          color: var(--primary);
        }
        .section-header p {
          color: var(--text-muted);
          margin-top: 4px;
        }

        .portfolio-hn-list {
          display: flex;
          flex-direction: column;
          gap: 2px;
          background: var(--border);
          border-radius: 12px;
          overflow: hidden;
          border: 1px solid var(--border);
        }
        .hn-item {
          display: flex;
          padding: 16px 24px;
          background: var(--bg-main);
          gap: 16px;
          transition: background 0.2s;
        }
        .hn-item:hover {
          background: rgba(99, 102, 241, 0.05);
        }
        .rank {
          color: var(--text-muted);
          font-family: monospace;
          font-size: 16px;
          width: 30px;
          padding-top: 2px;
        }
        .hn-content .title {
          font-size: 17px;
          font-weight: 400;
          margin-bottom: 4px;
          display: flex;
          align-items: center;
          gap: 10px;
        }
        .hn-content .title a {
          color: var(--text-main);
          text-decoration: none;
        }
        .hn-content .title a:hover {
          text-decoration: underline;
        }
        .tags {
          display: flex;
          gap: 6px;
        }
        .tag {
          font-size: 10px;
          padding: 2px 6px;
          border-radius: 4px;
          font-weight: 700;
          text-transform: uppercase;
        }
        .tag.github { background: #333; color: white; }
        .tag.blog { background: #f48024; color: white; }

        .meta {
          font-size: 12px;
          color: var(--text-muted);
          display: flex;
          align-items: center;
          gap: 8px;
        }
        .divider {
          opacity: 0.3;
        }
        .user {
          display: flex;
          align-items: center;
          gap: 4px;
        }

        .loading, .empty-state {
          padding: 60px;
          text-align: center;
          background: var(--bg-main);
          color: var(--text-muted);
        }
      `}</style>
        </div>
    );
};

export default Home;
