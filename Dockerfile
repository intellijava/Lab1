FROM adoptopenjdk:16-jre

EXPOSE 5050
COPY core/target/modules /app/modules
COPY core/target/core-1.0-SNAPSHOT.jar  /app/core.jar
COPY core/target/classes /app/core/

ENTRYPOINT [ "java", "--module-path", "/app/core:/app/modules", "-m", "core/core.Main"]
