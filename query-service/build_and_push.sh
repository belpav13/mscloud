mvn clean install  -DskipTests
docker build . -t belpav/mscloud-queryservice:latest
docker push belpav/mscloud-queryservice:latest
