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
package io.microsphere.nacos.client.v1.server.model;

import io.microsphere.nacos.client.common.model.Model;

/**
 * The {@link Model model} {@link Class} of Nacos Server
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class Server implements Model {

    private static final long serialVersionUID = -3050148488406954752L;

    private String ip;

    private Integer port;

    private String site;

    private String state;

    private Float weight;

    private Float adWeight;

    private Boolean alive;

    private Long lastRefreshTime;

    private String lastRefreshTimeString;

    private String version;

    private Integer raftPort;

    private String address;

    private Integer failAccessCount;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setAdWeight(Float adWeight) {
        this.adWeight = adWeight;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public void setLastRefreshTime(Long lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public void setLastRefreshTimeString(String lastRefreshTimeString) {
        this.lastRefreshTimeString = lastRefreshTimeString;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRaftPort(Integer raftPort) {
        this.raftPort = raftPort;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFailAccessCount(Integer failAccessCount) {
        this.failAccessCount = failAccessCount;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getSite() {
        return site;
    }

    public String getState() {
        return state;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getAdWeight() {
        return adWeight;
    }

    public Boolean getAlive() {
        return alive;
    }

    public Long getLastRefreshTime() {
        return lastRefreshTime;
    }

    public String getLastRefreshTimeString() {
        return lastRefreshTimeString;
    }

    public String getVersion() {
        return version;
    }

    public Integer getRaftPort() {
        return raftPort;
    }

    public String getAddress() {
        return address;
    }

    public Integer getFailAccessCount() {
        return failAccessCount;
    }

    @Override
    public String toString() {
        return "Server{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", site='" + site + '\'' +
                ", state='" + state + '\'' +
                ", weight=" + weight +
                ", adWeight=" + adWeight +
                ", alive=" + alive +
                ", lastRefreshTime=" + lastRefreshTime +
                ", lastRefreshTimeString='" + lastRefreshTimeString + '\'' +
                ", version='" + version + '\'' +
                ", raftPort=" + raftPort +
                ", address='" + address + '\'' +
                ", failAccessCount=" + failAccessCount +
                '}';
    }
}
