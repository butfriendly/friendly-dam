#!/usr/bin/env sh
source _config.sh
URL=${API_ENDPOINT}$1
printf "\n// GET %s\n\n" $URL
${CURL_BIN} ${CURL_XML_OPTIONS} $URL | ${TIDY_BIN} ${TIDY_XML_OPTIONS}
