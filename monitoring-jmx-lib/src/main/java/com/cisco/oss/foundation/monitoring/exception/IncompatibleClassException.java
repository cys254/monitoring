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
public class IncompatibleClassException extends MonitoringAgentException {
    static final long serialVersionUID = -3323487893226546448L;

    /**
     * Creates a new IncompatibleClassException.
     *
     * @param message
     */
    public IncompatibleClassException(String message) {
        super(message);
    }

    /**
     * Creates a new IncompatibleClassException.
     *
     * @param message
     * @param cause
     */
    public IncompatibleClassException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new IncompatibleClassException.
     *
     * @param cause
     */
    public IncompatibleClassException(Throwable cause) {
        super(cause);
    }
}
