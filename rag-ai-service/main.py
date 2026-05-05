from fastapi import FastAPI, UploadFile, File
from pydantic import BaseModel
import uvicorn
import os
import io
import PyPDF2
from dotenv import load_dotenv
from langchain_google_genai import ChatGoogleGenerativeAI

# 1. Load the secret API key
load_dotenv()

app = FastAPI()


class ChatRequest(BaseModel):
    question: str

# 2. Initialize LangChain
llm = ChatGoogleGenerativeAI(model="gemini-flash-latest") 

# 3. Memory storage for our uploaded document
document_context = ""

# --- NEW: FILE UPLOAD ENDPOINT ---
@app.post("/api/ai/upload")
async def upload_file(file: UploadFile = File(...)):
    global document_context
    try:
        print(f"Receiving file: {file.filename}")
        
        # If it's a PDF, extract the text page by page
        if file.filename.endswith('.pdf'):
            pdf_reader = PyPDF2.PdfReader(io.BytesIO(await file.read()))
            text = ""
            for page in pdf_reader.pages:
                text += page.extract_text() + "\n"
            document_context = text
            
        # If it's a simple text file, just decode it
        elif file.filename.endswith('.txt'):
            document_context = (await file.read()).decode('utf-8')
            
        else:
            return {"message": "Unsupported file format. Please upload PDF or TXT."}

        return {"message": f"Successfully read '{file.filename}'. The AI is now ready to answer questions about it!"}
        
    except Exception as e:
        print(f"Upload Error: {e}")
        return {"message": f"Failed to process file: {str(e)}"}


# --- UPDATED: CHAT ENDPOINT ---
@app.post("/api/ai/chat")
async def chat(request: ChatRequest):
    global document_context
    try:
        user_question = request.question
        print(f"Asking LangChain: {user_question}")
        
        # 4. RAG MAGIC: If we have a document loaded, combine it with the user's question!
        if document_context != "":
            print("Using document context to answer...")
            final_prompt = f"Use the following document context to answer the user's question. \n\nDocument Context:\n{document_context}\n\nQuestion: {user_question}"
        else:
            final_prompt = user_question
            
        # Get response from LangChain
        ai_response = llm.invoke(final_prompt)
        raw_answer = ai_response.content
        
        # Extract string safely (Fixes the ArrayList Java crash)
        if isinstance(raw_answer, list):
            final_text = raw_answer[0].get("text", str(raw_answer[0])) if isinstance(raw_answer[0], dict) else str(raw_answer[0])
        else:
            final_text = str(raw_answer)
            
        return {"answer": final_text}
        
    except Exception as e:
        print(f"LangChain Error: {e}")
        return {"answer": "Sorry, my LangChain AI encountered an error."}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)