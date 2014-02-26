package com.cisco.vss.foundation.monitoring;


//import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.net.URI;

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
