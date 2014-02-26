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
public class AgentNotRegisteredException extends MonitoringAgentException {
    static final long serialVersionUID = -3387516993126546448L;
    private static final String attrExString = "Agent not registered";

    /**
     * Creates a new AgentNotRegisteredException.
     */
    public AgentNotRegisteredException() {
        super(attrExString);
    }

    /**
     * Creates a new AgentNotRegisteredException.
     *
     * @param message
     */
    public AgentNotRegisteredException(String message) {
        super(message);
    }

    /**
     * Creates a new AgentNotRegisteredException.
     *
     * @param message
     * @param cause
     */
    public AgentNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new AgentNotRegisteredException.
     *
     * @param cause
     */
    public AgentNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
