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
 * Created on Aug 26, 2010
 */

package com.cisco.oss.foundation.monitoring.component;

import java.util.Date;
import java.util.Map;

import com.cisco.oss.foundation.monitoring.MonitoringMXBean;

public interface AllValidDataMXBean extends MonitoringMXBean {
    Integer getIntWrapper();

    int getInt();

    float getFloat();

    Float getFloatWrapper();

    double getDouble();

    Double getDoubleWrapper();

    Date getDate();

    String getCommandLine();

    Severity getEnum();

    Request getCompositeObject();

    Request getNullObject();

    Request[] getCompositeObjectArray();

    Map<Integer, String> getStringMap();

    Map<Integer, Request> getObjectMap();
}
