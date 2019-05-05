FROM node:slim

ENV MEMEBOOK_VARIANT=dogs
ENV SERVER_PORT=3000

WORKDIR /app/
COPY memebook-expressjs /app/
RUN npm install

EXPOSE 3000

ENTRYPOINT [ "node", "./bin/www" ]