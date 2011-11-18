API_ENDPOINT="http://127.0.0.1:8080/dam/rest"

CURL_BIN=`which curl`
TIDY_BIN=`which tidy`
curl_options="--silent -v"
tidy_options="-quiet"

if [[ $# -lt 3 ]]; then
	echo "invalid parameters"
	exit 1
fi

while getopts ":f t v" o ; do
        case $o in
                f ) FILE=$OPTARG;;
                t ) t=$OPTARG;;
                v ) curl_options="${curl_options} -v";;
        esac
done

METHOD=`echo "$1"|awk '{print toupper($0)}'`
FORMAT=`echo "$2"|awk '{print toupper($0)}'`
url=${API_ENDPOINT}$3

case $FORMAT in
	XML)
		curl_options="--header 'Content-Type: application/xml' --header 'Accept: application/xml' ${curl_options}"
		tidy_options="-xml -i ${tidy_options}"
		;;
	JSON)
		curl_options="--header 'Content-Type: application/json' --header 'Accept: application/json' ${curl_options}"
		;;
	*)
		printf "Invalid format: %s\n" $FORMAT
		;;
esac

case $METHOD in
	GET)
		;;
	DELETE)
		curl_options="--request DELETE ${curl_options}"
		;;
	POST)
		FILE=$4
		curl_options="--request POST --location ${curl_options}"
		;;
	PUT)
		FILE=$4
		curl_options="--request PUT ${curl_options}"
		;;
	*)
		printf "Invalid method: %s\n" $METHOD
		;;
esac

if [ -n "$FILE" ]; then
	if [[ ! -f "$FILE" ]]; then
		printf "File not found: %s\n" $FILE
		exit 1
	fi
	curl_options="${curl_options} --data @${FILE}"
fi

cmd="$CURL_BIN $curl_options $url"
printf "$METHOD $url...\nCommand: $cmd\n\n"

response=$($cmd)
if [[ $? -ne 0 ]]; then
	echo "curl failed"
	exit 1
fi

if grep -q 'Error report' <<<$response; then
	echo $response
	exit 1
fi

exit 0

