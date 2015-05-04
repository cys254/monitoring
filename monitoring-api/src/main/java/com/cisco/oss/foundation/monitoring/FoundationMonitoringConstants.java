/*
 * Copyright 2015 Cisco Systems, Inc.
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

/**
 * Created by Yair Ogen on 3/17/14.
 */
public class FoundationMonitoringConstants {

    public static final String DOMAIN_NAME = "foundation.mx";
    public static final String MX_PORT = "service.mxagentRegistry.port";
    public static final String EXPORTED_PORT = "service.mxagentRegistry.innerPort";
    public static final String MONITOR_ENABLED = "service.mxagentRegistry.monitoringEnabled";
    public static final String CALCULATIONS_WINDOW = "service.mxagentRegistry.statisticsCalculationWindow";
    public static final String RMIREGISTRY_MAXHEAPSIZE = "service.mxagentRegistry.rmiregistryMaxHeapSize";
    public static final String IN_PROC_RMI = "service.mxagentRegistry.inProcess";
    public static final String AGENT_VERSION = "3.1.0-0";
    public static final String APP_NAME_NAMING_STANDARD = "^[a-zA-Z](([ a-zA-Z0-9_-])*[a-zA-Z0-9_-])*$";
    public static final String APP_INSTANCE_NAMING_STANDARD = "^[a-zA-Z0-9](([ a-zA-Z0-9_-])*[a-zA-Z0-9_-])*$";

}
