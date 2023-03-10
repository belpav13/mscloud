mvn clean install  -DskipTests
docker build . -t belpav/mscloud-apigateway:latest
docker push belpav/mscloud-apigateway:latest
