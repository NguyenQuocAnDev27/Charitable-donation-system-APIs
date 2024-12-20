# Charitable-donation-system-APIs
The system is a donation platform for charitable contributions to higher education institutions.

## Overview
DonationInUniversity is a Spring Boot project built with JPA and using MySQL for database. The system manages donation projects, user roles, and allows users to donate through multiple payment methods. The project also supports tagging projects, generating reports, and sending notifications to users. (web services, microservices, multithread, multi-processing, file (image, pdf,...) processing)

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
```

## Documents
[Báo cáo dự án.pdf](https://github.com/user-attachments/files/18208829/Bao.cao.d.an.pdf)

# Other project
## Frontend Web
This web is build with NextJS and Tailwind which is used for donor user.<br />
Github project link [Click here](https://github.com/NguyenQuocAnDev27/Charitable-donation-system-client-web).

## Backend 2
This server is build with Typesciprt and ExpressJS which is used for translate VI-EN language.<br />
Github project link [Click here](https://github.com/NguyenQuocAnDev27/translate-server).

## Nguyễn Quốc An's Mobile App Platform (In developing stage ⚙️)
This app is build with React Native.<br />
You can see my UX design on Figma [Click here](https://www.figma.com/design/de0oO1ulDj3JuYjAQqU7xL/CDS-mobile-app?node-id=0-1&t=sU0HzlIGzsIjMetp-1).

![CDS mobile app](https://github.com/user-attachments/assets/e6778ee5-3061-430b-975a-470a0f01e19a)
