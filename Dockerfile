FROM phusion/baseimage:latest

#base setup
RUN apt-get update \
    && apt-get install -y openjdk-7-jre supervisor \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* 

#Configs
COPY consent-autocomplete/target/autocomplete.jar /opt/consent-autocomplete.jar

CMD ["/usr/bin/java -jar /opt/consent-autocomplete.jar server /opt/consent-autocomplete.yml"]
