/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.oss.foundation.monitoring;

/**
 * It is an interface when implemented by Component's MXBean class, it will get
 * a reference to NotificationSender object through which it can send
 * notifications by itself.
 *
 * @author manojc
 */
public interface INotifier {
    void setNotificationSender(NotificationSender nSender);
}
