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

package com.cisco.vss.foundation.monitoring.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class MonitoringAgentExceptionTest {

    @Test
    public void testMonitoringAgentExceptionString() {
        assertEquals("my message", new MonitoringAgentException("my message").getMessage());
    }

    @Test
    public void testMonitoringAgentExceptionStringThrowable() {
        Throwable th = new Exception("ex");
        MonitoringAgentException monitoringAgentException = new MonitoringAgentException("my message", th);
        assertEquals("my message", monitoringAgentException.getMessage());
        assertEquals(th, monitoringAgentException.getCause());
    }

    @Test
    public void testMonitoringAgentExceptionThrowable() {
        Throwable th = new Exception("ex");
        MonitoringAgentException monitoringAgentException = new MonitoringAgentException(th);
        assertEquals(th, monitoringAgentException.getCause());
    }

}
