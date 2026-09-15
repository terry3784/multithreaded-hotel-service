# ---------- Stage 1: Build Angular UI ----------
FROM node:18-bullseye-slim AS ui-build
WORKDIR /app/ui

COPY src/main/UI/package*.json ./
RUN npm ci

COPY src/main/UI/ ./
RUN /usr/local/bin/node ./node_modules/@angular/cli/bin/ng build --configuration production


# ---------- Stage 2: Build Spring Boot ----------
FROM maven:3.9.6-eclipse-temurin-17 AS backend-build
WORKDIR /app

# Install Node.js + Angular CLI so Maven's exec plugin can run "ng"
RUN apt-get update && apt-get install -y curl \
  && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
  && apt-get install -y nodejs \
  && npm install -g @angular/cli \
  && rm -rf /var/lib/apt/lists/*

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# ---------- Stage 3: Final Runtime Image ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy Spring Boot JAR
COPY --from=backend-build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]