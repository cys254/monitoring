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

public class IncompatibleClassExceptionTest {

    @Test
    public void testIncompatibleClassExceptionString() {
        assertEquals("my message", new IncompatibleClassException("my message").getMessage());
    }

    @Test
    public void testIncompatibleClassExceptionStringThrowable() {
        Throwable th = new Exception("ex");
        IncompatibleClassException incompatibleClassException = new IncompatibleClassException("my message", th);
        assertEquals("my message", incompatibleClassException.getMessage());
        assertEquals(th, incompatibleClassException.getCause());
    }

    @Test
    public void testIncompatibleClassExceptionThrowable() {
        Throwable th = new Exception("ex");
        IncompatibleClassException incompatibleClassException = new IncompatibleClassException(th);
        assertEquals(th, incompatibleClassException.getCause());
    }
}
