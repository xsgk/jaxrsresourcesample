#!/bin/sh

CONTAINER_NAME="jaxrsresourcesample"
LOCAL_HTTP_PORT=9080
CONTAINER_HTTP_PORT=9080
LOCAL_SSL_PORT=9443
CONTAINER_SSL_PORT=9443
NET=jaxrsnw
CPUSETCPUS=0-1
STARTCMD="server start"

#--------------------------------------------------
# Check for container network, if does not exist
# create new
#--------------------------------------------------
NW=`docker network ls | grep $NET`
if [ -z "$NW" ]
then
    docker network create $NET
fi

#--------------------------------------------------
# Stop and remove current container
#--------------------------------------------------
docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME
docker rmi $CONTAINER_NAME

#--------------------------------------------------
# Build container using Dockerfile
#--------------------------------------------------
docker build -t $CONTAINER_NAME .


#--------------------------------------------------
# Run the container
#--------------------------------------------------
docker run -dti -h $CONTAINER_NAME --name $CONTAINER_NAME -p $LOCAL_HTTP_PORT:$CONTAINER_HTTP_PORT -p $LOCAL_SSL_PORT:$CONTAINER_SSL_PORT --net=$NET --cpuset-cpus=$CPUSETCPUS $CONTAINER_NAME /bin/bash

#--------------------------------------------------
# Start WAS Liberty
#--------------------------------------------------
docker exec $CONTAINER_NAME $STARTCMD


