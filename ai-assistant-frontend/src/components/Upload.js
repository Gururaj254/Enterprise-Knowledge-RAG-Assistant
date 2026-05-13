// src/components/Upload.js
import React, { useState } from 'react';
import { uploadDocument } from '../services/api';

const Upload = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [uploadStatus, setUploadStatus] = useState("");

    const handleFileChange = (e) => {
        setSelectedFile(e.target.files[0]);
        setUploadStatus("");
    };

    const handleUpload = async () => {
        if (!selectedFile) {
            setUploadStatus("Please select a file first.");
            return;
        }

        setUploadStatus("Uploading... ⏳");
        try {
            await uploadDocument(selectedFile);
            setUploadStatus("✅ Document loaded into AI Memory!");
            setSelectedFile(null); // Reset input
        } catch (error) {
            console.error(error);
            setUploadStatus("❌ Failed to upload document.");
        }
    };

    return (
        <div className="upload-container">
            <input 
                type="file" 
                accept=".pdf, .txt" 
                onChange={handleFileChange} 
                className="file-input"
            />
            <button onClick={handleUpload} className="upload-button">
                Upload to AI
            </button>
            {uploadStatus && <span className="status-text">{uploadStatus}</span>}
        </div>
    );
};

export default Upload;