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

package com.cisco.oss.foundation.monitoring;

import com.cisco.oss.foundation.monitoring.component.data.ComponentInfo;

public class DefaultMonitoringMXBean implements MonitoringMXBean {



    @Override
    public String getName() {
        return ComponentInfo.INSTANCE.getName();
    }

    @Override
    public String getFullName() {
        return ComponentInfo.INSTANCE.getFullName();
    }

    @Override
    public String getInstance() {
        return ComponentInfo.INSTANCE.getInstance();
    }

    @Override
    public String getVersion() {

        return ComponentInfo.INSTANCE.getVersion();
    }




}
