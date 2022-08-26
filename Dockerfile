FROM tomcat:8.5.16-jre8
RUN rm -rf $CATALINA_HOME/webapps/*
ADD ./target/*.war $CATALINA_HOME/webapps/ROOT.war
COPY ./src/main/resources/application.properties $CATALINA_HOME/webapps/