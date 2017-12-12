FROM java:8-jre
MAINTAINER Jonathan Merayo <jonathan@kcouk.org>

ADD ./target/zipkin-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/zipkin-service.jar"]

EXPOSE 9411