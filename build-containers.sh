#!/usr/bin/env bash
set -e

# smartbar back office
## Build container
podman build -f src/main/docker/Dockerfile.jvm -t smartbar-orderclient:dev .
