#!/usr/bin/env sh
source _config.sh
URL=${API_ENDPOINT}$1
FILE=$2
if [[ ! -f "$FILE" ]]; then
	echo "File $FILE not found"
	exit 1
fi
printf "\n// POST %s\n\n" $URL
${CURL_BIN} ${CURL_XML_OPTIONS} -X POST -d @$FILE $URL
