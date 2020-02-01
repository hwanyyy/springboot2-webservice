#!/usr/bin/env bash


ABSPATH=$(readlink -f $0)   # 현재 stop.sh가 속해있는 디렉토리를 나타냅니다.

ABSDIR=$(dirname ${ABSPATH})

source ${ABSDIR}/profile.sh     # stop.sh에서도 profile.sh의 function들을 사용하기 위해 import

IDLE_PORT=$(fins_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PORT=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $IDLE_PID"
    kill -15 ${IDLE_PID}
    sleep 5
fi