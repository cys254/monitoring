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
