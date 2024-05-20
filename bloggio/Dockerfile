FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]