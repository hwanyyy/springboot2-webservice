#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=springboot2-webservice

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션 pid 확인"

# 실행 중이면 종료하기 위해서 현재 수행 중인 프로세스id를 찾습니다.
# springboot2-webservice으로 된 다른 프로그램들이 있을 수 있어 springboot2-webservice된 jar 프로세스를 찾은 뒤 id를 찾습니다(awk '{print $1}').
CURRENT_PID=$(pgrep -fl $PROJECT_NAME | grep jar | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  ehco "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR name: JAR_NAME"
echo "> $JAR_NAME에 실행 권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 & # nohup실행 시 CodeDeploy는 무한 대기 합니다. 이 이슈를 해결하기 위해 nohub.out파일을 표준 입출력용으로 별도로 사용합니다. 이렇게 하지 않으면 nohup.out파일이 생기지 않고, CodeDeploy 로그에 표준 입출력이 출력됩니다. nohub이 끝나기 전까지 CodeDeploy도 끝나지 않으니 꼭 이렇게 해야합니다.