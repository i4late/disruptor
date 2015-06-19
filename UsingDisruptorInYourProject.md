# Using disruptor in your project #

There are several options on how to use the disruptor as a dependency in one of your projects. These are a few hints and code sinppets on how to use it in conjunction with various build systems.

## Manual dependency management ##

JAR files containing the compiled classes, sources and javadoc documentation are available from the [download page](http://code.google.com/p/disruptor/downloads/list). You can use these with any kind of build system.

## Dependency management using maven central repository ##

Disruptor JARs (artifacts) are available from maven central repository from version 2.7.1. Disruptor uses group id: com.googlecode.disruptor and artifact id: disruptor.

Below are code snippets for usage in popular build systems:

### Maven ###

Add the following snippet to dependencies section of your pom.xml file
```
<dependency>
    <groupId>com.googlecode.disruptor</groupId>
    <artifactId>disruptor</artifactId>
    <version>2.7.1</version>
</dependency>
```

### Gradle ###

Enable dependency resolution using maven central repository:
```
repositories {
    mavenCentral()
}
```

Then add disruptor to the dependencies section:
```
dependencies {
    compile "com.googlecode.disruptor:disruptor:2.7.1"
}
```

### Ivy ###

Add following line to the ivy.xml:
```
<dependency org="com.googlecode.disruptor" name="disruptor" rev="2.7.1"/>
```