#!/bin/bash
set -ev
echo $TRAVIS_TAG
echo $TRAVIS_BRANCH
if [ ! -z "$TRAVIS_TAG" ] && [ "$TRAVIS_BRANCH" == "develop" ]; then
    docker login -u="$DOCKER_USER" -p="$DOCKER_PASS"
    docker build -t triberraar/triber-chat:latest -t triberraar/triber-chat:$TRAVIS_TAG .
    docker push triberraar/triber-chat
fi