### Conceitos

</br>

### Topico
> Um tópico no Apache Kafka é um fluxo ordenado de registros.
>É semelhante a uma tabela em um banco de dados relacional ou a uma fila em um sistema de mensagens.
</br>
### Partição
> A partição é uma unidade de paralelismo no Kafka. Ela permite que os dados de um tópico sejam distribuídos em vários servidores Kafka, permitindo maior capacidade de processamento e maior escalabilidade.
### Replicação
> Os fatores de replicação se referem ao número de cópias que o Kafka armazena de cada partição. Cada partição pode ter várias réplicas, e as réplicas são distribuídas por diferentes servidores para garantir a disponibilidade e a tolerância a falhas do cluster.
> 

### Brokers
> Os brokers são responsáveis por gerenciar os tópicos, as partições e as réplicas, além de controlar a troca de mensagens entre os produtores e consumidores.
Quando um produtor envia uma mensagem para um tópico, o broker é responsável por garantir que essa mensagem seja armazenada na partição correta. E quando um consumidor faz uma solicitação de leitura de registros, o broker é responsável por recuperar as mensagens da partição correspondente e entregá-las ao consumidor.