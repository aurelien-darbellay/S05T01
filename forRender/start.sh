#!/usr/bin/env bash
set -e

# Start MySQL server and initialize if needed
echo "Initializing MySQL..."
mysqld --datadir=/var/lib/mysql --user=mysql --skip-networking --skip-grant-tables &
sleep 5
mysql < /docker-entrypoint-initdb.d/init.sql
kill %1

# Start MongoDB and initialize
echo "Initializing MongoDB..."
mkdir -p /data/db
mongod --dbpath /data/db --fork --logpath /var/log/mongod.log
sleep 5
mongo < /docker-entrypoint-initdb.d/init.mongo.js
mongod --dbpath /data/db --shutdown

# Launch MySQL on default port 3306
echo "Starting MySQL server..."
mysqld --datadir=/var/lib/mysql --user=mysql &

# Launch MongoDB on default port 27017
echo "Starting MongoDB server..."
mongod --dbpath /data/db --fork --logpath /var/log/mongod.log &

# Finally, start the Spring Boot application
echo "Starting Spring Boot application..."
java -jar app.jar