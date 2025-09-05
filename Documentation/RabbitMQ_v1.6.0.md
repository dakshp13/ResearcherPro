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


