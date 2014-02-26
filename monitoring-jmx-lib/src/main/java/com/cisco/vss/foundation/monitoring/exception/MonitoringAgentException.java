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
public class MonitoringAgentException extends Exception {
    static final long serialVersionUID = -3323487893226546448L;

    /**
     * Creates a new MonitoringAgentException.
     *
     * @param message
     */
    public MonitoringAgentException(String message) {
        super(message);
    }

    /**
     * Creates a new MonitoringAgentException.
     *
     * @param message
     * @param cause
     */
    public MonitoringAgentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new MonitoringAgentException.
     *
     * @param cause
     */
    public MonitoringAgentException(Throwable cause) {
        super(cause);
    }
}
