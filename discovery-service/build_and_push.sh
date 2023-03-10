mvn clean install  -DskipTests
docker build . -t belpav/mscloud-discoveryservice:latest
docker push belpav/mscloud-discoveryservice:latest
