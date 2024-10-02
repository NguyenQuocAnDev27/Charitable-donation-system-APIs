# Charitable-donation-system-APIs
Hệ thống quyên góp từ thiện cho cơ sở giáo dục ĐH

## Tổng quan
DonationInUniversity là một đồ án Spring Boot được xây dựng cùng JPA and MySQL. Hệ thống quản lý các dự án quyên góp, vai trò của người dùng và cho phép người dùng quyên góp thông qua nhiều phương thức thanh toán. Dự án cũng hỗ trợ gắn tags dự án, báo cáo và thông báo cho người dùng.

## Technologies Used
- **Spring Boot** (v3.x)
- **JWT-Token** (for security, session management)
- **JPA** (Java Persistence API)
- **MySQL** (Database)
- **Jakarta EE** (JPA, Validation, etc.)
- **SpringDoc OpenAPI** (for API documentation)
- **Maven** (Dependency Management)

## Folder Structure

```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── DonationInUniversity
│   │   │               ├── config         # Configuration classes (property readers)
│   │   │               ├── cache          # Caching mechanism classes
│   │   │               ├── constants      # Constant defined classes
│   │   │               ├── controller
│   │   │               │   ├── admin      # Controllers for web admin/dashboard
│   │   │               │   └── api        # REST APIs for backend services
│   │   │               ├── exception      # Custom exception classes
│   │   │               ├── model          # JPA entities representing DB tables
│   │   │               ├── repository     # Data access classes for interacting with DB
│   │   │               ├── service        # Business logic implementation (service layer)
│   │   │               ├── utils          # Utility classes
│   │   │               ├── validation     # Custom validation classes
│   │   │               └── security       # Security configuration (authentication, authorization)
│   │   └── resources
│   │       └── application.properties     # Basic configuration file
│   │       └── env.properties             # Local configuration file
├── pom.xml                                # Maven configuration file (dependencies, plugins, etc.)
└── README.md                              # Project documentation
