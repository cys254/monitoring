/*
 * Copyright 2014 Cisco Systems, Inc.
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

package com.cisco.oss.foundation.monitoring.serverconnection;


import com.cisco.oss.foundation.monitoring.services.Service;

/**
 * Created by Yair Ogen on 3/5/14.
 */
public interface ServerConnectionActor {

    void startTransaction(ServerConnection serverConnection);
    void endTransaction(ServerConnection serverConnection, boolean isFailed, String description);
    void updateOpenSince(ServerConnection serverConnection, long openSince);
}

