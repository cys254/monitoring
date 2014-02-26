package com.cisco.vss.foundation.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MasterSlaveInfo {

    static final Logger LOGGER = LoggerFactory.getLogger(MasterSlaveInfo.class.getName());

    public static void setRedundancyMode(RedundancyMode redundancyMode) {

        AppProperties.redundancyModeChanged(redundancyMode);
        LOGGER.info("Redundancy Mode switched to :" + redundancyMode);
    }

}
