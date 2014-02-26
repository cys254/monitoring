/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring;

import javax.xml.bind.annotation.XmlType;

/**
 * Base interface that needs to be extended and implemented by applications that
 * needs to be monitored.
 *
 * @author manojc
 * @see com.cisco.vss.foundation.monitoring.MonitoringAgent
 * @since NDSMXAgent 1.0
 */
@XmlType
public interface MonitoringMXBean {
    /**
     * Name of the component.Name can be Acronym.
     */
    String getName();

    /**
     * Full name of the component.
     */
    String getFullName();

    /**
     * Instance Name format must be
     */
    String getInstance();

    /**
     * Component version
     */
    String getVersion();


}
