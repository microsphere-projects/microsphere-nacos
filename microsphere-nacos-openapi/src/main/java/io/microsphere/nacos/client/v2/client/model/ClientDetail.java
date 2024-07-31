/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.nacos.client.v2.client.model;

import io.microsphere.nacos.client.common.model.Model;

/**
 * The {@link Model} {@link Class} for Nacos Client Detail
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class ClientDetail implements Model {

    private static final long serialVersionUID = -7456524977337954973L;

    private String clientId;

    private boolean ephemeral;

    private long lastUpdatedTime;

    private String clientType;

    private String clientIp;

    private int clientPort;

    private String connectType;

    private String appName;

    private String version;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isEphemeral() {
        return ephemeral;
    }

    public void setEphemeral(boolean ephemeral) {
        this.ephemeral = ephemeral;
    }

    public long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(long lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "clientId='" + clientId + '\'' +
                ", ephemeral=" + ephemeral +
                ", lastUpdatedTime=" + lastUpdatedTime +
                ", clientType='" + clientType + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", clientPort=" + clientPort +
                ", connectType='" + connectType + '\'' +
                ", appName='" + appName + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
