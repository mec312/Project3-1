FROM java:8
VOLUME /tmp
ADD target/microservice-0.0.1-SNAPSHOT.jar desposit.jar
RUN bash -c 'touch /desposit.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /desposit.jar"]