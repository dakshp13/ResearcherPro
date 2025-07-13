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
# 🚀 v1.5.0 - Redis-Powered Performance Boost!

## ✨ New Features & Improvements

- ⚡ **Redis Cache Integration (via Docker)**  
  Added a Redis caching layer for the `/api/research/getstats` endpoint to dramatically improve response time and reduce database load.

- 🧪 **Docker Testcontainers for Redis**  
  Implemented test infrastructure using Docker-based Redis to ensure isolated and consistent testing environments.

- ⏱️ **42.6x Faster Access**  
  Benchmarked cache access to be **42.6x faster** than direct MongoDB queries for repeated requests!

- 🧠 **Smarter Backend Logic**  
  Now uses `@Cacheable`, `@CacheEvict`, and fine-tuned cache configurations via `RedisCacheManager`.

- 🔁 **Seamless Cache Refresh**  
  When stats are updated or deleted, the cache is properly invalidated to keep everything in sync.
  
---

> 💡 This release lays the foundation for future performance optimizations and scalable caching mechanisms across more endpoints!

---
🔧 Full functionality is available in the updated side panel and stats view pages.


