FROM java:8-jre
MAINTAINER Jonathan Merayo <jonathan@kcouk.org>

ADD ./target/client.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/client.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:9999/health || exit 1

EXPOSE 9999