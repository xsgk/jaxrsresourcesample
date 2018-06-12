FROM websphere-liberty:javaee7
MAINTAINER cheese@jp.ibm.com
RUN mkdir -p /opt/jaxrsresourcesample/upload && \
	mkdir -p /opt/jaxrsresourcesample/download
ADD ./jaxrs.war /opt/ibm/wlp/usr/servers/defaultServer/dropins
ADD ./server.xml /opt/ibm/wlp/usr/servers/defaultServer
ADD ./download.png /opt/jaxrsresourcesample/download
EXPOSE 9080
EXPOSE 9443
