FROM openjdk:11
RUN mkdir -p /var/deployment/apps/
RUN git -C /var/deployment/apps clone --branch keeplly-auth https://gitlab-ci-token:glpat-zWy1kfCWucJu6zFzzgVh@gitlab.com/shadeau99/keeplly-jars.git .
WORKDIR /var/deployment/apps/
RUN git fetch
RUN git pull origin keeplly-auth
RUN touch /var/deployment/apps/keepllyAuth/nohup.txt
RUN ["chmod", "+x", "/var/deployment/apps/keepllyAuth/startup.sh"]
EXPOSE 8087
#ENTRYPOINT ["java","-jar","/ioldocgen-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["/bin/bash","-c", "/var/deployment/apps/keepllyAuth/startup.sh"]
