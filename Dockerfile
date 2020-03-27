FROM openjdk:11-jre-slim

ENV APP_ENV=${APP_ENV:-production}

WORKDIR /app

RUN mkdir -p /app/build

# TODO: Enable newrelic
COPY build/libs/*.jar build/libs/
COPY runApp.sh runApp.sh

ENTRYPOINT ["./runApp.sh"]
