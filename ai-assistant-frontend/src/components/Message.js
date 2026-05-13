// src/components/Message.js
import React from 'react';
import ReactMarkdown from 'react-markdown';

const Message = ({ text, isUser }) => {
    return (
        <div className={`message-wrapper ${isUser ? 'user' : 'ai'}`}>
            <div className={`message-bubble ${isUser ? 'user-bubble' : 'ai-bubble'}`}>
                {isUser ? (
                    <p>{text}</p>
                ) : (
                    <ReactMarkdown>{text}</ReactMarkdown>
                )}
            </div>
        </div>
    );
};

export default Message;