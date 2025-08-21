#!/bin/sh

# startup.sh - startup script for the server docker image

echo "Starting bookMyShow server"

/app/bookMyShow/host_entry.sh $HOSTS
 
 echo "Initializing the database..."
/app/bookMyShow/init_db.sh
if [ $? -ne 0 ]; then
    echo "Database initialization failed. Exiting."
    exit 1
fi

export config_file=/app/bookMyShow/application.properties
#fi
echo "BOOKMYSHOW:              $BOOKMYSHOW"
echo "BOOKMYSHOWSERVICE:       $BOOKMYSHOWSERVICE"
echo "hosts:                  $HOSTS"
echo "DB_IP:                  $DB_IP"
echo "DB_PORT:                $DB_PORT"
echo "DB_NAME:                $DB_NAME"
echo "DB_USER:                $DB_USER"
echo "DB_PASS:                $DB_PASS"
echo "SECRET:                $SECRET"



sed -i "s;##DB_IP##;$DB_IP;"                      $config_file
sed -i "s;##DB_PORT##;$DB_PORT;"                  $config_file
sed -i "s;##DB_NAME##;$DB_NAME;"                  $config_file
sed -i "s;##DB_USER##;$DB_USER;"                  $config_file
sed -i "s;##DB_PASS##;$DB_PASS;"                  $config_file
sed -i "s;##BOOKMYSHOW##;$BOOKMYSHOW;"              $config_file
sed -i "s;##BOOKMYSHOWSERVICE##;$BOOKMYSHOWSERVICE;" $config_file
sed -i "s;##SECRET##;$SECRET;"                      $config_file

echo "Using java options config: $JAVA_OPTS"

java ${JAVA_OPTS} -jar  -Dspring.config.location=/app/bookMyShow/application.properties  /app/bookMyShow/bookmyshow-backend-*-SNAPSHOT.jar 2>&1 | tee -a /app/bookMyShow/server.log

 