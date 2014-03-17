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
