/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
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
