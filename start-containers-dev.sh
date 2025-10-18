#!/usr/bin/env bash
set -e

podman run -d --name smartbar-redis \
  -p 6379:6379 \
  redis:latest