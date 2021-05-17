FROM adoptopenjdk/openjdk11:alpine
RUN mkdir /code_challenge
RUN mkdir /code_challenge/files
COPY ./app/build/libs/code_challenge.jar /code_challenge/code_challenge.jar
WORKDIR /code_challenge
ENTRYPOINT ["java", "-jar", "./code_challenge.jar"]
