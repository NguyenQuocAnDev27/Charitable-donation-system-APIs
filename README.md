# Charitable-donation-system-APIs
The system is a donation platform for charitable contributions to higher education institutions.

## Overview
DonationInUniversity is a Spring Boot project built with JPA and MySQL. The system manages donation projects, user roles, and allows users to donate through multiple payment methods. The project also supports tagging projects, generating reports, and sending notifications to users. (web services, microservices, multithread, multi-processing, file (image, pdf,...) processing)

## Technologies Used
- **Spring Boot** (version 3.3.3)
- **Oauth 2.0** (for security, session management)
- **MySQL 8.0** (Database)
- **Jakarta EE** (JPA, Validation, etc.)
- **SpringDoc OpenAPI** (for API documentation)
- **Google Cloud** (Google Account Login, Google Sheet View)
- **VietQR** (Payment)
- **Casso** (Transaction Monitor)

## Folder Structure

```bash
├── logs                                   # Save error logs file
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
│   │   │               │   ├── web        # Controllers for web
│   │   │               │   │   ├── admin         # Controllers for admin
│   │   │               │   │   ├── global        # Controllers for global functions
│   │   │               │   │   └── pm            # Controllers for project manager
│   │   │               │   └── api        # REST APIs for backend services
│   │   │               ├── exception      # Custom exception classes
│   │   │               ├── model          # JPA entities representing DB tables
│   │   │               ├── repository     # Data access classes for interacting with DB
│   │   │               ├── service        # Business logic implementation (service layer)
│   │   │               │   ├── admin      # Controllers for web admin/dashboard
│   │   │               │   ├── api        # REST APIs for backend services
│   │   │               │   ├── google     # Google service
│   │   │               │   └── schedule   # Schedule service
│   │   │               ├── utils          # Utility classes
│   │   │               ├── validation     # Custom validation classes
│   │   │               └── security       # Security configuration (authentication, authorization)
│   │   └── resources
│   │       ├── application.properties     # Basic configuration file
│   │       ├── env.properties             # Local configuration file            (Note: Only Dev have this file)
│   │       ├── credentials.json           # Get credentials google to use API   (Note: Only Dev have this file)
│   │       ├── logback-spring.xml         # Logs file configuration
│   └──     └── keystore.p12               # Self create SSL                     (Note: Only Dev have this file)
├── pom.xml                                # Maven configuration file (dependencies, plugins, etc.)
└── README.md                              # Project documentation
