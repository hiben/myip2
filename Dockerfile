FROM alpine-jdk17-gradle8 AS build

COPY . /src/
WORKDIR /src

RUN gradle --no-daemon jar

FROM gcr.io/distroless/java17-debian12 AS distroless

COPY --from=build /src/build/libs/myip2.jar /opt/

CMD ["/opt/myip2.jar"]

EXPOSE 8080/tcp
