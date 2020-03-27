#!/bin/bash

echo "--------------------------------"
echo "Starting Inventory Query Service"
echo "ENV: $APP_ENV"
echo "--------------------------------"

java -jar build/libs/spring-boot-minimal*.jar
