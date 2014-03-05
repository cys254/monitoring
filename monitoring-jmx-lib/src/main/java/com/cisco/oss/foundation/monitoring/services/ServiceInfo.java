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

package com.cisco.oss.foundation.monitoring.services;

import com.cisco.oss.foundation.monitoring.MonitoringAgent;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum ServiceInfo implements ServiceInfoMXBean {

    INSTANCE;

    private ServiceInfo() {

    }

    public static ServiceInfo getServiceInfo() {
//		if(serviceInfo == null)
//			serviceInfo = new ServiceInfo();
//		return serviceInfo;
        return INSTANCE;
    }

    @Override
    public List<Service> getServices() {

        Map<Service,ServiceImp> services = MonitoringAgent.serviceActorImpl.services;
        return (List)Lists.newArrayList(services.values());


    }


}
