import google.generativeai as genai
import os
from dotenv import load_dotenv

# Load your API key
load_dotenv()
api_key = os.getenv("GOOGLE_API_KEY")
genai.configure(api_key=api_key)

print("Fetching allowed models for your API key...\n")

# Ask Google for the list of models you have access to
try:
    for m in genai.list_models():
        if 'generateContent' in m.supported_generation_methods:
            print(f"✅ YOU CAN USE: {m.name}")
except Exception as e:
    print(f"Error fetching models: {e}")