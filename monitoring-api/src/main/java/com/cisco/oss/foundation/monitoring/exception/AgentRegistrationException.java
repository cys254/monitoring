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

package com.cisco.oss.foundation.monitoring.exception;

/**
 * @author manojc
 */
public class AgentRegistrationException extends MonitoringAgentException {
    static final long serialVersionUID = -3387588676987546448L;
    private static final String attrExString = "GenericMXBean specific attributes doesn't conform to FoundationMXAgent standards";

    /**
     * Creates a new AgentRegistrationException.
     */
    public AgentRegistrationException() {
        super(attrExString);
    }

    /**
     * Creates a new AgentRegistrationException.
     *
     * @param message
     */
    public AgentRegistrationException(String message) {
        super(message);
    }

    /**
     * Creates a new AgentRegistrationException.
     *
     * @param message
     * @param cause
     */
    public AgentRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new AgentRegistrationException.
     *
     * @param cause
     */
    public AgentRegistrationException(Throwable cause) {
        super(cause);
    }
}
