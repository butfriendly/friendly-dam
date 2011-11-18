#!/usr/bin/env sh
source config.sh
cat xml/bucket_create.xml | sed -l "s/__UID__/$1/g" | ${CURL_BIN} ${CURL_XML_OPTIONS} -X POST -d @- "${API_ENDPOINT}/buckets"