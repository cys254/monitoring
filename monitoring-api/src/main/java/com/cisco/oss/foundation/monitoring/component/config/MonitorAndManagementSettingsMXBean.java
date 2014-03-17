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
