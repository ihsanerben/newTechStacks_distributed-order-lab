#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_FILE="${SCRIPT_DIR}/.env"

if [[ ! -f "${ENV_FILE}" ]]; then
    echo "Missing ${ENV_FILE}. Copy .env.example to .env and configure it."
    exit 1
fi

set -a
source "${ENV_FILE}"
set +a

cd "${SCRIPT_DIR}"
exec ./mvnw spring-boot:run
