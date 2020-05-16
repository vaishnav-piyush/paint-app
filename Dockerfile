FROM store/oracle/serverjre:8

RUN mkdir /app

WORKDIR /app

COPY target/paint-app-1.0-SNAPSHOT.jar .

LABEL maintainer="Piyush Vaishnav <vaishnav.piyush@gmail.com>" \
              version="1.0-SNAPSHOT"

CMD java -jar paint-app-1.0-SNAPSHOT.jar


