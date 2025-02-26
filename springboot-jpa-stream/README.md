# Spring JPA Stream
This app shows that it handles the 7 million data efficiently with just 1GB max heap size averaging of 5-10% in 4 minutes


## Notes
Jpa stream works well in single threaded environment. For example single threaded batch processing.

## Overriding Java heap to see the difference between stream/cursor vs find all
```bash
#Override via terminal though this project overrides the heap via pom at spring-boot-maven-plugin though when you run this app through Intellij, it does not override the heap, but using mvn spring-boot:run works.
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xms128m -Xmx256m"
```


