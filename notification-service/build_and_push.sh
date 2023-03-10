mvn clean install  -DskipTests
docker build . -t belpav/mscloud-notifyservice:latest
docker push belpav/mscloud-notifyservice:latest
