# start from a pre-packaged tomcat appserver on boot2docker
FROM tomcat

# install vim
RUN apt-get update && apt-get install -y vim

#use CATALINA_OPTS vs JPDA_OPTS to avoid jpda run
ENV CATALINA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9001