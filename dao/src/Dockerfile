FROM mysql:5.7.33

ENV MYSQL_ROOT_PASSWORD=my-secret-pw
ENV MYSQL_ROOT_HOST=%
ENV MYSQL_DATABASE=web1_db
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root

COPY ./scripts/ /docker-entrypoint-initdb.d/
