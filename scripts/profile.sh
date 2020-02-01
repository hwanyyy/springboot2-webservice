#! /usr/bin/env bash

# idle상태인 profile 찾기: real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬고 있음
function find_idle_profile(){
  # 현재 nginx가 바라보고 있는 스프링부트가 정상적으로 수행 중인지 확인합니다. 응답을 HttpStatus로 받고, 오류가 발생하면 400~503사이로 발생하니, 오류면 real2를 현재 profile로 사용합니다.
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면(즉, 40x/50x에러 모두 포함)
  then
    CURRENT_PROFILE=real2   # 이슈가 있을 경우 real1에 배포하기 위해서 강제로 현재 포트를 real2로 두는 정책을 사용
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"  # bash는 값을 반환하는 기능이 없으므로 제일 마지막에 echo로 결과를 출력하고, 클라이언트에서 그 값을 잡아서 $(find_dile_profile)을 사용, 중간에 echo를 사용해선 안됨
}

# idle 상태인 profile의 port 찾기
function find_idle_port(){
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}