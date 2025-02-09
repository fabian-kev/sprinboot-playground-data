# Spring Data: JPA Batching

How to enable JPA batching?
```yaml
spring:
  jpa:
    properties:
      hibernate:
        order_inserts: true
        order_updates: true
        generate_statistics: true
        jdbc:
          batch_size: 5
```
`hibernate.jdbc.batch_size`: Sets the batch size.
`hibernate.order_inserts` and `hibernate.order_updates`: Ensure that inserts and updates are ordered, which is necessary for batching to work.

### Updating objects to add a new associated object without @Transactional
This usually happens when you perform your database operations outside @Transactional.

### Hibernate’s Dirty Checking Mechanism
Hibernate uses a mechanism called dirty checking to determine which entities have changed and need to be updated in the database.
Inside a transaction, Hibernate keeps track of the state of entities (e.g., whether they are new, modified, or unchanged).
Without a transaction, Hibernate loses this tracking ability and has to query the database to check the state of each entity before deciding whether to INSERT or UPDATE.