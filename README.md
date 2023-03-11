# listing-details-filtered-pages

1. Preparation:
  if image does not exist or need recompile after any changes, for current module run **[module]\ ./build_and_push.sh**
  will be created image and put docker hub.
1.1 Optional: for create all images with one command, run jib build: **mscloud\mvn clean compile jib:build**
2. Run: **mscloud\docker-compose up -d**
3. Open Keycloak. _http://keycloak:8080/_
    travel into...
4. Configure Postman:
   add/generate token from _http://keycloak:8080/realms/mscloud-realm_

5. Upload csv by POST action
   **curl --location 'http://localhost:8081/upload' \
   --form 'file=@"[path2file]/listing-details.csv"'**
![img_1.png](../../../../dev/workspaces/javaWorkSpace/IdeaProjects/listing-details-filtered-pages/img_1.png)
6. Open H2 in browser and check table.(optional)
   _http://localhost:8084/h2/_
![img.png](../../../../dev/workspaces/javaWorkSpace/IdeaProjects/listing-details-filtered-pages/img.png)
7. Run Query by Get action  (restemplate)
   **curl --location 'http://localhost:8080/listings?
                                          minPrice=0.0081&
                                          maxPrice=0.15&
                                          minMinCpm=1&
                                          maxMinCpm=2& 
                                          pageNumber=1&
                                          pageSize=20'**
    
    will be delivered json of second page with 20 blocks.
![img_2.png](../../../../dev/workspaces/javaWorkSpace/IdeaProjects/listing-details-filtered-pages/img_2.png)
.....
![img_3.png](../../../../dev/workspaces/javaWorkSpace/IdeaProjects/listing-details-filtered-pages/img_3.png)

8. Run Query by Get action (webclient)
    **curl --location 'http://localhost:8080/query?
                                        minPrice=0.0081&
                                        maxPrice=0.15&
                                        minMinCpm=1&
                                        maxMinCpm=2&
                                        pageNumber=1&
                                        pageSize=20'**
9. Open discovery server _http://eureka:password@discovery-server:8761/eureka_
   travel into...
10. Open Zipkin host. _http://zipkin:9411_    travel into...

11. Open Prometheus host on port **9090**. add queries.
12. Open Grafana host on port **3000**. add Prometheus data and design from **mscloud\Grafana_Dashboard.json** 
    travel into...