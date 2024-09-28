# Charitable-donation-system-APIs
Hệ thống quyên góp từ thiện cho cơ sở giáo dục ĐH

## Technologies Used
- **Spring Boot** (v2.x)
- **JPA** (Hibernate)
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
│   │       └── application.properties     # Spring Boot configuration file
├── pom.xml                                 # Maven configuration file (dependencies, plugins, etc.)
└── README.md                               # Project documentation
