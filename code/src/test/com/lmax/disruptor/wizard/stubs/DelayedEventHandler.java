/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lmax.disruptor.wizard.stubs;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.support.TestEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class DelayedEventHandler implements EventHandler<TestEvent>
{
    private AtomicBoolean readyToProcessEvent = new AtomicBoolean(false);
    private volatile boolean stopped = false;

    @Override
    public void onEvent(final TestEvent entry, final boolean endOfBatch) throws Exception
    {
        waitForAndSetFlag(false);
    }

    public void processEvent()
    {
        waitForAndSetFlag(true);
    }

    public void stopWaiting()
    {
        stopped = true;
    }

    private void waitForAndSetFlag(final boolean newValue)
    {
        while (!stopped && !Thread.interrupted() && !readyToProcessEvent.compareAndSet(!newValue, newValue))
        {
            Thread.yield();
        }
    }
}
