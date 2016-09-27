FROM ubuntu:14.04
MAINTAINER Zec Zhao

RUN apt-get install -y software-properties-common \
&& add-apt-repository -y ppa:webupd8team/java

RUN apt-get update \
&& echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections \
&& echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections \
&& apt-get install -y oracle-java7-installer

RUN apt-get install -y openssh-client \
&& ssh-keygen -q -N '' -f ~/.ssh/id_rsa -t rsa -C "docker.jdk7@abc.se"

ENV         JAVA_HOME         /usr/lib/jvm/java-7-oracle

ADD  target/ChatServer-1.0-SNAPSHOT.jar     /opt/ChatServer-1.0-SNAPSHOT.jar

RUN  mkdir -p /opt/lib
ADD  lib/netty-all-4.1.0.Final.jar     /opt/lib/netty-all-4.1.0.Final.jar

RUN  cd /opt
RUN  java -jar ChatServer-1.0-SNAPSHOT.jar