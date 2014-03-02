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

public class AgentRegistrationExceptionTest {

    @Test
    public void testAgentRegistrationException() {
        assertEquals("GenericMXBean specific attributes doesn't conform to NDSMXAgent standards",
                new AgentRegistrationException().getMessage());
    }

    @Test
    public void testAgentRegistrationExceptionString() {
        assertEquals("my message", new AgentRegistrationException("my message").getMessage());
    }

    @Test
    public void testAgentRegistrationExceptionStringThrowable() {
        Throwable th = new Exception("ex");
        AgentRegistrationException agentRegistrationException = new AgentRegistrationException("my message", th);
        assertEquals("my message", agentRegistrationException.getMessage());
        assertEquals(th, agentRegistrationException.getCause());
    }

    @Test
    public void testAgentRegistrationExceptionThrowable() {
        Throwable th = new Exception("ex");
        AgentRegistrationException agentRegistrationException = new AgentRegistrationException(th);
        assertEquals(th, agentRegistrationException.getCause());
    }

}
