#!/bin/bash
set -e

host="$1"
port="$2"
timeout="${3:-30}"

echo "Waiting for Redis at $host:$port (timeout: ${timeout}s)..."

for ((i=0; i<timeout; i++)); do
  if redis-cli -h "$host" -p "$port" ping | grep -q PONG; then
    echo "Redis is up!"
    exit 0
  fi
  sleep 1
done

echo "Timeout waiting for Redis at $host:$port"
exit 1

