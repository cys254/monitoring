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

package com.cisco.oss.foundation.monitoring;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void testConfiguration() {
        Configuration configuration = new Configuration();
        assertEquals(3000, configuration.getAgentPort());
        assertEquals(4000, configuration.getExportedPort());
    }

    @Test
    public void testSetAgentPort() {
        Configuration configuration = new Configuration();
        configuration.setAgentPort(3005);
        assertEquals(3005, configuration.getAgentPort());
    }

    @Test
    public void testSetExportedPort() {
        Configuration configuration = new Configuration();
        configuration.setExportedPort(4005);
        assertEquals(4005, configuration.getExportedPort());
    }

}
