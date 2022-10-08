#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
echo ">>>>> ${ABSDIR}"
source $(ABSDIR)/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=kwan-springboot2-webservice

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">JAR name: $JAR_NAME"
chmod +x $JAR_NAME

echo "$JAR_NAME 실행"
IDLE_PROFILE=$(find_idle_profile)
nohup java -jar -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties -Dspring.profiles.active=$IDLE_PROFILE \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &