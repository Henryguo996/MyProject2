# 電商網站後端專案 - MyProject2

本專案為使用 Spring Boot 建立的電商網站後端系統，主要實作會員註冊與登入、商品管理、購物車與訂單功能。資料庫使用 Railway 提供的 MySQL 雲端服務。專案採用三層架構（Controller、Service、Repository）並使用 Thymeleaf 作為前端模板引擎。

---

## 💡 專案說明

此專案為學習用途所建立，專注於後端邏輯設計與功能開發，目的是讓使用者能：
- 註冊並登入帳號
- 查看商品與搜尋
- 加入購物車
- 建立訂單

---

## 🔧 使用技術

- Java 17
- Spring Boot 3
- Spring MVC + Spring Data JDBC
- Thymeleaf
- MySQL（Railway 雲端資料庫）
- Swagger API 文件自動生成

---

## 📂 專案結構簡介

```bash
├── controller        # 控制器，負責接收請求
├── service           # 商業邏輯層，處理各種操作
├── repository        # 資料存取層，負責與資料庫互動
├── model             # Entity 類別
├── config            # 資源設定 (資料庫、Swagger)
└── application.properties
```

---


## ⚙️ 本地啟動教學

1. 下載專案
```bash
git clone https://github.com/Henryguo996/MyProject2.git
cd MyProject2
```

2. 設定資料庫連線資訊：
請修改 `src/main/resources/application.properties`，填入 Railway 提供的連線資訊：

```properties
spring.datasource.url=jdbc:mysql://mainline.proxy.rlwy.net:39986/railway?useSSL=false&serverTimezone=Asia/Taipei
spring.datasource.username=root
spring.datasource.password=（請至 Railway 控制台查看）
```

3. 匯入資料庫初始資料（若尚未建表）
```bash
mysql -h mainline.proxy.rlwy.net -P 39986 -u root -p railway < backup.sql
```

4. 啟動應用程式
```bash
./mvnw spring-boot:run
```

5. 瀏覽網站與 API 文件
```
網站首頁：http://localhost:8080
Swagger 文件：http://localhost:8080/swagger
```

---

## 📌 備註

- 若你尚未建立 Railway 的 MySQL，可前往 https://railway.app 建立並複製連線資訊
- Swagger 可視化 API 有助於測試各項功能

---

## 🙋‍♂️ 聯絡作者

- GitHub: [Henryguo996](https://github.com/Henryguo996)
- Email: yourmail@example.com

---

## License

MIT License
