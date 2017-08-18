#!/bin/bash

#global variables:
application_name='church-connect-api'
#also can identify bucket path by: aws elasticbeanstalk describe-application-versions --application-name church-connect-api
s3bucket='elasticbeanstalk-us-east-1-532310389034'

#deployment script built off: http://www.deplication.net/2013/11/java-war-deployment-options-on-aws.html
if [ -z "$1" ]; then
    echo 'need to specify a war file'
    exit
fi

if [ -z "$2" ]; then
    echo 'need to specify a version label'
    exit
fi

war_path=$1
war_filename=$(basename "$1")
version_label=$2

#the process is fairly simple:
#copy the war file up to s3 (to the bucket for the application)
aws s3 cp $war_path s3://$s3bucket/$version_label.war

#create an application version using the file in s3
aws elasticbeanstalk create-application-version --application-name $application_name --version-label $version_label --source-bundle S3Bucket=$s3bucket,S3Key=$version_label.war
