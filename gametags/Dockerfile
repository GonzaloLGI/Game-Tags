FROM maven:3.8.6-openjdk-18 as previous
COPY . . 

RUN mvn clean package -Dmaven.test.skip=true

CMD ["java","-jar","target/gametags-0.0.1-SNAPSHOT.jar"]