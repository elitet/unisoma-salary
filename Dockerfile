# Define a imagem base com o Maven para fazer o build do projeto
FROM maven:3.8.4-openjdk-11-slim AS build

# Copia os arquivos de configuração do projeto para o contêiner
COPY pom.xml .
COPY src ./src

# Faz o build do projeto com o Maven
RUN mvn clean package -DskipTests

# Define uma nova imagem base com o Java 11
FROM adoptopenjdk:11-jdk-hotspot

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR do build para o diretório de trabalho
COPY --from=build target/unisoma-salary-api.jar app.jar

# Define o comando de inicialização da aplicação
CMD ["java", "-jar", "app.jar"]