/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.oss.foundation.monitoring;

import java.io.IOException;
import java.util.*;

import javax.management.*;
import javax.management.openmbean.*;
import javax.management.remote.*;

/**
 * This class provides a means for connecting to a JMX Server and fetching the
 * management information.
 *
 * @author manojc
 */
public class MonitoringClient {
    private final static String[] primitiveTypes = {"boolean", "int", "long", "java.lang.String", "java.lang.Integer",
            "java.lang.Long"};
    private final static String[] compositeTypes = {"javax.management.openmbean.CompositeData",
            "javax.management.openmbean.CompositeDataSupport"};
    private final static String[] tabularTypes = {"javax.management.openmbean.TabularData",
            "javax.management.openmbean.TabularDataSupport"};
    private String serviceUrl;
    private String objectName;
    private String userName;
    private String password;
    private Map<String, Object> env;
    private JMXConnector jmxc;
    private MBeanServerConnection server;
    private boolean isConnected;

    /**
     * @param serviceUrl
     * @param objectName
     */
    public MonitoringClient(String serviceUrl, String objectName) {
        this.serviceUrl = serviceUrl;
        this.objectName = objectName;
        this.userName = null;
        this.password = null;
        this.env = null;
        this.isConnected = false;
    }

    /**
     * @param serviceUrl
     * @param objectName
     * @param userName
     * @param password
     */
    @SuppressWarnings("unchecked")
    public MonitoringClient(String serviceUrl, String objectName, String userName, String password) {
        this.serviceUrl = serviceUrl;
        this.objectName = objectName;
        this.userName = userName;
        this.password = password;
        this.isConnected = false;
        if (this.userName != null) {
            env = new HashMap();
            env.put(JMXConnector.CREDENTIALS, new String[]{this.userName, this.password});
        }
    }

    /**
     * @param name
     * @param instance
     * @param jmxAgentHost
     * @param jmxAgentPort
     */
    public MonitoringClient(String name, String instance, String jmxAgentHost, int jmxAgentPort) {
        this.serviceUrl = getServiceURL(name, instance, jmxAgentHost, jmxAgentPort);
        this.objectName = getObjectName(name, instance);
        this.userName = null;
        this.password = null;
        this.env = null;
        this.isConnected = false;
    }

    /**
     * @param name
     * @param instance
     * @param jmxAgentHost
     * @param jmxAgentPort
     * @param userName
     * @param password
     */
    @SuppressWarnings({"unchecked"})
    public MonitoringClient(String name, String instance, String jmxAgentHost, int jmxAgentPort, String userName,
                            String password) {
        this.serviceUrl = getServiceURL(name, instance, jmxAgentHost, jmxAgentPort);
        this.objectName = getObjectName(name, instance);
        this.userName = userName;
        this.password = password;
        this.isConnected = false;

        if (this.userName != null) {
            env = new HashMap();
            env.put(JMXConnector.CREDENTIALS, new String[]{this.userName, this.password});
        }
    }

    private static Boolean isPrimitiveType(String type) {
        for (int i = 0; i < primitiveTypes.length; i++)
            if (type.compareTo(primitiveTypes[i]) == 0) {
                return true;
            }

        return false;
    }

    private static Boolean isCompositeType(String type) {
        for (int i = 0; i < compositeTypes.length; i++)
            if (type.compareTo(compositeTypes[i]) == 0) {
                return true;
            }

        return false;
    }

    private static Boolean isTabularType(String type) {
        for (int i = 0; i < tabularTypes.length; i++)
            if (type.compareTo(tabularTypes[i]) == 0) {
                return true;
            }

        return false;
    }

    private String getServiceURL(String name, String instance, String jmxAgentHost, int jmxAgentPort) {
        if (jmxAgentHost == null) {
            jmxAgentHost = "";
        }

        return "service:jmx:rmi:///jndi/rmi://" + jmxAgentHost + ":" + jmxAgentPort + "/jmxrmi/" + name + instance;
    }

    private String getObjectName(String name, String instance) {
        String objName = AppProperties.getDomainName() + ":name=" + name;

        if ((instance != null) && !instance.trim().equals("")) {
            objName = objName + ",instance=" + instance;
        }

        objName = objName + ",*";

        return objName;
    }

    @SuppressWarnings({"unchecked"})
    public boolean connect() {
        boolean isSuccess = false;
        try {
            if (!isConnected) {
                JMXServiceURL url = new JMXServiceURL(this.serviceUrl);
                jmxc = JMXConnectorFactory.connect(url, env);
                server = jmxc.getMBeanServerConnection();
                isConnected = true;
                isSuccess = true;
            }
        } catch (IOException ex) {
            isSuccess = false;
        } catch (ClassCastException ex) {
            isSuccess = false;
        }
        return isSuccess;
    }

