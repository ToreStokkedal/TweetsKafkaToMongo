# Use the Official OpenJDK image for a lean production stage of our multi-stage build.
# https://hub.docker.com/_/openjdk
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM openjdk:11

# Copy local code to the container image.
COPY /target/TweetsKafkaToMongo-0.1.jar /TweetsKafkaToMongo-0.1.jar
COPY /target/lib/* /lib/
COPY ./startservices.sh /startservices.sh

# stat the services in the image (moved from startservices.sh to Java directly)
CMD java -jar TweetsKafkaToMongo-0.1.jar

# Copy to rhel1
# scp -r ./* tores@rhel1.local:./TweetsKafkaToMongo
# to build; docker build . -t k2m
#           podman build . -t k2m
# to run: docker run  -name k2m k2m
#         podman run  -d --name k2m k2m