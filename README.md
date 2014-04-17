INTRODUCTION
============

This example shows groovy way to use Apache Camel, Spring Framework and Mustache templates.
It implements RSS feed digest. Every 10 minutes RSS feed is pulled and digest prepared and saved to file.

REQUIRED
============
- JDK7
- Linux Shell

HOW TO RUN
==========

    ./gradlew run

If you have gradle installed:

    gradle run


CUSTOMIZE
---------

Digest mustache template:
    /src/main/resources/rssdigest.mustache


RSS Feed Uri:
    /src/main/resources/rssdigest.properties


REFERENCES
==========

http://gvmtool.net/
    the Groovy enVironment Manager to install groovy and gradle

https://camel.apache.org/
    Apache Camel

http://spring.io/
    Spring Framework

