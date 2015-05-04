/*
 * Copyright 2015 Cisco Systems, Inc.
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

package com.cisco.oss.foundation.monitoring;


//import java.io.Serializable;

import java.util.Date;

public class ExceptionNotificationMXBeanImpl implements ExceptionNotificationMXBean {


    /**
     *
     */
    private static final long serialVersionUID = -4023636728005746424L;
    private String componentName;
    private String id;
    private Date timeOfOccurence;
    private ExceptionSeverity severity;
    //private java.util.List<Emp> listOFEmployee;
    //private List<URI> poHandlesInFailedTxn;
    //private List<ParameterDetails> parameterList;
    private Object data;

    /*
    public List<URI> getPoHandlesInFailedTxn() {
        return poHandlesInFailedTxn;
    }

    public void setPoHandlesInFailedTxn(List<URI> poHandlesInFailedTxn) {
        this.poHandlesInFailedTxn = poHandlesInFailedTxn;
    }
    */
    @Override
    public ExceptionSeverity getSeverity() {
        return this.severity;
    }

    @Override
    public void setSeverity(ExceptionSeverity severity) {
        this.severity = severity;

    }

    @Override
    public String getComponentName() {
        return componentName;
    }

    @Override
    public String getId() {

        return this.id;
    }

    @Override
    public Date getTimeOfOccurance() {

        return timeOfOccurence;
    }

    @Override
    public void setComponentName(String componentName) {
        this.componentName = componentName;

    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public void setTimeOfOccurance(Date timeOfOccurence) {
        this.timeOfOccurence = timeOfOccurence;

    }
//
//	public java.util.List<Emp> getListOFString() {
//		return listOFEmployee;
//	}
//
//	public void setListOFString(java.util.List<Emp> listOFEmployee) {
//		this.listOFEmployee = listOFEmployee;
//	}

    public Date getTimeOfOccurence() {
        return timeOfOccurence;
    }

    public void setTimeOfOccurence(Date timeOfOccurence) {
        this.timeOfOccurence = timeOfOccurence;
    }


}
