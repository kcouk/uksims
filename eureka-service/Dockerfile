FROM java:8-jre
MAINTAINER Jonathan Merayo <jonathan@kcouk.org>

ADD ./target/eureka-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/eureka-service.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8761/health || exit 1

EXPOSE 8761