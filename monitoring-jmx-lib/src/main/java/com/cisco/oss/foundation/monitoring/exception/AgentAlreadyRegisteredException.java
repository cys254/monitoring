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

package com.cisco.oss.foundation.monitoring.exception;

/**
 * @author manojc
 */
public class AgentAlreadyRegisteredException extends MonitoringAgentException {
    static final long serialVersionUID = -3387516993126546448L;
    private static final String attrExString = "Agent already registered";

    /**
     * Creates a new AgentAlreadyRegisteredException.
     */
    public AgentAlreadyRegisteredException() {
        super(attrExString);
    }

    /**
     * Creates a new AgentAlreadyRegisteredException.
     *
     * @param message
     */
    public AgentAlreadyRegisteredException(String message) {
        super(message);
    }

    /**
     * Creates a new AgentAlreadyRegisteredException.
     *
     * @param message
     * @param cause
     */
    public AgentAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new AgentAlreadyRegisteredException.
     *
     * @param cause
     */
    public AgentAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
