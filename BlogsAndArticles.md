# How the Disruptor works and how to use it #

[What's so special about a ring buffer?](http://mechanitis.blogspot.com/2011/06/dissecting-disruptor-whats-so-special.html) - A summary by Trisha of the data structure at the heart of the Disruptor patter, how it's implemented and what's so great about it.

[How do I read from a ring buffer?](http://mechanitis.blogspot.com/2011/06/dissecting-disruptor-how-do-i-read-from.html) - Trisha gives an overview of the `Consumer` and `ConsumerBarrier`, which allows you to read stuff off the ring buffer.

[Writing to the ring buffer](http://mechanitis.blogspot.com/2011/07/dissecting-disruptor-writing-to-ring.html) - The third piece from Trisha explaining how to write to the ring buffer, and how it avoids wrapping.

[Lock-free publishing](http://blog.codeaholics.org/2011/the-disruptor-lock-free-publishing/) - Danny outlines the concepts behind putting items into the ring buffer.

[DSL for wiring up the Disruptor](http://www.symphonious.net/2011/07/11/lmax-disruptor-high-performance-low-latency-and-simple-too/) - Adrian came up with a cunning way to configure your Disruptor

[Disruptor wizard now part of the Disruptor](http://www.symphonious.net/2011/08/13/the-disruptor-wizard-is-dead-long-live-the-disruptor-wizard/) - Adrian's wizard now makes it easy to configure your very own Disruptor straight out of the box

[Disruptor version 2.0](http://mechanitis.blogspot.com/2011/08/disruptor-20-all-change-please.html) - Trisha outlines the cosmetic changes to the Disruptor in version 2.0.

[Sharing Data Among Threads Without Contention](http://www.oraclejavamagazine-digital.com/javamagazine/20120304/?pg=56&pm=1&u1=friend) - An updated and summarised version of Trisha's blogs in Oracle's Java Magazine.

# Why the Disruptor is so fast #

[Locks Are Bad](http://mechanitis.blogspot.com/2011/07/dissecting-disruptor-why-its-so-fast.html) - Trisha gives some basic concurrency background and explains why locks are evil.

[Magic cache line padding](http://mechanitis.blogspot.com/2011/07/dissecting-disruptor-why-its-so-fast_22.html) - An explanation around why the odd cache line padding variables are required, by Trisha.

[Demystifying Memory Barriers](http://mechanitis.blogspot.com/2011/08/dissecting-disruptor-why-its-so-fast.html) - Trisha attempts to explain why memory barriers are important in the Disruptor.

# What some other people have written about the Disruptor #

[LMAX Architecture](http://martinfowler.com/articles/lmax.html) by Martin Fowler.

Processing [1m TPS](http://blog.jteam.nl/2011/07/20/processing-1m-tps-with-axon-framework-and-the-disruptor/) with the Axon Framework using the Disruptor.

Interview on the [Disributed Podcast](http://distributedpodcast.com/2012/episode-12-lmax) about LMAX and the Disruptor