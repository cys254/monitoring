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
 * This settings reflects how a component can be started remotely.
 *
 * @author manojc
 */
public class StartCommandSettings {
    private String command;
    private MatchPolicy matchPolicy;
    private String successIndication;

    /**
     * @return The start script command using which this component can be
     * started
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets the Start script command using which this component can be started.
     *
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return The MatchPolicy that has to be applied on
     * {@link #getSuccessIndication()} to confirm if the component is
     * started successfully
     */
    public MatchPolicy getMatchPolicy() {
        return matchPolicy;
    }

    /**
     * Sets the MatchPolicy that has to be applied on
     * {@link #getSuccessIndication()} to confirm if the component is started
     * successfully.
     *
     * @param matchPolicy
     */
    public void setMatchPolicy(MatchPolicy matchPolicy) {
        this.matchPolicy = matchPolicy;
    }

    /**
     * @return The text output of the component's Start script command which
     * says the component is started successfully.
     */
    public String getSuccessIndication() {
        return successIndication;
    }

    /**
     * Sets the text output of the component's Start script command which says
     * the component is started successfully.
     *
     * @param successIndication
     */
    public void setSuccessIndication(String successIndication) {
        this.successIndication = successIndication;
    }
}
