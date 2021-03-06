#!/bin/bash
set -e

# This script is specific to DSP's jenkins CI server.
# Although this script lives in a `jenkins` directory, it is intended to be run from `automation`.
# It is here because it is only intended to be run from a jenkins job and not by developers.

FC_INSTANCE=${1:-$FC_INSTANCE}
ENV=${2:-$ENV}
TEST_IMAGE=automation-ontology

mkdir target

# Render Configurations
docker run --rm \
    -e ENVIRONMENT=${ENV} \
    -e ROOT_DIR="${PWD}" \
    -e FC_INSTANCE=${FC_INSTANCE} \
    -e OUT_PATH=/output/src/test/resources \
    -e INPUT_PATH=/input \
    -v /etc/vault-token-dsde:/root/.vault-token \
    -v "${PWD}/configs":/input \
    -v "${PWD}":/output \
    broadinstitute/dsde-toolbox:dev render-templates.sh


# Build Test Image
docker build -f Dockerfile -t ${TEST_IMAGE} .

# Run Tests
if docker run -v "${PWD}/target":/app/target ${TEST_IMAGE}
then
    TEST_EXIT_CODE=$?
else
    TEST_EXIT_CODE=$?
fi

# Parse Tests
ls "${PWD}/scripts"
docker run -v "${PWD}/scripts":/working -v "${PWD}/target":/working/target -w /working broadinstitute/dsp-toolbox python interpret_results.py

# exit with exit code of test script
exit $TEST_EXIT_CODE
