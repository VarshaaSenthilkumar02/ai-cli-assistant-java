# AI CLI Assistant (Java)

A simple terminal-based AI assistant built using Java and Ollama.

This project was created to learn:
- AI integration using APIs
- Java backend fundamentals
- JSON parsing
- HTTP communication
- Conversation memory handling
- Clean project structure

---

# Features

- Continuous AI chat in terminal
- Conversation memory
- Timestamped messages
- Custom commands
- Exception handling
- Local LLM integration using Ollama
- Clean service-layer architecture

---

# Tech Stack

- Java 21
- Maven
- Jackson Databind
- Ollama
- Llama3

---

# Commands

| Command    | Description               |
| ---------- | ------------------------- |
| `/help`    | Show available commands   |
| `/history` | Show chat history         |
| `/clear`   | Clear conversation memory |
| `/exit`    | Stop assistant            |

---

# How it Works

The Java application sends HTTP requests to the locally running Ollama server.
```text
Java Application
↓
HTTP Request
↓
Ollama Server
↓
Llama3 Model
↓
AI Response

```

---

# Project Structure

```text
src/main/java/com/varshaa/aiassistant
│
├── Main.java
│
└── service
    └── OllamaService.java
```

# SetUp Instructions

# 1.Install Ollama

- Install Ollama from: 
```text 
https://ollama.com 
```

# 2.Pull llama3

```text 
ollama run llama3 
```

# 3.Clone Repository

- Install Ollama from:
```text 
git clone <your-repository-url>
```

# 4.Run the Project

```text 
AiAssistance.java
```

# Example

```text
[10:45 PM] You : My name is Varshaa

[10:45 PM] AI :
Nice to meet you, Varshaa!

[10:46 PM] You : What is my name?

[10:46 PM] AI :
Your name is Varshaa.
```

# Author
- Varshaa 

