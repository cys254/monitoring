/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

/* 
 * Created on Aug 24, 2010
 */

package com.nds.cab.component;

import java.util.ArrayList;
import java.util.List;

import com.cisco.vss.foundation.monitoring.RedundancyMode;

public class SampleSystemWithSetters implements SampleSystemWithSettersMXBean {
    private String name = "ABC";
    private String instance = "Backup";
    private String version = "1.2.3.R4";
    private Request latestRequest = new Request("LatestRequestType");
    private String myMessage = "Hello World";
    private int myInt = 999;
    private long myLong = 9999999L;
    private Request[] allRequest = {new Request("req1"), new Request("req2")};
    private String[] connectedSystems = {"System1", "System2", "System3"};
    private List<Error> errors = new ArrayList<Error>();

    public SampleSystemWithSetters() {
        errors.add(new Error("Err1", Severity.Critical));
        errors.add(new Error("Err2", Severity.Major));
    }

    @Override
    public String getInstance() {
        return this.instance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getVersion() {
        return this.version;
    }


    @Override
    public Request getLatestRequest() {
        return this.latestRequest;
    }

    @Override
    public String getMyMessage() {
        return this.myMessage;
    }

    @Override
    public void setLatestRequest(Request latestRequest) {
        this.latestRequest = latestRequest;

    }

    @Override
    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    @Override
    public int getMyInt() {
        return this.myInt;
    }

    @Override
    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    @Override
    public long getMyLong() {
        return this.myLong;
    }

    @Override
    public void setMyLong(long myLong) {
        this.myLong = myLong;
    }

    public Request[] getAllRequest() {
        return this.allRequest;
    }

    public void setAllRequest(Request[] allRequest) {
        this.allRequest = allRequest;
    }

    public String[] getConnectedSystems() {
        return this.connectedSystems;
    }

    public void setConnectedSystems(String[] connectedSystems) {
        this.connectedSystems = connectedSystems;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

    @Override
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public String getFullName() {
        // TODO Auto-generated method stub
        return null;
    }

}
