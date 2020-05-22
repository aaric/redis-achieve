# redis-achieve

[![license](https://img.shields.io/badge/license-MIT-green.svg?style=flat&logo=github)](https://www.mit-license.org)
[![java](https://img.shields.io/badge/java-1.8-brightgreen.svg?style=flat&logo=java)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![gradle](https://img.shields.io/badge/gradle-6.3-brightgreen.svg?style=flat&logo=gradle)](https://docs.gradle.org/6.3/userguide/installation.html)
[![build](https://github.com/aaric/redis-achieve/workflows/build/badge.svg)](https://github.com/aaric/redis-achieve/actions)
[![release](https://img.shields.io/badge/release-0.4.1-blue.svg)](https://github.com/aaric/redis-achieve/releases)

> Redis Learning.

## Redis Shell

```bash
# select db
127.0.0.1:6379> select 0
# auth
127.0.0.1:6379> auth redis2019
# subscribe
127.0.0.1:6379> subscribe "message:channel"
# publish
127.0.0.1:6379> publish "message:channel" "hello world"
```
