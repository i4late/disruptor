# Introduction #

The Disruptor class provides a simple DSL-style API to make it simpler to setup event handlers and express the dependencies between them.

## Parallel Event Handlers ##

First create the wizard with the desired configuration for the ring buffer:

```
Disruptor<ValueEvent> disruptor =
  new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY, EXECUTOR,
                            new SingleThreadedClaimStrategy(RING_SIZE),
                            new SleepingWaitStrategy());
```

Note that we pass in an Executor instance which will be used to execute the event handlers in their own thread.

Then we add the event handlers which will process events in parallel:

```
disruptor.handleEventsWith(handler1, handler2, handler3, handler4);
```

And finally start the event handler threads and retrieve the configured RingBuffer:

```
RingBuffer<ValueEvent> ringBuffer = disruptor.start();
```

Producers can then use the RingBuffer's nextEvent and publish functions as normal to add events to the ring buffer.

## Dependencies ##

Dependencies between handlers can be expressed in the Disruptor by chaining them together, for example:

```
disruptor.handleEventsWith(handler1).then(handler2, handler3, handler4);
```

In this case handler 1 must process events first, with handler 2, 3 and 4 processing them in parallel after that.  Dependency chains can also be created, so to ensure each handler processes events in sequence:

```
disruptor.handleEventsWith(handler1).then(handler2).then(handler3).then(handler4);
```

Multiple chains can also be created:

```
disruptor.handleEventsWith(handler1).then(handler2);
disruptor.handleEventsWith(handler3).then(handler4);
```

## Using Custom EventProcessors ##

The most common usage of the Disruptor is to provide an EventHandler and have the Disruptor create a BatchEventProcessor instance automatically. In cases where the behaviour of BatchEventProcessor is not suitable, it is possible to use other types of EventProcessor as part of the dependency chain.

To set up a custom event processor to process events from the ring buffer:

```
RingBuffer<TestEvent> ringBuffer = disruptor.getRingBuffer();
SequenceBarrier barrier = ringBuffer.newBarrier();
final MyEventProcessor customProcessor = new MyEventProcessor(ringBuffer, barrier);
disruptor.handleEventsWith(processor);
disruptor.start();
```

The Disruptor will execute the custom processor when the start() method is called.  Then to require the custom processor handle events before a BatchEventHandler:

```
disruptor.after(customProcessor).handleEventsWith(anEventHandler);
```

Alternatively, to require a BatchEventHandler to process events before the custom processor, the SequenceBarrier can be created from the Disruptor:

```
SequenceBarrier barrier = disruptor.after(batchEventHandler1, batchEventHandler2).asBarrier();
final MyEventProcessor customProcessor = new MyEventProcessor(ringBuffer, barrier);
```

## Publishing Events ##

The Disruptor provides a convenience method to make publishing events to the ring buffer simpler  - publishEvent(EventTranslator).  For example a publisher could be written as:
```
public class MyPublisher implements EventTranslator, Runnable
{
  private Object computedValue;
  private Disruptor disruptor;

  public MyPublisher(Disruptor disruptor)
  {
    this.disruptor = disruptor;
  }

  public void run()
  {
    while (true)
    {
      computedValue = doLongRunningComputation();
      disruptor.publishEvent(this);
    }
  }

  public void translateTo(MyEvent event, long sequence)
  {
    event.setComputedValue(computedValue);
  }

  private Object doLongRunningComputation()
  {
    ...
  }
}
```