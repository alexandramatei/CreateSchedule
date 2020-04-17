FROM amazoncorretto:11

COPY build/libs/CreateSchedule-0.0.1-SNAPSHOT.jar .

EXPOSE 8084

CMD ["java", "-jar", "/CreateSchedule-0.0.1-SNAPSHOT.jar" ]

