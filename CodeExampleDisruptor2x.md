# Code Example for Disruptor 2.x #

The code below is an example of a single producer and single consumer using the convenience interface `EventHandler` for implementing a consumer.  The consumer runs on a separate thread receiving entries as they become available.

RingBuffer events as the item of data exchange.  These event objects as simple or complex as your application needs.
```
public final class ValueEvent
{
    private long value;

    public long getValue()
    {
        return value;
    }

    public void setValue(final long value)
    {
        this.value = value;
    }

    public final static EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>()
    {
        public ValueEvent newInstance()
        {
            return new ValueEvent();
        }
    };
}
```

Event processors implement this interface for convenience.
```
final EventHandler<ValueEvent> handler = new EventHandler<ValueEvent>()
{
    public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception
    {
        // process a new event.
    }
};
```

Setup the RingBuffer and barriers.
```
RingBuffer<ValueEvent> ringBuffer =
    new RingBuffer<ValueEvent>(ValueEntry.EVENT_FACTORY, 
                               new SingleThreadedClaimStrategy(RING_SIZE),
                               new SleepingWaitStrategy());

SequenceBarrier<ValueEvent> barrier = ringBuffer.newBarrier();       
BatchEventProcessor<ValueEvent> eventProcessor = new BatchEventProcessor<ValueEvent>(barrier, handler);
ringBuffer.setGatingSequences(eventProcessor.getSequence());  

// Each EventProcessor can run on a separate thread
EXECUTOR.submit(eventProcessor);
```

For most situations, the DSLWizard can be used to simplify the setup. The equivalent ring buffer and processor setup when using the DSLWizard would be:

```
Disruptor<ValueEvent> disruptor =
  new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY, EXECUTOR, 
                            new SingleThreadedClaimStrategy(RING_SIZE),
                            new SleepingWaitStrategy());
disruptor.handleEventsWith(handler);
RingBuffer<ValueEvent> ringBuffer = disruptor.start();
```

Publisher claims events in sequence for publishing to EventProcessors.
```
// Publishers claim events in sequence
long sequence = ringBuffer.next();
ValueEvent event = ringBuffer.get(sequence);

event.setValue(1234); // this could be more complex with multiple fields

// make the event available to EventProcessors
ringBuffer.publish(sequence);   
```

or with the Disruptor DSL:
```
disruptor.publishEvent(eventTranslator);
```
where eventTranslator is an instance of the class com.lmax.disruptor.EventTranslator. The translateTo method will be called to copy data into the supplied event from the ring buffer.