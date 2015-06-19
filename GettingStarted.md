# Getting Started #
**1. Check out the project locally to your machine**
```
    > cd ${MY_PROJECTS_HOME}
    > svn checkout http://disruptor.googlecode.com/svn/trunk/ disruptor-read-only
```

**2. Build a distribution**
```
    > cd ${MY_PROJECTS_HOME}/disruptor-read-only/code
    > ant
```

As a result of the build you should find the following files:
```
    ${MY_PROJECTS_HOME}/disruptor-read-only/code/target/dist/disruptor.jar
    ${MY_PROJECTS_HOME}/disruptor-read-only/code/target/dist/disruptor-api.jar
    ${MY_PROJECTS_HOME}/disruptor-read-only/code/target/dist/disruptor-src.jar
```

**3. Run the performance tests**
```
    > cd ${MY_PROJECTS_HOME}/disruptor-read-only/code
    > ant throughput:test
    > ant latency:test
```