FROM openjdk:17-buster

# Install PostgreSQL client using apt-get
RUN apt-get update && apt-get install -y postgresql-client && rm -rf /var/lib/apt/lists/*

RUN mkdir -p  /app/bookMyShow
COPY ./target/*.jar /app/bookMyShow
#ARG TZ_ARG
ENV TZ=Africa/Johannesburg
COPY ./docker/host_entry.sh /app/bookMyShow
COPY ./docker/start_app.sh /app/bookMyShow
COPY ./docker/application.properties /app/bookMyShow
COPY ./docker/init_db.sh /app/bookMyShow
RUN chmod a+x /app/bookMyShow/*
RUN chmod 7777 /app/bookMyShow/*
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
CMD [ "/app/bookMyShow/start_app.sh" ]
ENTRYPOINT [ "/bin/sh"]
EXPOSE 8080