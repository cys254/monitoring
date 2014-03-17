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

import com.cisco.oss.foundation.monitoring.ConnectionStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yair Ogen on 3/5/14.
 */
public class ServiceActorImpl  implements ServiceActor{
    protected Map<Service, ServiceImp> services = new HashMap<>();

    public void startTransaction(Service service, int usedThreads){
        ServiceImp serviceImp = null;
        if (services.containsKey(service)) {
            serviceImp = services.get(service);
        } else {
            serviceImp = (ServiceImp) service;
            services.put(service, serviceImp);
        }
        serviceImp.usedThreads = usedThreads;
        serviceImp.totalRequestCount++;

        serviceImp.getTotalStatisticsRequestCount().incrementAndGet();
        serviceImp.lastTransactionStartTime = new Date();
    }

    public void endTransaction(Service service, boolean isFailed, String description){
        ServiceImp serviceImp = services.get(service);
        serviceImp.lastTransactionEndTime = new Date();
        serviceImp.tpsHistogram.update(serviceImp.getTotalRequestCount());

        if (isFailed == true) {
            serviceImp.lastFailedRequestTime = new Date();
            serviceImp.failedRequestCount++;
            serviceImp.lastFailedRequestDescription = description;

        } else {
            serviceImp.lastSuccessfulRequestTime = new Date();
        }
        if (serviceImp.getLastFailedRequestTime() == null || (serviceImp.getLastSuccessfulRequestTime().getTime() > serviceImp.getLastFailedRequestTime().getTime())) {
            serviceImp.connectionStatus = ConnectionStatus.Healthy;
        } else {
            serviceImp.connectionStatus = ConnectionStatus.Broken;
        }
        long end = System.currentTimeMillis();
        serviceImp.lastTransactionProcessingTime = end - serviceImp.lastTransactionStartTime.getTime();
    }
}
