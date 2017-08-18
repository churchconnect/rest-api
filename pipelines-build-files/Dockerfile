FROM openjdk:8
MAINTAINER Sharptop Software <info@sharptop.co>

#install dependencies
# awscli is for uploading to s3 and creating application versions
RUN apt-get update 
RUN apt-get install --yes --force-yes awscli wget zip unzip

ENV GRAILS /opt/grails-3.1.9/bin/grails

#install grails 3.1.9
RUN wget https://github.com/grails/grails-core/releases/download/v3.1.9/grails-3.1.9.zip
RUN unzip grails-3.1.9.zip
RUN mv grails-3.1.9 /opt/
RUN java -version
RUN /opt/grails-3.1.9/bin/grails

ENV SCRIPTS_DIR pipelines-build-files
