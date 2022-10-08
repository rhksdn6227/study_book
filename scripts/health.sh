#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source $(ABSDIR)/profile.sh
source $(ABSDIR)/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> health check start";
echo "> curl -s http://localhost:$IDLE_PORT/profiles"
sleep 10

for RETRY_COUNT in {1..10}
do
 RESPONSE=${curl -s http://localhost:$IDLE_PORT/profiles}
 UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)
 if [ ${UP_COUNT} -ge 1 ]; then
   echo "> health check 성공"
   switch_proxy
   break
  else
    echo "> health check 이상함: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]; then
    echo "health check 실패. 엔진엑스 연결 안하고 배포 종료"
    exit 1
  fi

  echo "헬스 체크 재시도.."
  sleep 10
done