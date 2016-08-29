#!/bin/bash
set -ev
if [ "$TRAVIS_BRANCH" == "develop" ]; then
	echo "dev"
else
	echo "no dev"
fi
if [ ! -z "$TRAVIS_TAG" ] && [ "$TRAVIS_BRANCH" == "develop" ]; then 
	echo "go" 
else 
	echo "no go" 
fi