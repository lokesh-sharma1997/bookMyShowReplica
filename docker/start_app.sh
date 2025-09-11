#!/bin/sh

# startup.sh - startup script for the server docker image

echo "Starting bookMyShow server"

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
echo "SECRET:                 $SECRET"
echo "REDIS_HOST:             $REDIS_HOST"
echo "REDIS_PORT:             $REDIS_PORT"
echo "TTL:                    $TTL"

echo "Done"



sed -i "s;##DB_IP##;$DB_IP;"                      $config_file
sed -i "s;##DB_PORT##;$DB_PORT;"                  $config_file
sed -i "s;##DB_NAME##;$DB_NAME;"                  $config_file
sed -i "s;##DB_USER##;$DB_USER;"                  $config_file
sed -i "s;##DB_PASS##;$DB_PASS;"                  $config_file
sed -i "s;##BOOKMYSHOW##;$BOOKMYSHOW;"              $config_file
sed -i "s;##BOOKMYSHOWSERVICE##;$BOOKMYSHOWSERVICE;" $config_file
sed -i "s;##SECRET##;$SECRET;"                      $config_file
sed -i "s;##REDIS_HOST##;$REDIS_HOST;"              $config_file
sed -i "s;##REDIS_PORT##;$REDIS_PORT;"              $config_file
sed -i "s;##TTL##;$TTL;"              $config_file
echo "Using java options config: $JAVA_OPTS"

java ${JAVA_OPTS} -jar  -Dspring.config.location=/app/bookMyShow/application.properties  /app/bookMyShow/bookmyshow-backend-1.0.jar 2>&1 | tee -a /app/bookMyShow/server.log

 
