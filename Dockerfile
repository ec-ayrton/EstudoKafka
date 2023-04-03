# Imagem baseada em JDK 17 do OpenJDK
FROM openjdk:17-jdk-alpine

# Cria diretório de trabalho
WORKDIR /app

# Copia o arquivo war gerado pelo Gradle para o diretório de trabalho
COPY build/libs/Mensageria-0.0.1-SNAPSHOT.war app.war

# Expose a porta usada pela aplicação
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "app.war"]

