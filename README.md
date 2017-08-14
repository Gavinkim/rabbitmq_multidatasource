## RabbitMQ and Multi-Datasource(mysql,postgresql)

```json
    - RabbitMQ Replication 
      클러스터링 된 rabbitmq
    - Multidatasource (mysql, postgresql)
    
    큐에 데이터를 넣은 후 각 데이터베이스에 큐에서 꺼내온 데이터를 동기화 시켜주는 샘플 예제
    큐에서 데이터를 꺼내올 경우 버퍼에 max 만큼 증가 되면 큐리스너를 잠시 중지하고 데이터 저장후 다시 큐 리스너를 동작 시키도록 한다. 
```