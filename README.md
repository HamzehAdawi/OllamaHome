# OllamaHome

**OllamaHome** is a web application that allows you to interface locally hosted Ollama LLM's. Equip your home with a clean local web app cross their home network with a clean and simple UI to interface. 

## Features
- Menu to interface locally run ollama models  
- Resume adjustment / enhancement 
- Real-time chat with persistent saves over any home device  
- Simple to setup and interchange or download ollama models 
- Dynamic configuration of UI menu properties
- Dynamically change models while the application is running (no restart required)

## Demo

<img width="1200" height="563" alt="image" src="https://github.com/user-attachments/assets/1905b4a8-96b5-4778-9e2b-c1c579d45604" />

## Getting Started

### Prerequisites

- Java 17 or higher  
- Maven or Gradle build tool  
- Ollama installed locally (see instructions below)  
- Ollama models downloaded and available locally  
- LibreOffice (required for resume analyzer)

### Installing Ollama and Models

1. Download and install Ollama from the official website (LOCALLY hosted):  
https://ollama.com  

2. Verify installation by running in your terminal/command prompt:

ollama version

3. To list available models, run:

ollama list

4. To pull/download a model, run:

ollama pull <model-name>

Example:

ollama pull llama3

5. Make sure Ollama’s local API is running on default port 11434. Usually it runs automatically after installation. You can check with:

ollama status

### Installing LibreOffice (Required for Resume Analyzer)

LibreOffice is used by the resume analyzer to process and convert resume files such as DOC / DOCX into text that can be analyzed by the model.

1. Download LibreOffice from the official site:  
https://www.libreoffice.org/download/download-libreoffice/

2. Run the installer and follow the default installation steps.

3. After installation, verify LibreOffice is installed by running:

soffice --version

4. If the command is not recognized, ensure the LibreOffice program directory is available on your system path.

Typical Windows installation path:

C:\Program Files\LibreOffice\program\soffice.exe

Once installed, the resume analyzer will automatically use LibreOffice to handle document conversions when processing resumes.

### Running the App

1. Clone the repository:

git clone https://github.com/HamzehAdawi/OllamaUI.git
cd OllamaUI

2. Build and run with Maven:

mvn spring-boot:run

3. Open your browser at http://localhost:8080 to start chatting.

---

## Changing the Ollama Model

OllamaUI lets you switch between different Ollama LLM models easily without changing any code.

You can either change the default model in the configuration file or dynamically switch models directly from the UI while the application is running.

Option 1 — Change the default model in configuration

1. Open the src/main/resources/application.properties file.

2. Locate the property:

spring.ai.ollama.chat.model=llama3

3. Change the value (llama3) to any supported Ollama model you want to use, for example:

spring.ai.ollama.chat.model=llama2

4. Save the file.

5. Restart the Spring Boot application to apply the new default model:

mvn spring-boot:run

Option 2 — Change the model from the UI

Use the model dropdown in the application interface to dynamically switch models while the application is running. The change is applied immediately to new prompts.

---

## Project Structure

- controllers/ — Spring MVC controllers handling web requests  
- services/ — Business logic and Ollama API integration  
- config/ — Configuration classes (e.g., model property injection)  
- entities/ — Data entities like messages  
- resources/templates/ — Thymeleaf HTML templates  
- resources/static/ — Static assets (CSS, images)  

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## Contact

Hamzeh Adawi —  
Enjoy chatting with Ollama! 🤖
