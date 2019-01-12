FROM java:8
WORKDIR /
ADD githubissues.jar githubissues.jar
EXPOSE 8080
CMD ["java", "-jar", "githubissues.jar"]
