## 📦 Version 1.3.0 – Feature: Usage Statistics Page

### ✨ New
- **Usage Stats Page** (`stats.html`): Added a new page to the extension UI accessible via a button on the home screen.
- Users can now view:
  - Total number of times they've used each core feature (e.g., summarization, suggestion, citation).
  - The **last accessed timestamp** for each feature.

### 🔧 Backend
- Added a new `GET` endpoint at `/api/research/getstats` to serve usage statistics from MongoDB.
- Introduced `StatsService` in the Spring Boot backend to retrieve and format usage data.

### 🎯 Purpose
This new feature enhances transparency and user awareness by allowing individuals to track how often and recently they engage with various research assistant tools.

---

