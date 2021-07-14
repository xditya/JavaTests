FROM maven:3.6.3-jdk-8

COPY . .

RUN mvn clean package

# set the startup command to execute the jar
CMD ["java", "-jar", "target/JavaTests-0.1-jar-with-dependencies.jar"]