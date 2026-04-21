# Coupons API

Project for coupons management. 
System allows creating coupons and registering usage by user.

## API

POST /coupons/create
```json
{
  "code": "coupon1",
  "maxUsages": 5,
  "country": "PL"
}
```

POST /coupons/redeem
```json
{
  "userId": "user1",
  "code": "coupon1"
}
```


## Running app
```bash
docker-compose up
./gradlew bootRun
```
## Running tests
```bash
./gradlew test
```

## Tech stack
- Kotlin
- Spring Boot
- PostgreSQL
- Testcontainers
- Flyway

## Notes
- Project has hexagonal-like architecture 
- Consistency is handled on the database layer (atomic update, uniqueness)
- SpringJDBC used for full control on SQL and data flow
- Most tests are integration tests with testcontainers and PostgreSQL to test all layers together
- [api.country.is](https://api.country.is) used as an external service for IP → COUNTRY
- Simple caching added on top of external service


## Improvements
- Improve resilient for external service 
- Add better concurrent users tests
- Add external service tests
- Introduce test with slow responses from DB and external API
- Configure code coverage 
- Improve logging