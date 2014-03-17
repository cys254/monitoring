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
 * This settings reflects how a component can be checked if it is running or not.
 *
 * @author manojc
 */
public class StatusCommandSettings {
    private String command;
    private MatchPolicy matchPolicy;
    private String upIndication;
    private String downIndication;

    /**
     * @return The status script command using which this component can be
     * checked if it is running or not
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets the status script command using which this component can be checked
     * if it is running or not.
     *
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return The MatchPolicy that has to be applied on
     * {@link #getUpIndication()} and {@link #getDownIndication()} to
     * confirm if the component is running or not running respectively
     */
    public MatchPolicy getMatchPolicy() {
        return matchPolicy;
    }

    /**
     * Sets the MatchPolicy that has to be applied on {@link #getUpIndication()}
     * and {@link #getDownIndication()} to confirm if the component is running
     * or not running respectively.
     *
     * @param matchPolicy
     */
    public void setMatchPolicy(MatchPolicy matchPolicy) {
        this.matchPolicy = matchPolicy;
    }

    /**
     * @return The text output of the component's Status script command which
     * says the component is UP and running
     */
    public String getUpIndication() {
        return upIndication;
    }

    /**
     * Sets the text output of the component's Status script command which says
     * the component is UP and running.
     *
     * @param upIndication
     */
    public void setUpIndication(String upIndication) {
        this.upIndication = upIndication;
    }

    /**
     * @return The text output of the component's Status script command which
     * says the component is DOWN
     */
    public String getDownIndication() {
        return downIndication;
    }

    /**
     * Sets the text output of the component's Status script command which says
     * the component is DOWN.
     *
     * @param downIndication
     */
    public void setDownIndication(String downIndication) {
        this.downIndication = downIndication;
    }
}
