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
