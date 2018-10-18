#!/bin/sh

mvn package
echo "\r\n\r\b"
echo "TEST HELP"
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc help
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc help get
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc help post
echo "--------------------------------------------------------------------------------------------"
echo "TEST GET"
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc get 'http://httpbin.org/get?course=networking&assignment=1'
echo "curl http://httpbin.org/get?course=networking&assignment=1'\r\n"
curl 'http://httpbin.org/get?course=networking&assignment=1'
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc get -v -h Accept:*/* 'http://httpbin.org/get?course=networking&assignment=1'
echo "curl -v -H Accept:*/* 'http://httpbin.org/get?course=networking&assignment=1'\r\n"
curl -v -H Accept:*/* 'http://httpbin.org/get?course=networking&assignment=1'
echo "--------------------------------------------------------------------------------------------"
echo "TEST POST"
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc post -v -h Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post
echo "curl -v -H Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post\r\n"
curl -v -H Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc post -v -h Content-Type:application/json -f TestFile.txt http://httpbin.org/post
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc post -v -h Content-Type:application/json -d '{"Assignment": 1}' -f TestFile.txt http://httpbin.org/post
echo "--------------------------------------------------------------------------------------------"
echo "TEST REDDIRECTION"
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc get -v http://httpbin.org/status/301
echo "curl -v http://httpbin.org/status/301\r\n"
curl -v http://httpbin.org/status/301
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc post -v -d '{"Assignment": 1}' http://httpbin.org/status/301
echo "curl -v -d '{"Assignment": 1}' http://httpbin.org/status/301\r\n"
curl -v -d '{"Assignment": 1}' http://httpbin.org/status/301
echo "--------------------------------------------------------------------------------------------"
echo "TEST -o"
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc get -v 'http://httpbin.org/get?course=networking&assignment=1' -o GetBody.txt
echo "curl -v 'http://httpbin.org/get?course=networking&assignment=1' -o GetBody_curl.txt\r\n"
curl -v 'http://httpbin.org/get?course=networking&assignment=1' -o GetBody_curl.txt
echo "--------------------------------------------------------------------------------------------"
java -cp target/Assignment1-0.0.1-SNAPSHOT.jar httpc post -v -d '{"Assignment": 1}' http://httpbin.org/post -o PostBody.txt
echo "curl -v -d '{"Assignment": 1}' http://httpbin.org/post -o PostBody_curl.txt\r\n"
curl -v -d '{"Assignment": 1}' http://httpbin.org/post -o PostBody_curl.txt

