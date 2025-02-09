# Spring JPA Transactional
This is just a simple @Transactional demo where the rollback works when encountered exception.

## Default transaction type
The default transaction type is REQUIRED. This transaction type creates a transaction if there is no existing transaction.
If there is an existing transaction, it will be merged in an existing transaction.