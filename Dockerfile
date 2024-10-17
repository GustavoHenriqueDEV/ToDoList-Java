# Fase 1: Build do projeto
FROM ubuntu:latest AS build

# Atualizar repositórios e instalar o JDK 17 e Maven
RUN apt-get update && apt-get install openjdk-17-jdk maven -y

# Definir o diretório de trabalho para o projeto
WORKDIR /app

# Copiar todo o código-fonte para o container
COPY . .

# Executar o Maven para construir o projeto
RUN mvn clean install

# Fase 2: Criar a imagem final
FROM ubuntu:latest

# Instalar o JDK na imagem final
RUN apt-get update && apt-get install openjdk-17-jdk -y

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo .jar do estágio de build
COPY --from=build /app/target/testeJava172-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando de inicialização do aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
