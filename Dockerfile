FROM maven:3.6-slim AS DownloadDependencies
LABEL author="Anderson Marques"
WORKDIR /app
COPY pom.xml .
RUN [ "mvn", "clean", "validate", "dependency:resolve"]
#
FROM DownloadDependencies AS Build
WORKDIR /app
COPY src src
RUN [ "mvn", "test", "install", "dependency:resolve" ]
#
FROM payara/server-full AS Server
COPY --from=Build /app/target/websockets-jee7.war $DEPLOY_DIR
