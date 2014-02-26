/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring;

/**
 * Represents the mode in which application is running.
 *
 * @author manojc
 */
public enum RedundancyMode {
    /**
     * Represents this instance of application is running as Master.
     */
    Master,
    /**
     * Represents this instance of application is running as Slave.
     */
    Slave,
    /**
     * Represents a Standalone instance of application.
     */
    StandAlone;
}
