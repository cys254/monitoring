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

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.Set;

/**
 * Created by yogen on 18/11/2014.
 */
public class MonitoringAgentFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringAgentFactory.class);
    private static final Reflections reflections = new Reflections("com", "org");
    private static Set<Class<? extends MonitoringAgent>> subTypesOfMonitoringAgent = reflections.getSubTypesOf(MonitoringAgent.class);

    public static MonitoringAgent getInstance() {
        MonitoringAgent monitoringAgent = null;
        try {

            if (subTypesOfMonitoringAgent.isEmpty()) {
                throw new IllegalArgumentException("Can't find any implementations of MonitoringAgent in the classpath");
            } else {
                Class<? extends MonitoringAgent> next = subTypesOfMonitoringAgent.iterator().next();
                if (next.isEnum()) {
                    monitoringAgent = (MonitoringAgent) Enum.valueOf((Class<? extends Enum>) next, "INSTANCE");
                } else {
                    throw new IllegalArgumentException("MonitoringAgent implementations must be of enum type and have one insance called 'INSTANCE' to verify a singleton instance");
                }
                if (subTypesOfMonitoringAgent.size() > 1) {
                    LOGGER.warn("found more then one implementation of MonitoringAgent. Using: {}", next.getName());
                }
            }
        } catch (Exception e) {
            MonitoringImplementationNotFoundException exception = new MonitoringImplementationNotFoundException("could not find any implementation or monitoring-api lib", e);
            LOGGER.error(e.toString());
            throw exception;
        }

        return monitoringAgent;
    }
}
