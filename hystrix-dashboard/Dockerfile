FROM java:8-jre
MAINTAINER Jonathan Merayo <jonathan@kcouk.org>

ADD ./target/hystrix-dashboard.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/hystrix-dashboard.jar"]

EXPOSE 8010