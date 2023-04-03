# Kafka Project

Este é um projeto exemplo utilizando o Apache Kafka com Spring Boot.
## Descrição

Este projeto é um exemplo de como utilizar o Apache Kafka em conjunto com o Spring Boot para realizar a troca de mensagens entre microsserviços.

O projeto é composto por dois microsserviços: o produtor e o consumidor.

O produtor é responsável por gerar as mensagens e enviá-las para o Kafka, enquanto o consumidor é responsável por receber essas mensagens e processá-las.

Neste projeto, utilizamos o KafkaTemplate para enviar as mensagens para o Kafka e o ConcurrentKafkaListenerContainerFactory para receber as mensagens.

Além disso, implementamos um mecanismo de tratamento de erros, utilizando a Dead Letter Queue (DLQ), para enviar as mensagens que não foram processadas com sucesso para uma fila de "mensagens mortas", para que possam ser analisadas posteriormente.

## Tecnologias usadas
- Java 17
- Docker
- Kafka


## Instação

Necessário rodas os seguintes comandos após ter baixado o projeto e navegado até a pasta raiz.

- **./gradlew build** 
- **docker-compose build**
- **docker-compose up**

