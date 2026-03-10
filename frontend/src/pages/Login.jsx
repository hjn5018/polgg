import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';

const Login = () => {
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            const response = await axios.post('/api/auth/login', {
                loginId,
                password,
            });

            // TODO: Store token in context/local storage
            console.log('Login Success:', response.data);
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('user', JSON.stringify(response.data));

            navigate('/');
        } catch (err) {
            setError(err.response?.data?.error || '로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-container">
            <form className="login-card glass" onSubmit={handleLogin}>
                <h2>Welcome Back</h2>
                <p className="form-subtitle">로그인을 통해 포트폴리오 관리를 시작하세요.</p>

                {error && <div className="error-message">{error}</div>}

                <div className="input-field">
                    <label>Email or Student ID</label>
                    <input
                        type="text"
                        placeholder="example@office.kopo.ac.kr or 2024010101"
                        value={loginId}
                        onChange={(e) => setLoginId(e.target.value)}
                        required
                        autoComplete="username"
                    />
                </div>

                <div className="input-field">
                    <label>Password</label>
                    <input
                        type="password"
                        placeholder="••••••••"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        autoComplete="current-password"
                    />
                </div>

                <button type="submit" className="login-btn primary" disabled={loading}>
                    {loading ? 'Logging in...' : 'Sign In'}
                </button>

                <p className="signup-link">
                    Don't have an account? <Link to="/signup">Create one now</Link>
                </p>
            </form>

            <style>{`
        .login-container {
          display: flex;
          justify-content: center;
          align-items: center;
          height: calc(100vh - 150px);
        }
        .login-card {
          width: 440px;
          padding: 40px;
          border-radius: 16px;
        }
        .login-card h2 {
          font-size: 2rem;
          margin-bottom: 8px;
        }
        .form-subtitle {
          color: var(--text-muted);
          margin-bottom: 32px;
        }
        .input-field {
          margin-bottom: 20px;
          text-align: left;
        }
        .input-field label {
          display: block;
          font-size: 0.9rem;
          margin-bottom: 8px;
          color: var(--text-muted);
        }
        .input-field input {
          width: 100%;
        }
        .login-btn {
          width: 100%;
          margin-top: 24px;
          padding: 12px;
          border-radius: 8px;
          border: none;
          background: var(--primary);
          color: white;
          font-weight: 700;
        }
        .error-message {
          padding: 12px;
          background: rgba(239, 68, 68, 0.1);
          color: var(--error);
          border-radius: 8px;
          margin-bottom: 24px;
          font-size: 0.9rem;
        }
        .signup-link {
          margin-top: 24px;
          font-size: 0.9rem;
          color: var(--text-muted);
        }
        .signup-link a {
          color: var(--primary);
          text-decoration: none;
        }
      `}</style>
        </div>
    );
};

export default Login;
