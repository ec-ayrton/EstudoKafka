## Configurações

### Properties
#### Configuração do projeto:
1. spring.kafka.bootstrap-servers = localhost:9092
2. spring.kafka.consumer.group-id = my-group
3. spring.kafka.consumer.enable-auto-commit = false
4. spring.kafka.consumer.auto-offset-reset = latest
5. spring.kafka.consumer.max-poll-records = 200


#### O que cada configuração representa:
1. Configuração necessária para indicar qual porta o serve do kafka está disponivel.
2. indica o grupo dos consumidores padrão.
3. Configuração que permite o controle da confirmação do offset ao servidor Kakfa. Usando o Acknowledgmente para confirmar.
4. Configuração que indica o offset inicial para um consumidor quando ele é iniciado pela primeira vez ou quando não consegue encontrar um offset valido.Com o "latest" significa que o consumidor lerá as mensagens que foram produzidas após a sua ultima execução ou depois que ele caiu a ultima vez.
5. Configuração responsavel por indicar o número máximo de registros que cada consumidor poderá buscar em cada chamada ao servidor Kafka.


## Classe de configuração

#### consumerConfigs

   ```
   @Bean
   public Map<String, Object> consumerConfigs() {
      Map<String, Object> props = new HashMap<>();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
      props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      return props;
   }
   ```
   - É Responsavel pela configuração do consumer da aplicação fazendo uso de propriedades pré-definidas seja nos atributos da aplicação, seja no arquivo properties.

#### consumerFactory

     @Bean
     public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
     }
- É responsavel por definir/criar a fabrica de consumidores usando o **DefaultKafkaConsumerFactory** que irá receber os parametros de configuração da aplicação.
Assim essa fábrica vai criar os consumidores com as configurações já pré-definidas anteriormente.

#### kafkaListenerContainerFactory

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(KafkaTemplate<String,String> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);

        settingConfigDLQ(factory,kafkaTemplate);

        return factory;
    }

- O método é resposavel por criar a fábrica que é usada para criar instâncias de **ConcurrentMessageListenerContainer** para consumir mensagens do Apache Kafka.


- ConcurrentMessageListenerContainer é a classe que representa um contêiner que executa um ou mais ouvintes de mensagens Kafka de forma simultânea em várias threads.


#### _factory.setConsumerFactory(consumerFactory());_
        
    - Aqui especifico a fábrica de consumidores que deverá ser usada e consequentemente as configurações inerentes aos consumidores desse listener.

#### _factory.setConcurrency(3);_
    
    - Aqui é configurado o número de instância de consumidores para cada partição que serão criadas para processar as mensagens do tópico especifico. 
      Isso significa que o processo de consumo será distribuído entre essas instâncias.
      Exemplo: Se tenho 3 tópicos distintos, com uma partição cada, então terei um total de 9 instâncias.
               Se tenho 4 tópicos distintos, com 2 partições cada e fator concurrency 3. Com isso, terei no minimo 8 consumidores devido o número de partições e topicos.
               Mas além disso com o fator concurrency 3, cada consumidor será executado em 3 threds separadas. Totalizando 24 consumidores.

#### _factory.getContainerProperties().setPollTimeout(3000);_
    - A configuração aqui diz respeito ao tempo que o método poll() irá aguardar para retornar a lista de registros para o servidor kafka.
      Caso não haja, ele retornará uma lista vazia. A configuração é importante para evitar o consumo de recursos, 
      como aguardar demasiadamente por novos registros podendo causar demora no processamente de novos registros de forma desnecessária.

#### _settingConfigDLQ(factory,kafkaTemplate);_
     private void settingConfigDLQ(ConcurrentKafkaListenerContainerFactory<String, String> factory,KafkaTemplate<String,String> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,(data, exception) -> {
            String dlqTopic = "DeadQueue.DLQ";
            int dlqPartition = -1;
            return new TopicPartition(dlqTopic, dlqPartition);
        });
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 1));
        factory.setCommonErrorHandler(errorHandler);
    }

    Aqui a implementação desse método se faz necessária para tratar dos Registros que não foram processados com sucesso e foram enviador para a DeadListQueue.
    Sobre o método:
    - O recover é um objeto DeadLetterPublishingRecoverer responsavel por definir como tratar os registros que não foram tratados com sucesso e foram enviados para a dead-letter.
      Nesse caso foi implementado para usaro o kafkaTemplate para enviar os registros da dead-letter para um tópico chamado "DeadQueue.DLQ" .
    - O errorHandler é um objeto DefaultErrorHandler que é responsavel por chamar o método recover() do objeto DeadLetterPublishingRecoverer quando ocorre um erro no processamento do registro e 
      por definir o inverfalo de espera entre as tentativas de processamento.
    por fim é configurado para o factory usar o errorHandler.