# Distributed Order Lab

Spring Boot mikroservisleriyle ürün, stok ve sipariş akışını gösteren örnek proje.

## Servisler

| Servis | Port | Sorumluluk |
| --- | ---: | --- |
| Product Service | 8083 | Ürün kataloğu |
| Inventory Service | 8082 | Stok sahipliği ve rezervasyon |
| Order Service | 8081 | Sipariş oluşturma ve stok rezervasyonu |

Her servis kendi PostgreSQL veritabanına sahiptir. Order Service, stok rezervasyonu için Inventory Service ile REST üzerinden iletişim kurar.

## Docker Compose ile çalıştırma

```bash
docker compose up --build
```

Servislerin durumunu kontrol etmek için:

```bash
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health
```

## Swagger UI

API'leri tarayıcı üzerinden incelemek ve çalıştırmak için:

- Order Service: <http://localhost:8081/swagger-ui.html>
- Inventory Service: <http://localhost:8082/swagger-ui.html>
- Product Service: <http://localhost:8083/swagger-ui.html>

OpenAPI JSON sözleşmeleri sırasıyla `http://localhost:8081/v3/api-docs`,
`http://localhost:8082/v3/api-docs` ve `http://localhost:8083/v3/api-docs`
adreslerinden alınabilir.

Sistemi durdurmak için:

```bash
docker compose down
```

Kalıcı veritabanı volume'larını da kaldırmak isterseniz `docker compose down --volumes` kullanın.
