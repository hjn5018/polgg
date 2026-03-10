import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';

const Signup = () => {
    const [formData, setFormData] = useState({
        studentId: '',
        email: '',
        password: '',
        name: '',
        verificationCode: ''
    });
    const [isEmailSent, setIsEmailSent] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const sendVerificationCode = async () => {
        if (!formData.email.endsWith('@office.kopo.ac.kr')) {
            setError('폴리텍 대학교 이메일 (@office.kopo.ac.kr)만 사용 가능합니다.');
            return;
        }

        setLoading(true);
        setError('');
        setMessage('');

        try {
            await axios.post('/api/auth/email-verify', { email: formData.email });
            setIsEmailSent(true);
            setMessage('인증 코드가 이메일로 전송되었습니다. 5분 이내에 확인해주세요.');
        } catch (err) {
            setError(err.response?.data?.error || '이메일 전송에 실패했습니다.');
        } finally {
            setLoading(false);
        }
    };

    const handleSignup = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            await axios.post('/api/auth/signup', formData);
            alert('회원가입이 완료되었습니다. 로그인해주세요.');
            navigate('/login');
        } catch (err) {
            setError(err.response?.data?.error || '회원가입에 실패했습니다. 다시 시도해주세요.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="signup-container">
            <form className="signup-card glass" onSubmit={handleSignup}>
                <h2>Sign Up</h2>
                <p className="form-subtitle">폴리텍 AI융합소프트웨어과 포트폴리오 관리를 시작하세요.</p>

                {error && <div className="error-message">{error}</div>}
                {message && <div className="success-message">{message}</div>}

                <div className="input-field">
                    <label>Email (@office.kopo.ac.kr)</label>
                    <div className="email-input-group">
                        <input
                            type="email"
                            name="email"
                            placeholder="example@office.kopo.ac.kr"
                            value={formData.email}
                            onChange={handleInputChange}
                            required
                        />
                        <button
                            type="button"
                            className="send-code-btn"
                            onClick={sendVerificationCode}
                            disabled={loading || isEmailSent}
                        >
                            {isEmailSent ? 'Resend' : 'Send Code'}
                        </button>
                    </div>
                </div>

                {isEmailSent && (
                    <div className="input-field">
                        <label>Verification Code</label>
                        <input
                            type="text"
                            name="verificationCode"
                            placeholder="000000"
                            value={formData.verificationCode}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                )}

                <div className="form-row">
                    <div className="input-field">
                        <label>Student ID (10 digits)</label>
                        <input
                            type="text"
                            name="studentId"
                            placeholder="2024010101"
                            value={formData.studentId}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div className="input-field">
                        <label>Full Name</label>
                        <input
                            type="text"
                            name="name"
                            placeholder="홍길동"
                            value={formData.name}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                </div>

                <div className="input-field">
                    <label>Password (min 8 characters)</label>
                    <input
                        type="password"
                        name="password"
                        placeholder="••••••••"
                        value={formData.password}
                        onChange={handleInputChange}
                        required
                        autoComplete="new-password"
                    />
                </div>

                <button type="submit" className="signup-btn primary" disabled={loading || !isEmailSent}>
                    {loading ? 'Creating account...' : 'Create Account'}
                </button>

                <p className="login-link">
                    Already have an account? <Link to="/login">Sign In</Link>
                </p>
            </form>

            <style>{`
        .signup-container {
          display: flex;
          justify-content: center;
          align-items: center;
          margin: 40px 0;
        }
        .signup-card {
          width: 500px;
          padding: 40px;
          border-radius: 16px;
        }
        .signup-card h2 {
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
        .email-input-group {
          display: flex;
          gap: 10px;
        }
        .email-input-group input {
          flex: 1;
        }
        .send-code-btn {
          padding: 8px 16px;
          border-radius: 8px;
          border: 1px solid var(--border);
          background: rgba(255, 255, 255, 0.05);
          color: white;
          white-space: nowrap;
          font-size: 0.85rem;
          font-weight: 600;
        }
        .form-row {
          display: flex;
          gap: 20px;
        }
        .form-row .input-field {
          flex: 1;
        }
        .signup-btn {
          width: 100%;
          margin-top: 24px;
          padding: 14px;
          border-radius: 8px;
          border: none;
          background: var(--primary);
          color: white;
          font-weight: 700;
          font-size: 1rem;
        }
        .signup-btn:disabled {
          opacity: 0.5;
          cursor: not-allowed;
        }
        .error-message {
          padding: 12px;
          background: rgba(239, 68, 68, 0.1);
          color: var(--error);
          border-radius: 8px;
          margin-bottom: 24px;
          font-size: 0.9rem;
        }
        .success-message {
          padding: 12px;
          background: rgba(16, 185, 129, 0.1);
          color: var(--success);
          border-radius: 8px;
          margin-bottom: 24px;
          font-size: 0.9rem;
        }
        .login-link {
          margin-top: 24px;
          font-size: 0.9rem;
          color: var(--text-muted);
        }
        .login-link a {
          color: var(--primary);
          text-decoration: none;
        }
      `}</style>
        </div>
    );
};

export default Signup;
