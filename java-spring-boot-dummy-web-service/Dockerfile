FROM maven:3.6.1-jdk-8
COPY . /usr/src/app/
WORKDIR /usr/src/app
RUN mvn clean package

FROM openjdk:8-jre-stretch
ENV SERVER_PORT 8080
COPY --from=0 /usr/src/app/target/petstore-dummy-spring-boot-1.0.0.jar /usr/src/app/
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/usr/src/app/petstore-dummy-spring-boot-1.0.0.jar" ]