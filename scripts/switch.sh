#!/usr/bin/env bash


ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname ${ABSPATH})
source ${ABSDIR}/profile.sh

function switch_proxy(){
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 port: $IDLE_PORT"
    echo "> Port 전환"
    # nginx가 변경할 프록시 주소를 생성, ''를 사용하지 않으면 $service_url을 그대로 인식못하고 변수를 찾음,  inc파일에 url빼 둠, sudo tee(화면+파일 출력) 로 덮어씀
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> nginx reload"
    sudo service nginx reload   # nginx 설정을 다시 불러오는데 restart는 잠시 끊기는 현상이 있지만, reload는 끊김 없이 다시 불러옴, 다만 중요한 설정들은 restart를 사용하고, 여기선 외부 설정파일인 service-url만 다시 불러오기 때문에 reload 가능

}