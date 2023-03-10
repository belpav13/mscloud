mvn clean install  -DskipTests
docker build . -t belpav/mscloud-dbservice:latest
docker push belpav/mscloud-dbservice:latest
