#!/bin/bash

docker pull wiremock/wiremock:2.35.0
docker run --name rococo-mock -p 8080:8080 -v C:/Users/k1r1l/IdeaProjects/rococo-diploma/wiremock/rest/mappings:/home/wiremock/mappings -d wiremock/wiremock:2.35.0 --global-response-templating --enable-stub-cors
