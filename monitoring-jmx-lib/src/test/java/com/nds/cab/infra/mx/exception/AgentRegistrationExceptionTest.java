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
