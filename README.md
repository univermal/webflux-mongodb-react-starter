# Starter Project for Spring Boot WebFlux Swagger MongoDB React & many other goodies

### What's in the package:
- Spring Boot with WebFlux
- Swagger2 fully automated configuration, accessible at /api
- Logback configured with SizeAndTimeBasedRollingPolicy
- Access log support added
- **JWT**: Full authentication and authorization implemented with sample Role and User (do change the secrets before publishing your app)
- Access controlled API and Bearer login
- Ready application config
- Spring **Actuator** configured, accessible at /manage
- **Reactive MongoDB** with generic CRUD service
- **Audit** support on any collection
- **Auto-sequence** generation for collections
- End to end sample backend flow
- Sample JUnits
- **Lombok** based pojos
- Separate modules for backend and frontend
- **React** configured as frontend
- Integrated production build for backend and frontend, frontend can still be used separately during dev
 
### Instructions:
- Start local MongoDB
- Run Application.java
- Optionally pass -Dreactor.netty.http.server.accessLogEnabled=true in commandline to enable access.log
- Open localhost:8080
- Goto /api for swagger
- You can login using /login from swagger (user/user), then take the token and use swagger authentication to pass Bearer <token>. You are now authorized and should be able to use other APIs.


Enjoy! 