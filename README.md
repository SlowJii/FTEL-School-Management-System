# Building web applications in Java with Spring Boot 3

## Agenda

- Module 1: Create project - Jul 2nd 2024
  - [start.spring.io](http://start.spring.io)
    - Java Build Tool (Maven)
    - Spring Data JPA
    - Spring Web
    - Rest Repositories
    - Lombok
    - PostgreSQL Driver
    - Thymleaf
    - Json Web Token
    - OAuth2
  - IDE: Intellij Idea Community
  - Java 22
  - Open JDK 22
- Module 2: Create model, service, repository and controller - Jul 3rd 2024
  - Student
  - Course
  - Generate email with Name and UUID
- Module 3: RESTful service - Jul 4th 2024
  - CRUD
  - Annotation
  - Postman (Testing)
- Module 4: Connect database Jul 12th 2024
  - PostgreSQL
  - Postman (Testing)
  - ManyToMany relationship
- Module 5: Security with BCrypt 19th Jul 2024
  - Inject dependency org.springframework.security
  - Try PasswordEncoder
- Module 6: Authenticate and Authorize, building Role

## ROLE
- ADMIN: all permissions!

- STAFF
  - Update User information
  - Add Course to User
  - Remove Course from User
  - Get Course by user id
  - Get Users by course id
  - Get all Course
  - Get Course by id
  
## ER Diagram
![ERD](https://private-user-images.githubusercontent.com/116516222/355509626-bd523420-6e1f-4bc6-819b-589b594dac8d.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjI5NTgyOTgsIm5iZiI6MTcyMjk1Nzk5OCwicGF0aCI6Ii8xMTY1MTYyMjIvMzU1NTA5NjI2LWJkNTIzNDIwLTZlMWYtNGJjNi04MTliLTU4OWI1OTRkYWM4ZC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQwODA2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MDgwNlQxNTI2MzhaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT03MTI2ZjhmMzY4ZWNiZjU2NjA5ZjliMGFiODFkYmRlNGM2NTQ0YTZhOGMwNDgxMDIyY2RiMWZlZjVhOTJiNjAyJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.g1VQd6McPPCG7nI95flrbzs6tepRmC4--c2eRs-LVxM)

## USER TABLE
![USER_TABLE] (https://github.com/user-attachments/assets/40a2a7f6-f3e4-4f07-b806-927dc1e24be7)
