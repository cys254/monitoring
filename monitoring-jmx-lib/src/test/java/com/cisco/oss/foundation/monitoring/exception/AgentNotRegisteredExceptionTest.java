/*
 * Copyright 2014 Cisco Systems, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/* 
 * Created on Aug 24, 2010
 */

package com.cisco.oss.foundation.monitoring.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class AgentNotRegisteredExceptionTest {

    @Test
    public void testAgentNotRegisteredException() {
        assertEquals("Agent not registered", new AgentNotRegisteredException().getMessage());
    }

    @Test
    public void testAgentNotRegisteredExceptionString() {
        assertEquals("my message", new AgentNotRegisteredException("my message").getMessage());
    }

    @Test
    public void testAgentNotRegisteredExceptionStringThrowable() {
        Throwable th = new Exception("ex");
        AgentNotRegisteredException agentNotRegisteredException = new AgentNotRegisteredException("my message", th);
        assertEquals("my message", agentNotRegisteredException.getMessage());
        assertEquals(th, agentNotRegisteredException.getCause());
    }

    @Test
    public void testAgentNotRegisteredExceptionThrowable() {
        Throwable th = new Exception("ex");
        AgentNotRegisteredException agentNotRegisteredException = new AgentNotRegisteredException(th);
        assertEquals(th, agentNotRegisteredException.getCause());
    }

}
