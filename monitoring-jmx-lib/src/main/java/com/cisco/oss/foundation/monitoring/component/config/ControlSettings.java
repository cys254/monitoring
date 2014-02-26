/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.oss.foundation.monitoring.component.config;

/**
 * This configuration reflects how a component can be controlled.
 *
 * @author manojc
 */
public class ControlSettings {
    private StartCommandSettings startCommandSettings;
    private StopCommandSettings stopCommandSettings;
    private StatusCommandSettings statusCommandSettings;

    /**
     * @return the startCommandSettings
     */
    public StartCommandSettings getStartCommandSettings() {
        return startCommandSettings;
    }

    /**
     * @param startCommandSettings the startCommandSettings to set
     */
    public void setStartCommandSettings(StartCommandSettings startCommandSettings) {
        this.startCommandSettings = startCommandSettings;
    }

    /**
     * @return the stopCommandSettings
     */
    public StopCommandSettings getStopCommandSettings() {
        return stopCommandSettings;
    }

    /**
     * @param stopCommandSettings the stopCommandSettings to set
     */
    public void setStopCommandSettings(StopCommandSettings stopCommandSettings) {
        this.stopCommandSettings = stopCommandSettings;
    }

    /**
     * @return the statusCommandSettings
     */
    public StatusCommandSettings getStatusCommandSettings() {
        return statusCommandSettings;
    }

    /**
     * @param statusCommandSettings the statusCommandSettings to set
     */
    public void setStatusCommandSettings(StatusCommandSettings statusCommandSettings) {
        this.statusCommandSettings = statusCommandSettings;
    }
}
