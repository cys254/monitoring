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
