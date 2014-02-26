/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

/* 
 * Created on Aug 24, 2010
 */

package com.cisco.oss.foundation.monitoring.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class AgentAlreadyRegisteredExceptionTest {

    @Test
    public void testAgentAlreadyRegisteredException() {
        assertEquals("Agent already registered", new AgentAlreadyRegisteredException().getMessage());
    }

    @Test
    public void testAgentAlreadyRegisteredExceptionString() {
        assertEquals("my message", new AgentAlreadyRegisteredException("my message").getMessage());
    }

    @Test
    public void testAgentAlreadyRegisteredExceptionStringThrowable() {
        Throwable th = new Exception("ex");
        AgentAlreadyRegisteredException agentAlreadyRegisteredException = new AgentAlreadyRegisteredException(
                "my message", th);
        assertEquals("my message", agentAlreadyRegisteredException.getMessage());
        assertEquals(th, agentAlreadyRegisteredException.getCause());
    }

    @Test
    public void testAgentAlreadyRegisteredExceptionThrowable() {
        Throwable th = new Exception("ex");
        AgentAlreadyRegisteredException agentAlreadyRegisteredException = new AgentAlreadyRegisteredException(th);
        assertEquals(th, agentAlreadyRegisteredException.getCause());
    }

}
