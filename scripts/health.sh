#!/usr/bin/env bash


ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname ${ABSPATH})
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT: ${IDLE_PORT}"
echo "> curl -s http://localhost:${IDLE_PORT}/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
    RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
    UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

    # nginx와 연결되지 않은 포트로 스프링부트가 잘 수행되었는지 체크합니다. 잘 떳는지 확인이 되어야 nginx proxy 설정을 변경(switch_proxy)합니다.
    if [ ${UP_COUNT} -ge 1 ]
    then    # up_count >= 1 ("real" 문자열이 있는지 검증)
        echo "> Health check success"
        swtich_proxy
        break
    else
        echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
        echo "> Health check: ${RESPONSE}"
    fi

    if [ ${RETRY_COUNT} -eq 10 ]
    then
        echo "> Health check fail"
        echo "> nginx에 연결하지 않고 배포를 종료합니다."
        exit 1
    fi

    echo "> Health check 연결 실패. 재시도..."
    sleep 10

done