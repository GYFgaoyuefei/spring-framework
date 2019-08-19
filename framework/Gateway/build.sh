#!/bin/bash
echo "-------------------------------------------"
echo "start mvn clean package -f package.xml"
echo "-------------------------------------------"
CURR_DIR=$(cd `dirname $0`; pwd)
echo "CURR_DIR:"$CURR_DIR
cd ${CURR_DIR}
mvn clean package -DskipTests -f package.xml -P $1