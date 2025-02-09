## Flush & Commit

## Flush

What It Does:
A flush synchronizes the persistence context (e.g., the Hibernate Session) with the database.
It sends all pending changes (inserts, updates, deletes) from the persistence context to the database as SQL
statements (INSERT, UPDATE, DELETE).
However, the changes are not yet permanently saved (committed) to the database.

In a scenario that you need to enforce constraint such as unique index. You have series of database operations and at
the end you have a event publisher.
The last database operation should flush the change before calling the eventPublisher so that if there are any database
constraint, the publisher won't be invoked.

```java
jpa.save(user);
jpa.

save(user1);
jpa.

save(user2);
//If there any constraint issues, this will encounter exception and won't proceed on publishing.
//In this scenario, if you are using normal jpa.save, flush and commit happens at the end of transaction which leads invoking the eventPublisher first before encountering an error.
jpa.

saveAndFlush(user3); 
        
eventPublisher.

publish(List.of(users))
```

## Commit

What It Does:
A commit finalizes the transaction and makes all changes permanent in the database.
It applies all the changes that were flushed to the database during the transaction.
After a commit, the changes are visible to other transactions (depending on the database isolation level).