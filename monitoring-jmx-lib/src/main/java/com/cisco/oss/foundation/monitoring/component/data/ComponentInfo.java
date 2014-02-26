package com.cisco.oss.foundation.monitoring.component.data;

import com.cisco.oss.foundation.monitoring.IComponentInfoMXBean;
import com.cisco.oss.foundation.monitoring.RedundancyMode;

public class ComponentInfo implements IComponentInfoMXBean {

    private String fullName;
    private String instance;
    private String version;
    private RedundancyMode redundancyMode;
    private String name;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRedundancyMode(RedundancyMode redundancyMode) {
        this.redundancyMode = redundancyMode;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFullName() {
        return this.fullName;
    }


    public String getInstance() {
        return this.instance;
    }


    public String getName() {
        return this.name;
    }


    public RedundancyMode getRedundancyMode() {
        return this.redundancyMode;
    }


    public String getVersion() {
        return this.version;
    }

}
