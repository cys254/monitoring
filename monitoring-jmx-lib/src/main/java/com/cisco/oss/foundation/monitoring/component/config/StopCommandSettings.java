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
 * This settings reflects how a component can be stopped remotely.
 *
 * @author manojc
 */
public class StopCommandSettings {
    private String command;
    private MatchPolicy matchPolicy;
    private String successIndication;

    /**
     * @return The stop script command using which this component can be stopped
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets the Stop script command using which this component can be stopped.
     *
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return The MatchPolicy that has to be applied on
     * {@link #getSuccessIndication()} to confirm if the component is
     * stopped successfully
     */
    public MatchPolicy getMatchPolicy() {
        return matchPolicy;
    }

    /**
     * Sets the MatchPolicy that has to be applied on
     * {@link #getSuccessIndication()} to confirm if the component is stopped
     * successfully.
     *
     * @param matchPolicy
     */
    public void setMatchPolicy(MatchPolicy matchPolicy) {
        this.matchPolicy = matchPolicy;
    }

    /**
     * @return The text output of the component's Stop script command which says
     * the component is stopped successfully
     */
    public String getSuccessIndication() {
        return successIndication;
    }

    /**
     * Sets the text output of the component's Stop script command which says
     * the component is stopped successfully.
     *
     * @param successIndication
     */
    public void setSuccessIndication(String successIndication) {
        this.successIndication = successIndication;
    }
}
