#!/usr/bin/env sh
source config.sh
${CURL_BIN} ${CURL_XML_OPTIONS} -X GET "${API_ENDPOINT}/buckets"