# 📚 ResearcherPro – Your AI Research Assistant Chrome Extension

**ResearcherPro** is a powerful, AI-enhanced Chrome Extension built to streamline your academic and research workflow. Whether you're writing essays, reviewing papers, or managing citations, ResearcherPro gives you the tools to summarize content, generate citations instantly, and take notes – all in one place.

---

## ✨ Features

### 🔍 AI-Powered Text Summarization (via Gemini API)
- **Brief or Detailed Summaries** – Summarize selected text using cutting-edge AI.
- Toggle between **concise** overviews or **in-depth** breakdowns depending on your research needs.

### 📝 Instant Citation Generator
- Supports all major citation styles:
  - **APA**
  - **MLA**
  - **IEEE**
  - **Chicago**
- Input your source and get formatted citations instantly – no need to leave the page!

### 🗒️ Note Taking & Storage
- Built-in note editor to **jot down, save, and retrieve** your research notes.
- Uses Chrome’s local storage for fast and secure access – even after browser restarts.

---

## 🧠 Tech Stack

- **JavaScript** 
- **HTML + CSS**
- **Java SpringBoot**
- **Chrome Extension APIs**
- **Gemini AI API (via Google Generative AI)**
- **Chrome Storage API**

---
---
---
# 🚀 Release v1.6.0  

This release introduces a **new RabbitMQ queue** to improve data flow between the main application and its supporting microservice.  

---

## ✨ New Features  

- 📨 **RabbitMQ Queue Integration**  
  - Added a dedicated queue to capture **user request usage data**.  
  - Data is forwarded to the microservice for later **analysis and insights**.  

- 📊 **Enhanced Data Pipeline**  
  - Improved structure for collecting, transferring, and processing usage metrics.  
  - Provides a foundation for **analytics and monitoring capabilities** in upcoming versions.  

---

## 🛠️ Improvements  

- Better separation of concerns between the **main app** and the **analytics microservice**.  
- Cleaner data flow design to support **future scalability**.  

---

The GitHub for the microservice: https://github.com/dakshp13/ResearcherPro-Microservice1


## Credits for Help & Useful Tutorials
- Majority of this project was developed by me, however throughout the course of the project including to start the development many people helped
- EmbarkX Programming: https://www.youtube.com/@EmbarkX
- Programming Techie: https://www.youtube.com/@ProgrammingTechie
- Daily Code Buffer: https://www.youtube.com/@DailyCodeBuffer


