# 🤖 Enterprise Knowledge RAG Assistant

A scalable, full-stack Retrieval-Augmented Generation (RAG) AI platform. This application allows users to upload massive documents (PDFs, TXT) and instantly query them using Semantic Search, all orchestrated through a multi-tier microservice architecture.

## ✨ Key Features
* **Enterprise RAG Pipeline:** Intelligent document chunking and vector embedding via LangChain and ChromaDB.
* **Semantic Search:** Instantaneously retrieves highly relevant context from large documents to eliminate AI hallucinations.
* **Microservice Architecture:** Complete separation of concerns between the user interface, business logic, and the AI processing engine.
* **Persistent Memory:** Complete chat interaction history securely saved and retrieved from a relational database.

## 🛠️ Technology Stack
* **Frontend UI:** React.js, CSS3, REST Fetch API
* **Backend Gateway:** Java, Spring Boot, Spring Web
* **Database:** MySQL, Hibernate / Spring Data JPA
* **AI Microservice:** Python, FastAPI, Uvicorn
* **LLM & Orchestration:** Google Gemini 2.5 Flash, LangChain, Chroma Vector Database

## 🏗️ System Architecture
[ React Frontend UI ]
         |
         | (Sends Prompt & File)
         v
[ Spring Boot Gateway ]  <------>  [ MySQL Database ]
         |
         | (Forwards Payload)
         v
[ Python FastAPI Engine ] <----->  [ Chroma Vector DB ]
         |
         | (Sends Context)
         v
[ Google Gemini 2.5 LLM ]

🚀 How It Works
The Bridge: The React frontend securely passes user prompts and uploaded documents to the Spring Boot backend.

Persistence: Spring Boot logs the transaction into MySQL for historical record-keeping and forwards the payload to the Python microservice.

Vectorization: The Python engine uses LangChain's RecursiveCharacterTextSplitter to chunk the document, converts the text into mathematical embeddings, and loads them into ChromaDB.

Retrieval: When a question is asked, Python queries ChromaDB to find the 3 most relevant chunks of data.

Generation: The context is appended to a strict system prompt and sent to Google Gemini to generate a highly accurate, context-aware response.

💻 Local Setup & Installation
Prerequisites
Node.js & npm

Java 17+ & Maven

Python 3.9+

MySQL Server

1. Database Setup
Create a new MySQL database named ai_assistant_db. Update your application.properties in the Spring Boot project with your MySQL username and password.

2. AI Microservice (Python)
Navigate to the Python directory and install the required enterprise libraries:

Bash
pip install fastapi uvicorn pydantic python-multipart PyPDF2 langchain-google-genai langchain-chroma langchain-text-splitters python-dotenv
Create a .env file and add your API key: GOOGLE_API_KEY=your_key_here.
Run the server:

Bash
python main.py
3. Backend Gateway (Spring Boot)
Run the Spring Boot application from your IDE or via Maven:

Bash
mvn spring-boot:run
4. Frontend UI (React)
Navigate to the React directory and install dependencies:

Bash
npm install react-markdown
Start the development server:

Bash
npm start
👨‍💻 Author
Gururaj Dharmashetti

Full Stack AI Developer | Java | Spring Boot | React | Generative AI
