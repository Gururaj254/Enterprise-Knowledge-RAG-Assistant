const API_BASE_URL = "http://localhost:6003/api";

// 1. Send a message to the AI
export const sendChatMessage = async (prompt) => {
    const response = await fetch(`${API_BASE_URL}/chat/save`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ userPrompt: prompt }),
    });

    if (!response.ok) throw new Error("Failed to send message");
    return await response.json();
};

// 2. Load chat history from MySQL
export const fetchChatHistory = async () => {
    const response = await fetch(`${API_BASE_URL}/chat/history`);
    if (!response.ok) throw new Error("Failed to fetch history");
    return await response.json();
};

// 3. Upload a document for the AI to read
export const uploadDocument = async (file) => {
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch(`${API_BASE_URL}/documents/upload`, {
        method: "POST",
        body: formData, // fetch automatically sets the multipart/form-data header
    });

    if (!response.ok) throw new Error("Upload failed");
    return await response.text(); // Python returns a simple text message
};


// 4. Clear chat history from MySQL
export const clearChatHistory = async () => {
    const response = await fetch(`${API_BASE_URL}/chat/clear`, {
        method: "DELETE",
    });
    if (!response.ok) throw new Error("Failed to clear history");
    return await response.text();
};