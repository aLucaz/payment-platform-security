# Payment Platform Security
Security layer of a payment platform, this project implements the 
new version of Spring Security which has deprecated the WebSecurityConfigurerAdapter.

## Technologies and Concepts used

- Oauth2
  - token generation
  - token introspection
  - token revokation
- Spring Security
- Client Credentials Flow for server to server communication
- Java 11

## Project achitecture

In progress

### Project structure
```
.
├── main
│   ├── java
│   │   └── pe
│   │       └── client
│   │           └── custom
│   │               └── app
│   │                   ├── config
│   │                   │   ├── keys
│   │                   │   └── properties
│   │                   ├── controller
│   │                   ├── domain
│   │                   ├── dto
│   │                   ├── exception
│   │                   ├── service
│   │                   │   └── impl
│   │                   └── util
│   │                       └── constant
│   └── resources
│       ├── static
│       └── templates
```