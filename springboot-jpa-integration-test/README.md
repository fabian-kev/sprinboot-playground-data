# READ ME

I had been debugging this for 2 days. it turned out the problem was the liquibase script itself.
The below example there was changeSet.changes.sqlFiles.`dbms`. That field made the Liquibase looked like it ran
successfully,
but it turned out that it didn't execute the script under it. there was no error that is why this one was so hard to
debug.

```yml
#not working
databaseChangeLog:
  - logicalFilePath: db.changelog.1.0.initial.yml
  - changeSet:
      author: kevin.fabian
      id: 1.0.1
      sqlFile:
        dbms: posgresql
        path: rollouts/V1.1.0_schema.sql
        relativeToChangelogFile: true
#working        
databaseChangeLog:
  - logicalFilePath: db.changelog.1.0.initial.yml
  - changeSet:
      author: kevin.fabian
      id: 1.0.1
      sqlFile:
        path: rollouts/V1.1.0_schema.sql
        relativeToChangelogFile: true
```