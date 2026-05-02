from fastapi import FastAPI, UploadFile, File
from pydantic import BaseModel
import uvicorn

app = FastAPI(title="InsightFlow AI Engine")

# Pydantic model to define the expected JSON structure for chat
class ChatRequest(BaseModel):
    question: str

@app.get("/")
def read_root():
    return {"status": "AI Service is running"}

@app.post("/api/ai/upload")
async def process_document(file: UploadFile = File(...)):
    # Later, LangChain will parse this PDF and store it in a Vector Database
    return {"message": f"Successfully received {file.filename} for AI processing."}

@app.post("/api/ai/chat")
async def generate_answer(request: ChatRequest):
    # Later, LangChain will search the Vector DB and call the LLM here
    ai_magic = f"LangChain received your question: '{request.question}'. Real AI response generation coming next!"
    return {"answer": ai_magic}

if __name__ == "__main__":
    # We run this on port 8000 so it doesn't conflict with React or Spring Boot
    uvicorn.run(app, host="0.0.0.0", port=8000)