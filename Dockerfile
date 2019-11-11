FROM websphere-liberty:latest

USER root

RUN mkdir -p /usr/applib
copy mysql-connector-java-8.0.16.jar /usr/applib

COPY server.xml /config/

COPY target/ColleagueService.war /config/apps/DevOpsService.war

ENTRYPOINT ["/opt/ibm/wlp/bin/server", "run", "defaultServer"]
