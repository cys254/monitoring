/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.nds.cab.component;

import java.util.List;

import com.cisco.vss.foundation.monitoring.MonitoringMXBean;

public interface SampleSystemWithSettersMXBean extends MonitoringMXBean {
    Request getLatestRequest();

    void setLatestRequest(Request latestRequest);

    String getMyMessage();

    void setMyMessage(String myMessage);

    int getMyInt();

    void setMyInt(int myInt);

    long getMyLong();

    void setMyLong(long myLong);

    Request[] getAllRequest();

    void setAllRequest(Request[] allRequest);

    String[] getConnectedSystems();

    void setConnectedSystems(String[] connectedSystems);

    List<Error> getErrors();

    void setErrors(List<Error> errors);
}
