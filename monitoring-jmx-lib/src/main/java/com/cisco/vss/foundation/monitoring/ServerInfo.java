/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * This class has agent related information.It implements the NotificationSender &
 * MonitoringAgentMXBean.
 *
 * @author manojc
 */
class ServerInfo extends NotificationBroadcasterSupport implements NotificationSender, MonitoringAgentMXBean {
    private Date agentStartTime;
    private long attrNotificationSeq = 0;
    MonitoringMXBean mXBean;

    ServerInfo(MonitoringMXBean monitoringmxBean) {
        agentStartTime = new Date();
        this.mXBean = monitoringmxBean;
    }

    /**
     * @return RMI port on which MonitoringAgent is registered
     */
    @Override
    public int getAgentPort() {
        return AppProperties.getAgentPort();
    }

    /**
     * @return The port number on which the Monitoring information is exported
     */
    @Override
    public int getExportedPort() {
        return AppProperties.getExportedPort();
    }

    /**
     * @return Date when MonitoringAgent was registered
     */
    @Override
    public Date getAgentStartTime() {
        return agentStartTime;
    }

    /**
     * @return Version of the MonitoringAgent
     */
    @Override
    public String getAgentVersion() {
        return AppProperties.getAgentVersion();
    }

    /**
     * @return IP of the host machine where MonitoringAgent runs
     */
    @Override
    public String getHostIP() {
        return AppProperties.getHostIP();
    }

    /**
     * @return Network name of the host machine where MonitoringAgent runs
     */
    @Override
    public String getHostName() {
        return AppProperties.getHostName();
    }

    /**
     * Sends a notification to registered clients about the change in value of
     * an attribute.
     *
     * @param msg           A String containing the message of the notification.
     * @param attributeName A String giving the name of the attribute.
     * @param attributeType A String containing the type of the attribute.
     * @param oldValue      An object representing value of the attribute before the
     *                      change.
     * @param newValue      An object representing value of the attribute after the
     *                      change.
     * @since NDSMXAgent 1.1
     */
    @Override
    public void sendAttributeChangeNotification(String msg, String attributeName, String attributeType,
                                                Object oldValue, Object newValue) {
        MonitoringAgent.LOGGER.debug("Sending Notification " + (attrNotificationSeq + 1) + ":" + msg + ":"
                + attributeName + ":" + attributeType + ":" + oldValue.toString() + ":" + newValue.toString());

        Notification n = new AttributeChangeNotification(this, attrNotificationSeq++, System.currentTimeMillis(), msg,
                attributeName, attributeType, oldValue, newValue);
        sendNotification(n);
        MonitoringAgent.LOGGER.debug("Notification Sent " + attrNotificationSeq);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{AttributeChangeNotification.ATTRIBUTE_CHANGE};
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);

        return new MBeanNotificationInfo[]{info};
    }

    @Override
    public void setAttributeValue(String path, String value) throws Exception {
        setAttributeValue(path, this.mXBean, value);
    }

    @Override
    public void setAttributeValue(String path, int value) throws Exception {
        setAttributeValue(path, this.mXBean, value);
    }

    @Override
    public void setAttributeValue(String path, long value) throws Exception {
        setAttributeValue(path, this.mXBean, value);
    }

    private static void setAttributeValue(String path, MonitoringMXBean mxBean, Object value) throws SecurityException,
            Exception {
        Object obj = mxBean;
        String[] properties = path.split("/", 0);
        for (int i = 0; i < (properties.length - 1); i++) {
            if (obj == null)
                return;
            obj = getChild(obj, properties[i], value, false);
        }
        getChild(obj, properties[properties.length - 1], value, true);
    }

    private static void callSetMethod(Object result, Object value, String strFunc) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method[] theMethods = result.getClass().getMethods();

        for (int i = 0; i < theMethods.length; i++) {
            String methodString = theMethods[i].getName();

            if (strFunc.equals(methodString)) {
                theMethods[i].invoke(result, value);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static Object getChild(Object obj, String attributeName, Object value, boolean isSet)
            throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        int substrstart = attributeName.indexOf('[');
        int substrend = attributeName.indexOf(']');

        if (substrstart == -1) {
            if (isSet) {
                String strFunc = "set" + attributeName;
                callSetMethod(obj, value, strFunc);
                return null;
            } else {
                String getStr = "get";
                StringBuilder strBuild = new StringBuilder(attributeName);
                strBuild.insert(0, getStr);

                String getFuncStr = strBuild.toString();

                Method m = obj.getClass().getMethod(getFuncStr);
                Object result = m.invoke(obj);

                return result;
            }
        } else {
            String firststr = attributeName.substring(0, substrstart);
            String secondstr = attributeName.substring(substrstart + 1, substrend);
            Object parentObj = obj;
            Object requests = parentObj.getClass().getDeclaredMethod("get" + firststr, new Class[]{}).invoke(
                    parentObj, new Object[]{});

            if (requests instanceof Map) {
                Object result = ((HashMap) requests).get(secondstr);
                return result;
            } else if (requests instanceof List) {
                if (isSet) {
                    ((List) requests).set(Integer.parseInt(secondstr), value);
                    return null;
                } else {
                    Object request = ((List) requests).get(Integer.parseInt(secondstr));
                    return request;
                }
            } else if (requests.getClass().isArray()) {
                if (isSet) {
                    Object[] arr = (Object[]) requests;
                    Array.set(arr, Integer.parseInt(secondstr), value);
                    return null;
                } else {
                    Object[] arr = (Object[]) requests;
                    return Array.get(arr, Integer.parseInt(secondstr));
                }
            } else {
                return null;
            }
        }
    }
}
