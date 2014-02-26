package com.nds.cab.component;

import java.beans.ConstructorProperties;
import java.util.Date;

public class Error {
    private String errorType;
    private Severity errorSeverity;
    private int errorCount;
    private Date lastErrorTime;
    private String lastErrorDescription;

    @ConstructorProperties({"errorType", "errorSeverity"})
    public Error(String errorType, Severity errorSeverity) {
        this.errorType = errorType;
        this.errorSeverity = errorSeverity;
        this.errorCount = 0;
        this.lastErrorTime = null;
        this.lastErrorDescription = null;
    }

    public String getErrorType() {
        return errorType;
    }

    public Severity getErrorSeverity() {
        return errorSeverity;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public Date getLastErrorTime() {
        return lastErrorTime;
    }

    public String getLastErrorDescription() {
        return lastErrorDescription;
    }
}
