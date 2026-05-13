// src/components/Chat.js
import React, { useState, useEffect, useRef } from 'react';
import Message from './Message';
import Upload from './Upload';
import { sendChatMessage, fetchChatHistory, clearChatHistory } from '../services/api';

const Chat = () => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const messagesEndRef = useRef(null);

    // Load history on startup
    useEffect(() => {
        const loadHistory = async () => {
            try {
                const history = await fetchChatHistory();
                const formattedHistory = [];
                history.forEach(interaction => {
                    formattedHistory.push({ text: interaction.userPrompt, isUser: true });
                    formattedHistory.push({ text: interaction.aiResponse, isUser: false });
                });
                setMessages(formattedHistory);
            } catch (error) {
                console.error("Failed to load history:", error);
            }
        };
        loadHistory();
    }, []);

    // Auto-scroll to bottom
    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    // Handle sending a new message
    const handleSend = async () => {
        if (!input.trim()) return;

        const userText = input;
        setInput(""); // clear input early for better UX
        setMessages(prev => [...prev, { text: userText, isUser: true }]);
        setIsLoading(true);

        try {
            const savedInteraction = await sendChatMessage(userText);
            setMessages(prev => [...prev, { text: savedInteraction.aiResponse, isUser: false }]);
        } catch (error) {
            setMessages(prev => [...prev, { text: "⚠️ Error connecting to server.", isUser: false }]);
        } finally {
            setIsLoading(false);
        }
    };

    // --- NEW: Handle clearing the chat history ---
    const handleClear = async () => {
        if (window.confirm("Are you sure you want to clear the entire chat history?")) {
            try {
                await clearChatHistory(); // Tells Spring Boot to delete from MySQL
                setMessages([]);          // Wipes the React screen instantly
            } catch (error) {
                console.error("Failed to clear chat:", error);
            }
        }
    };

    return (
        <div className="chat-container">
            {/* UPDATED HEADER WITH CLEAR BUTTON */}
            <div className="header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '15px 20px' }}>
                <h2 style={{ margin: 0 }}>🤖 AI Knowledge Assistant</h2>
                <button 
                    onClick={handleClear} 
                    style={{ backgroundColor: '#ff4444', color: 'white', border: 'none', padding: '8px 15px', borderRadius: '6px', cursor: 'pointer', fontWeight: 'bold', fontSize: '0.9rem' }}
                >
                    🗑️ Clear Chat
                </button>
            </div>
            
            <Upload />

            <div className="messages-window">
                {messages.map((msg, index) => (
                    <Message key={index} text={msg.text} isUser={msg.isUser} />
                ))}
                {isLoading && <div className="loading-indicator">AI is thinking...</div>}
                <div ref={messagesEndRef} />
            </div>

            <div className="input-area">
                <input 
                    type="text" 
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleSend()}
                    placeholder="Ask a question..."
                />
                <button onClick={handleSend} disabled={isLoading}>Send</button>
            </div>
        </div>
    );
};

export default Chat;