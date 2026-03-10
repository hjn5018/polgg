import React, { useState, useEffect, useRef } from 'react';
import { MessageSquare, Send, X, Bot, User, RefreshCw } from 'lucide-react';
import api from '../../api/axios';

const AIChat = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [messages, setMessages] = useState([
        { role: 'bot', content: '안녕하세요! POL.GG 취업 컨설턴트입니다. 포트폴리오 상담이나 취업 조언이 필요하신가요?' }
    ]);
    const [input, setInput] = useState('');
    const [loading, setLoading] = useState(false);
    const messagesEndRef = useRef(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    useEffect(() => {
        if (isOpen) scrollToBottom();
    }, [messages, isOpen]);

    const handleSend = async () => {
        if (!input.trim() || loading) return;

        const userMessage = { role: 'user', content: input };
        setMessages(prev => [...prev, userMessage]);
        setInput('');
        setLoading(true);

        try {
            const response = await api.get(`/ai/chat?query=${encodeURIComponent(input)}`);
            const botMessage = { role: 'bot', content: response.data.answer };
            setMessages(prev => [...prev, botMessage]);
        } catch (error) {
            console.error('Chat error:', error);
            setMessages(prev => [...prev, { role: 'bot', content: '죄송합니다. 서버와 연결이 원활하지 않습니다.' }]);
        } finally {
            setLoading(false);
        }
    };

    const handleTrain = async () => {
        if (!window.confirm('RAG 데이터를 재학습하시겠습니까? (상당한 시간이 소요될 수 있습니다)')) return;
        setLoading(true);
        try {
            await api.post('/ai/train');
            alert('학습이 완료되었습니다.');
        } catch (error) {
            alert('학습 실패: ' + (error.response?.data?.message || error.message));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="ai-chat-container">
            {!isOpen && (
                <button className="chat-toggle primary glass" onClick={() => setIsOpen(true)}>
                    <Bot size={28} />
                </button>
            )}

            {isOpen && (
                <div className="chat-window-wrapper glass">
                    <header className="chat-header">
                        <div className="header-info">
                            <Bot size={20} className="bot-icon" />
                            <h3>AI 취업 컨설턴트</h3>
                        </div>
                        <div className="header-actions">
                            <button className="icon-btn" title="데이터 최신화 (RAG 학습)" onClick={handleTrain} disabled={loading}>
                                <RefreshCw size={16} />
                            </button>
                            <button className="icon-btn" onClick={() => setIsOpen(false)}>
                                <X size={20} />
                            </button>
                        </div>
                    </header>

                    <main className="chat-messages">
                        {messages.map((msg, idx) => (
                            <div key={idx} className={`message-bubble ${msg.role}`}>
                                <div className="avatar">
                                    {msg.role === 'bot' ? <Bot size={16} /> : <User size={16} />}
                                </div>
                                <div className="content">
                                    {msg.content}
                                </div>
                            </div>
                        ))}
                        {loading && (
                            <div className="message-bubble bot typing">
                                <div className="avatar"><Bot size={16} /></div>
                                <div className="content">생각 중...</div>
                            </div>
                        )}
                        <div ref={messagesEndRef} />
                    </main>

                    <footer className="chat-input-area">
                        <input
                            type="text"
                            placeholder="질문을 입력하세요..."
                            value={input}
                            onChange={(e) => setInput(e.target.value)}
                            onKeyPress={(e) => e.key === 'Enter' && handleSend()}
                            disabled={loading}
                        />
                        <button className="send-btn" onClick={handleSend} disabled={loading || !input.trim()}>
                            <Send size={20} />
                        </button>
                    </footer>
                </div>
            )}

            <style>{`
        .ai-chat-container {
          position: fixed;
          bottom: 30px;
          right: 30px;
          z-index: 1000;
        }
        .chat-toggle {
          width: 60px;
          height: 60px;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          color: white;
          box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3);
          border: 1px solid rgba(255, 255, 255, 0.2);
          background: var(--primary);
        }
        .chat-window-wrapper {
          width: 380px;
          height: 550px;
          border-radius: 20px;
          overflow: hidden;
          display: flex;
          flex-direction: column;
          box-shadow: 0 20px 50px rgba(0, 0, 0, 0.5);
          border: 1px solid rgba(255, 255, 255, 0.1);
          animation: slideUp 0.3s ease;
          background: var(--bg-card);
        }
        @keyframes slideUp {
          from { transform: translateY(20px); opacity: 0; }
          to { transform: translateY(0); opacity: 1; }
        }
        .chat-header {
          padding: 16px 20px;
          background: var(--primary);
          color: white;
          display: flex;
          justify-content: space-between;
          align-items: center;
        }
        .header-info {
          display: flex;
          align-items: center;
          gap: 8px;
        }
        .header-info h3 {
          font-size: 16px;
          font-weight: 600;
        }
        .header-actions {
          display: flex;
          gap: 8px;
        }
        .icon-btn {
          background: none;
          border: none;
          color: rgba(255, 255, 255, 0.8);
          border-radius: 4px;
          padding: 4px;
          display: flex;
          align-items: center;
        }
        .icon-btn:hover {
          background: rgba(255, 255, 255, 0.1);
          color: white;
        }
        .chat-messages {
          flex: 1;
          overflow-y: auto;
          padding: 20px;
          display: flex;
          flex-direction: column;
          gap: 16px;
        }
        .message-bubble {
          display: flex;
          gap: 12px;
          max-width: 85%;
        }
        .message-bubble.user {
          flex-direction: row-reverse;
          align-self: flex-end;
        }
        .avatar {
          width: 30px;
          height: 30px;
          border-radius: 50%;
          background: var(--bg-card);
          border: 1px solid var(--border);
          display: flex;
          align-items: center;
          justify-content: center;
          color: var(--text-muted);
          flex-shrink: 0;
        }
        .user .avatar {
          background: var(--primary);
          color: white;
        }
        .content {
          padding: 10px 14px;
          border-radius: 12px;
          font-size: 14px;
          white-space: pre-wrap;
          line-height: 1.5;
        }
        .bot .content {
          background: rgba(255, 255, 255, 0.05);
          color: var(--text-main);
          border-bottom-left-radius: 2px;
        }
        .user .content {
          background: var(--primary);
          color: white;
          border-bottom-right-radius: 2px;
        }
        .typing .content {
          color: var(--text-muted);
          font-style: italic;
        }
        .chat-input-area {
          padding: 16px;
          background: rgba(15, 23, 42, 0.5);
          display: flex;
          gap: 10px;
          border-top: 1px solid var(--border);
        }
        .chat-input-area input {
          flex: 1;
          padding: 10px 16px;
          font-size: 14px;
          border-radius: 25px;
          background: var(--bg-card);
          color: white;
          border: 1px solid var(--border);
        }
        .send-btn {
          background: var(--primary);
          color: white;
          border: none;
          width: 40px;
          height: 40px;
          border-radius: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        .send-btn:disabled {
          background: var(--border);
          opacity: 0.5;
        }
      `}</style>
        </div>
    );
};

export default AIChat;
