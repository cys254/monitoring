/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring;

import javax.management.Notification;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This interface wraps the notification functionality.
 *
 * @author manojc
 */
public interface NotificationSender {
    /**
     * @param notification
     */
    void sendNotification(Notification notification);

    /**
     * @param msg
     * @param attributeName
     * @param attributeType
     * @param oldValue
     * @param newValue
     */
    void sendAttributeChangeNotification(String msg, String attributeName, String attributeType, Object oldValue,
                                         Object newValue);
}
