## Configurações

### Properties
#### Configuração do projeto:
1. spring.kafka.bootstrap-servers = localhost:9092
2. spring.kafka.consumer.group-id = my-group
3. spring.kafka.consumer.enable-auto-commit = false
4. spring.kafka.consumer.auto-offset-reset = latest


#### O que cada configuração representa:
1. Configuração necessária para indicar qual porta o serve do kafka está disponivel.
2. indica o grupo dos consumidores padrão.
3. Configuração que permite o controle da confirmação do offset ao servidor Kakfa. Usando o Acknowledgmente para confirmar.
4. Configuração que indica o offset inicial para um consumidor quando ele é iniciado pela primeira vez ou quando não consegue encontrar um offset valido.Com o "latest" significa que o consumidor lerá as mensagens que foram produzidas após a sua ultima execução ou depois que ele caiu a ultima vez.


