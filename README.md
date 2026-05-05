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

```mermaid
    graph TD
    classDef frontend fill:#20232a,stroke:#61dafb,stroke-width:2px,color:#61dafb;
    classDef java fill:#f89820,stroke:#5382a1,stroke-width:2px,color:#fff;
    classDef python fill:#306998,stroke:#ffe873,stroke-width:2px,color:#fff;
    classDef db fill:#00758f,stroke:#f29111,stroke-width:2px,color:#fff;
    classDef ai fill:#ea4335,stroke:#4285f4,stroke-width:2px,color:#fff;

    subgraph Client [React Frontend]
        UI[User Interface]:::frontend
        API_SVC[API Service]:::frontend
    end

    subgraph SpringBoot [Spring Boot Gateway]
        REST_CHAT[Chat Controller]:::java
        REST_DOC[Document Controller]:::java
        JPA[Spring Data JPA]:::java
    end

    subgraph Database [MySQL Layer]
        MySQL[(chat_interactions)]:::db
    end

    subgraph FastAPI [Python AI Engine]
        ROUTE_CHAT[Chat Endpoint]:::python
        ROUTE_UP[Upload Endpoint]:::python
        SPLITTER[Text Splitter]:::python
        EMBED[Embeddings Model]:::python
        CHROMA[(Chroma Vector DB)]:::db
        LANGCHAIN[LangChain]:::python
    end

    subgraph External [Google Cloud]
        GEMINI[Gemini 2.5 Flash]:::ai
    end

    UI --> API_SVC
    
    API_SVC -->|Prompt| REST_CHAT
    API_SVC -->|File| REST_DOC
    
    REST_CHAT --> JPA
    JPA -->|Save & Load| MySQL
    
    REST_DOC -->|Forward File| ROUTE_UP
    REST_CHAT -->|Forward Prompt| ROUTE_CHAT
    
    ROUTE_UP --> SPLITTER
    SPLITTER --> EMBED
    EMBED --> CHROMA
    
    ROUTE_CHAT --> LANGCHAIN
    LANGCHAIN -->|Semantic Search| CHROMA
    LANGCHAIN -->|Prompt + Context| GEMINI

    subgraph External [Google Cloud]
        GEMINI[Gemini 2.5 Flash]:::ai
    end

    API_SVC -->|"User Prompt"| REST_CHAT
    API_SVC -->|"File Upload"| REST_DOC
    
    JPA -->|"Save Data"| MySQL
    MySQL -->|"Load Data"| JPA
    
    REST_DOC -->|"File Payload"| ROUTE_UP
    REST_CHAT -->|"Forward Prompt"| ROUTE_CHAT
    
    LANGCHAIN -->|"Context Input"| GEMINI
    GEMINI -->|"AI Response"| LANGCHAIN🚀 How It Works
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
