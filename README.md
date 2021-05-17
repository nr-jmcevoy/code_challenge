# Overview

This project is designed to run from the command line and when given text(s) will return a list of the 100 most common three word sequences.

The project should also accept input from stdin for example: `cat my_file.txt | ./my_executable"


## Prerequesets

This project was built to target Java 11.

This project uses [Gradle](https://gradle.org/) to build and package the project. Please ensure Gradle is installed on your machine.

To run this project as a container please ensure that (docker)[https://www.docker.com/get-started] is installed on your machine.

## Build

To build the project `cd` into the root directory and run `./gradlew build`.

This should run all tests and build the project. This will also generate an executable `jar` file called `code_challenge.jar` located in `./app/build/libs`.

## Running with Gradle

To run the program with Gradle use the `./gradlew run` command.

To feed file paths to the program with gradle simply append the full file path(s) to `--args`:

```
./gradlew run --args="full/path/to/your/text/file.txt"
```

For multiple file paths simply pass in multiple space separated paths.

```
./gradlew run --args="full/path/to/your/text/file1.txt full/path/to/your/text/file2.txt"
```

To read from stdin, pipe the output to the Gradle as below:

```
cat my_file.txt | ./gradlew run
```

## Running the JAR directly

The `.jar` file generated by `./gradlew build` is named `code_challenge.jar` and is located in `./app/build/libs`.

To run the .jar file with a file path simply pass in the file path as below:

```
java -jar ./app/build/libs/code_challenge.jar path/to/my/file.txt
```

To read multiple files simple pass in the paths as below:

```
java -jar ./app/build/libs/code_challenge.jar path/to/my/file1.txt path/to/my/file2.txt
```

To read from stdin, pipe the content to the `.jar` as below:

```
cat my_file.txt | java -jar ./app/build/libs/code_challenge.jar
```

## Running with Docker

Before following these steps please ensure that you have run `./gradlew build` first.

To build the container run the following command:

```
docker build -t code_challenge .
```

Once built you can read from stdin as follows:

```
cat my_file.txt | docker run -i code_challenge
```

To read a file from outside the container we need to create a docker volume at runtime.

You can map the folder containing the text files in your local file system you wish to send to the program.

This folder will be mounted to `/code_challenge/files` in the Docker container.

You can then pass the path of the file inside the volume to the executable. The working directory of the docker container is `/code_challenge` so we can simply pass `./files/my_file.txt` as the path.

Example below:

```
docker run -i -v full/path/to/local/folder/containing/your/files:/code_challenge/files code_challenge ./files/my_file.txt
```

## Considerations

The program will accept either a single input from stdin or one/many file paths as command line arguments. The program will not accept both a stdin input and a file path at the same time.

## What you would do next, given more time (if anything)?

I would refactor the file reading code, probably making it more decoupled and allow for easier testing. I would research into the best way to write tests when using files in Java.

I would refactor the code to allow word sequences of a user specified size rather than simply 3 hardcoded.

I would refactor to allow a user specified number of results to print rather than the hard coded 100.

I would have written more test cases for non English Unicode characters, although depending on how granular this could end up becoming quite cumbersome/time consuming depending on languages/number of characters tested.

I didn't get around to the AWS task due to time constraints. I would probably have packaged/deployed as a Lambda service and refactor the code to possibly download files from S3 for example and then read them based on strings sent to the Lambda service via HTTP.

This could be done with something like Terraform or AWS CloudFormation.

If given more time in general I would have liked to have refactored the code into smaller methods with less coupling and written more test cases.
