FROM java:8-jre
MAINTAINER Jonathan Merayo <jonathan@kcouk.org>

ADD ./target/boot-admin.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/boot-admin.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8765/health || exit 1

EXPOSE 9999