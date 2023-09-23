FROM kdvolder/jdk8

EXPOSE 8081

VOLUME /docker/tmp:/log

ADD Blog-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]