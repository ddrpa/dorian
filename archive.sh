#!/bin/zsh

./mvnw clean
rm -f dist.zip
zip -r dist.zip . -x@compress-exclude