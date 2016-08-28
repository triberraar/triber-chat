# triber-chat [![Travis build status](https://img.shields.io/travis/triberraar/triber-chat/develop.svg)](https://travis-ci.org/triberraar/triber-chat)
A chat application written in Java and Javascript. See a running example at [triber-chat](https://triber-chat.herokuapp.com/).

Uses both rest and websockets (using stomp over sockjs).
## backend
[![Codacy code quality](https://img.shields.io/codacy/a94181c1ecb643dc9a6686dba37960c3/develop.svg)](https://www.codacy.com/app/geertolaerts/triber-chat/dashboard)
[![Codecov test coverage](https://img.shields.io/codecov/c/github/triberraar/triber-chat/develop.svg)](https://codecov.io/github/triberraar/triber-chat?branch=develop)

Written with help of Spring boot, Spring security, ....

## frontend
[![Coveralls test coverage](https://img.shields.io/coveralls/triberraar/triber-chat/develop.svg)](https://coveralls.io/github/triberraar/triber-chat?branch=develop)
[![Codacy code quality](https://img.shields.io/codacy/a94181c1ecb643dc9a6686dba37960c3/develop.svg)](https://www.codacy.com/app/geertolaerts/triber-chat/dashboard)
[![bitHound Code](https://www.bithound.io/github/triberraar/triber-chat/badges/code.svg?style=flat-square)](https://www.bithound.io/github/triberraar/triber-chat)
[![bitHound Overalll Score](https://www.bithound.io/github/triberraar/triber-chat/badges/score.svg?style=flat-square)](https://www.bithound.io/github/triberraar/triber-chat)
[![bitHound Dependencies](https://www.bithound.io/github/triberraar/triber-chat/badges/dependencies.svg?style=flat-square)](https://www.bithound.io/github/triberraar/triber-chat/develop/dependencies/npm)
[![bitHound Dev Dependencies](https://www.bithound.io/github/triberraar/triber-chat/badges/devDependencies.svg?style=flat-square)](https://www.bithound.io/github/triberraar/triber-chat/develop/dependencies/npm)

Written in AngularJs 1.5

## Configuration
The following spring environment variables are used:
* CAPTCHA_SECRET: the secret from the google recaptcha service
* CRYPTO_IV: cryptography IV, should be 16 characters
* CRYPTO_SECRET: cryptography IV, should be 16 characters
* SECURITY_JWT_SECRET: the secret for the jwt token
* SPRING_DATASOURCE_URL: url of the datasource, mysql or postgres
* SPRING_DATASOURCE_USERNAME: username of the datasource
* SPRING_DATASOURCE_PASSWORD: password of the datasource
* SPRING_MAIL_PASSWORD: password of the mail service
* SPRING_MAIL_USERNAME: username of the mail service
* MAIL_SENDER_SERVER_ADDRESS: the web address to be used in the mails send out, should be the address where this app is hosted

SPRING_MAIL_PASSWORD

## How to run
There are a few ways this app can be run

### Straigth with java
Like every Spring app this can be run just by executing java: java -jar *.jar

### With Docker
A docker file is provided. Build it like every docker file, with docker build -t <your tag> . See configuration for what properties to provide.

A docker compose file is also provided. It boots the app and the database, run with docker-compose up.  Fill in the environment file (.env). 