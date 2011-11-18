#!/usr/bin/env sh
source config.sh
URL=${API_ENDPOINT}$1
printf "\n// GET %s\n\n" $URL
${CURL_BIN} ${CURL_JSON_OPTIONS} $URL
