#!/usr/bin/env bash
set -e

podman run -d --name smartbar-redis \
  -p 6379:6379 \
  redis:latest

podman run -d --name smartbar-mongo \
  -p 27017:27017 \
  mongo:latest

podman run -d --name dynamodb-local --rm \
  -p 4566:8000 \
  public.ecr.aws/aws-dynamodb-local/aws-dynamodb-local:latest \
  -jar DynamoDBLocal.jar \
  -inMemory -sharedDb

