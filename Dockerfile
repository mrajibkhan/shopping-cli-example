FROM gradle:latest
USER root
COPY . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle --info --stacktrace clean build
ENTRYPOINT ["java", "-jar", "build/libs/flower-shop-0.1.0.jar"]