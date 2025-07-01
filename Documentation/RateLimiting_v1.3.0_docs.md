## 🔒 Rate Limiting Introduced (v1.3.0)

To improve backend stability and prevent abuse, **rate limiting** has been added to all endpoints.

### 🛡️ Key Details
- Implemented using **Bucket4j** for in-memory token bucket rate limiting
- Limit: **certain request per minute** per client
- Returns HTTP status **429** (Too Many Requests) if limit is exceeded
- Helps ensure fair usage and protects system from spam usage
- Rate Limiting is fully tested using API Mocks as well