    public boolean disconnect() {
        boolean isSuccess = false;
        try {
            if (isConnected) {
                jmxc.close();
                isConnected = false;
                isSuccess = true;
            }
        } catch (IOException ex) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @SuppressWarnings("unchecked")
    public String getManagementInformationXml() {
        String objectNamePattern = objectName;
        StringBuffer buf = new StringBuffer("<MBeans>");

        try {
            if (!isConnected && !connect()) {
                System.err.println("Couldn't connect to ServiceUrl:" + serviceUrl);
                return null;
            }

            String[] splitObjectNamePattern = objectNamePattern.split(";");

            for (int splitIndex = 0; splitIndex < splitObjectNamePattern.length; splitIndex++) {
                objectNamePattern = splitObjectNamePattern[splitIndex];

                ObjectName searchObjectName = null;

                if ((objectNamePattern != null) && !objectNamePattern.equals("")) {
                    try {
                        searchObjectName = new ObjectName(objectNamePattern);
                    } catch (MalformedObjectNameException malformedEx) {
                        return null;
                    }
                }

                server.queryMBeans(searchObjectName, null);

                final Set<ObjectName> names = server.queryNames(searchObjectName, null);

                for (final Iterator<ObjectName> i = names.iterator(); i.hasNext(); ) {
                    ObjectName objectName = (ObjectName) i.next();
                    String domainName = objectName.getDomain();
                    String keyPropList = objectName.getKeyPropertyListString();
                    buf.append(tabbify(1) + "<MBean Name= '" + objectName + "' DomainName= '" + domainName
                            + "' KeyPropList= '" + keyPropList + "'>");

                    MBeanInfo info = server.getMBeanInfo((ObjectName) objectName);

                    MBeanAttributeInfo[] attrs = info.getAttributes();

                    if (attrs == null) {
                        continue;
                    }

                    for (int j = 0; j < attrs.length; j++) {
                        if (attrs[j].isReadable()) {
                            try {
                                String attributeType = attrs[j].getType();
                                String attributeName = attrs[j].getName();
                                Object attributeValue = server.getAttribute(objectName, attributeName);
                                String result = resolveAttributeData(attributeType, attributeName, attributeValue, 2);
                                buf.append(result);
                            } catch (MBeanException x) {
                                continue;
                            } catch (AttributeNotFoundException e) {
                                continue;
                            }
                        }
                    }

                    buf.append(tabbify(1) + "</MBean>");
                }
            }

            buf.append("\n</MBeans>");
        } catch (IOException x) {
            return null;
        } catch (InstanceNotFoundException e) {
            return null;
        } catch (IntrospectionException e) {
            return null;
        } catch (ReflectionException e) {
            return null;
        }

        return buf.toString();
    }

    @SuppressWarnings("unchecked")
    private String resolveAttributeData(String attributeType, Object attributeName, Object attributeValue,
                                        int formatDepth) {
        StringBuffer buf = new StringBuffer();

        try {
            attributeName = attributeName.toString().replaceAll(" ", "_");
            attributeName = attributeName.toString().replaceAll("[\\[\\]]", "");

            if (isPrimitiveType(attributeType)) {
                if (attributeValue.toString().indexOf("<") >= 0) {
                    buf.append(tabbify(formatDepth) + "<" + attributeName + " Type='" + attributeType + "'><![CDATA["
                            + attributeValue + "]]></" + attributeName + ">");
                } else {
                    buf.append(tabbify(formatDepth) + "<" + attributeName + " Type='" + attributeType + "'>"
                            + attributeValue + "</" + attributeName + ">");
                }
            } else if (isCompositeType(attributeType)) {
                buf.append(tabbify(formatDepth) + "<" + attributeName + " Type='" + attributeType + "'>");

                try {
                    CompositeData compAttributeValue = (CompositeData) attributeValue;

                    for (Iterator<String> k = compAttributeValue.getCompositeType().keySet().iterator(); k.hasNext(); ) {
                        String key = (String) k.next();
                        Object value = "";
                        String type = "";

                        value = compAttributeValue.get(key);
                        type = value.getClass().getName();

                        buf.append(resolveAttributeData(type, key, value, formatDepth + 1));
                    }
                } finally {
                    buf.append(tabbify(formatDepth) + "</" + attributeName + ">");
                }
            } else if (isTabularType(attributeType)) {
                buf.append(tabbify(formatDepth) + "<" + attributeName + " Type='" + attributeType + "'>");

                TabularDataSupport tabularAttributeValue = (TabularDataSupport) attributeValue;

                for (Iterator<Map.Entry<Object, Object>> k = tabularAttributeValue.entrySet().iterator(); k.hasNext(); ) {
                    Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) k.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    String type = value.getClass().getName();
                    buf.append(resolveAttributeData(type, key, value, formatDepth + 1));
                }

                buf.append(tabbify(formatDepth) + "</" + attributeName + ">");
            } else {
                buf.append(tabbify(formatDepth) + "<" + attributeName + " Type='" + attributeType + "'>"
                        + attributeValue + "</" + attributeName + ">");
            }
        } catch (NullPointerException ex) {
            return null;
        }

        return buf.toString();
    }

    private String tabbify(int count) {
        StringBuffer result = new StringBuffer("\n");
        for (int i = 0; i < count; i++)
            result.append('\t');
        return result.toString();
    }
}
