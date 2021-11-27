FROM openjdk:1.8
COPY ./ ./
RUN sed -i 's/\r$//' ./mvnw
RUN chmod +x ./mvnw
RUN ./mvnw clean install
#COPY "./target/prueba-tecnica-bcp-0.0.1.jar" "app.jar"
EXPOSE 8090
ENTRYPOINT ["java","-jar","./target/prueba-tecnica-bcp-0.0.1.jar"]