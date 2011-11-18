#!/usr/bin/env sh
source config.sh
if [[ $# -lt 2 ]]; then
	echo "invalid parameters"
	exit 1
fi
cat xml/customer_create.xml | sed -l "s/__UID__/$1/g;s/__NAME__/$2/g" | ${CURL_BIN} ${CURL_XML_OPTIONS} -X POST -d @- "${API_ENDPOINT}/customers"