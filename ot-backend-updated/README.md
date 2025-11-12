# ot-backend (updated)

Spring Boot backend for OT Management (PostgreSQL). Ready for Render + Neon.

New features in this updated version:
- PUT /api/bookings/{id} to reschedule bookings with conflict detection.
- Structured 409 Conflict responses: { error: string, conflicts: [{id,start,end}, ...] }
- Global exception handler with JSON responses.

Quick start (locally):
1. Configure a PostgreSQL and set env vars:
   - DATABASE_URL=jdbc:postgresql://host:port/dbname
   - DB_USER=user
   - DB_PASS=pass
2. Build:
   ./mvnw clean package
3. Run:
   java -jar target/ot-backend-1.0.0.jar
