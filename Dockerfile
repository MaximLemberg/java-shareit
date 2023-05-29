FROM postgres:15.3-alpine3.18
COPY target/*.jar *.jar
ENTRYPOINT ["java","-jar","*.jar"]
