#!/usr/bin/env sh
source config.sh
URL=${API_ENDPOINT}$1
printf "\n// DELETE %s\n\n" $URL
${CURL_BIN} ${CURL_XML_OPTIONS} -X DELETE $URL
