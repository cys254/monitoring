/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring.exception;

/**
 * @author manojc
 */
public class AgentRegistrationException extends MonitoringAgentException {
    static final long serialVersionUID = -3387588676987546448L;
    private static final String attrExString = "GenericMXBean specific attributes doesn't conform to NDSMXAgent standards";

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
