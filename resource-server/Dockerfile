FROM java:8-jre
MAINTAINER Jonathan Merayo <jonathan@kcouk.org>

ADD ./target/resource-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/resource-server.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:9999/health || exit 1

EXPOSE 9999