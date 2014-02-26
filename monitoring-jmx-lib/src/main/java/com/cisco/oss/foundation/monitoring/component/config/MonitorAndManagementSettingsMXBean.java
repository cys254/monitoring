/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.oss.foundation.monitoring.component.config;

import javax.management.MXBean;

/**
 * This interface exposes several bits of information which eases monitoring
 * systems to easily discover and self configure how this component can be
 * monitored and managed.
 *
 * @author manojc
 */
@MXBean
public interface MonitorAndManagementSettingsMXBean {
    /**
     * @return The configuration which tells how this component can be
     * controlled remotely
     */
    ControlSettings getControlSettings();

    OtherSettings getOtherSettings();
}
