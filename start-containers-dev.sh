#!/usr/bin/env bash
set -e

podman network create soc || true

podman run -d --rm --name smartbar-redis \
  --network soc \
  -p 6379:6379 \
  redis:latest

podman run -d --rm --name smartbar-mongo \
  --network soc \
  -p 27017:27017 \
  mongo:latest

podman run -d --name dynamodb-local --rm \
  --network soc \
  -p 4566:8000 \
  public.ecr.aws/aws-dynamodb-local/aws-dynamodb-local:latest \
  -jar DynamoDBLocal.jar \
  -inMemory -sharedDb

podman run -d --rm --name smartbar-orderclient-dev \
  --network soc \
  -e QUARKUS_PROFILE=dev \
  -e QUARKUS_SWAGGER_UI_ALWAYS_INCLUDE=true \
  -e QUARKUS_REST_CLIENT__ORG_LUNSKRA_MENU_MENUAPICLIENT__URL="http://host.containers.internal:8088" \
  -e QUARKUS_MONGODB_CONNECTION_STRING="mongodb://smartbar-mongo:27017" \
  -e RABBITMQ_HOST="host.containers.internal" \
  -e RABBITMQ_PORT="5672" \
  -e RABBITMQ_USERNAME="smartbar" \
  -e RABBITMQ_PASSWORD="smartbar" \
  -p 8080:8080 \
  smartbar-orderclient:dev
