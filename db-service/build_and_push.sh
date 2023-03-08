mvn clean install
docker build . -t belpav/db-service:1.0.0
docker push belpav/db-service:1.0.0
