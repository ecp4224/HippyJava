HippyJava [![Build Status](https://drone.io/github.com/hypereddie10/HippyJava/status.png)](https://drone.io/github.com/hypereddie10/HippyJava/latest)
=========

HippyJava is a Java library for making bots for [HipChat][1]. It uses the [Smack API][2] to communicate with Hipchat's XMPP server and it also supports Hipchat's HTTP protocol.

Its easy to make a Hipchat bot in HippyJava!

## Installation

This package can be found the Boxtrot Studio maven repo. Add the following to your project's pom to add the repo

```
<repositories>
    <repository>
        <id>maven-central</id>
        <url>http://central.maven.org/maven2</url>
    </repository>
    ....
    <repository>
        <id>boxtrotstudio-repo</id>
        <url>https://repo.boxtrotstudio.com/maven</url>
    </repository>
</repositories>
```

or if you're using gradle

```
repositories {
    mavenCentral()
    maven { url "https://repo.boxtrotstudio.com/maven"}
}
```

once the repo is added, you can simply install the package by adding the following to your project's pom file

```
<dependencies>
    <dependency>
        <groupId>com.ep.hippyjava</groupId>
        <artifactId>HippyJava</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

or if you're using gradle

```
compile 'com.ep.hippyjava:HippyJava:1.0.0-SNAPSHOT'
```

## Current Features
* Multi-room Chatting [XMPP]
* Private Messaging [XMPP / HTTP]
* Retrieving User Data [HTTP]
* Notification Sending [HTTP]

## Getting Started/How-To
You can check out the [wiki][3] for tutorials/how-to's

## Contributing
If you would like to contribute to this project, simply fork the repo and send a pull request.
Please test your pull request and please try to keep your code neat.

## Building the Source
The source has a maven script for required dependencies, so just run 'mvn clean install' in the project folder.

[1]: http://hipchat.com/
[2]: http://www.igniterealtime.org/projects/smack/index.jsp
[3]: https://github.com/hypereddie10/HippyJava/wiki
