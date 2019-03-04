echo ---Fly.SpringCloud.UserService----
cd ../services/fly-user-service/target/
java -jar fly-user-service-1.0-SNAPSHOT.jar --server.port=8281 --spring.cloud.config.profile=default
