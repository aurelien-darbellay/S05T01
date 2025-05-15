#!/usr/bin/env bash
set -e

# Initialize MySQL data directory if empty
if [ ! -d "/var/lib/mysql/mysql" ]; then
    echo "Initializing MySQL data directory..."
    mysqld --initialize-insecure --user=mysql
fi

# Start MySQL server
echo "Starting MySQL server..."
service mysql start

# Initialize database if needed
if [ -f "/docker-entrypoint-initdb.d/init.sql" ]; then
    echo "Running init script..."
    mysql -u root < /docker-entrypoint-initdb.d/init.sql
fi

# Start MongoDB
echo "Starting MongoDB..."
mkdir -p /data/db
mongod --dbpath /data/db --fork --logpath /var/log/mongod.log

# Start Spring Boot application
echo "Starting Spring Boot application..."
exec java -jar app.jar