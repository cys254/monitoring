/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.nds.cab.component;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Request {
    String requestType;
    int successRequestCount = 0;
    int failedRequestCount = 0;
    Date lastRequestTime;
    Map<String, Error> errors = new HashMap<String, Error>();

    public Request() {
        errors.put("error1", new Error("error1", Severity.Major));
        errors.put("error2", new Error("error2", Severity.Major));
    }

    @ConstructorProperties({"requestType"})
    public Request(String requestType) {
        this.requestType = requestType;
        errors.put("error1", new Error("error1", Severity.Major));
        errors.put("error2", new Error("error2", Severity.Major));
    }

    public String getRequestType() {
        return requestType;
    }

    public int getSuccessRequestCount() {
        return successRequestCount;
    }

    public int getFailedRequestCount() {
        return failedRequestCount;
    }

    public Date getLastRequestTime() {
        return lastRequestTime;
    }

    public Map<String, Error> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Error> errors) {
        this.errors = errors;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setSuccessRequestCount(int successRequestCount) {
        this.successRequestCount = successRequestCount;
    }

    public void setFailedRequestCount(int failedRequestCount) {
        this.failedRequestCount = failedRequestCount;
    }

    public void setLastRequestTime(Date lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }
}
