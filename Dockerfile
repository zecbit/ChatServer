FROM java:7
MAINTAINER Zec Zhao

ADD  target/ChatServer-1.0-SNAPSHOT.jar     /opt/ChatServer-1.0-SNAPSHOT.jar

RUN  mkdir -p /opt/lib
ADD  lib/netty-all-4.1.0.Final.jar          /opt/lib/netty-all-4.1.0.Final.jar

RUN  cd /opt
RUN  java -jar ChatServer-1.0-SNAPSHOT.jar