FROM eclipse-temurin:17-jdk

RUN mkdir -p  /app/bookMyShow
COPY ./target/*.jar /app/bookMyShow
#ARG TZ_ARG
COPY ./docker/start_app.sh /app/bookMyShow
COPY ./docker/application.properties /app/bookMyShow
RUN chmod a+x /app/bookMyShow/*
RUN chmod 7777 /app/bookMyShow/*
CMD [ "/app/bookMyShow/start_app.sh" ]
ENTRYPOINT [ "/bin/sh"]
EXPOSE 8080
