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
package io.microsphere.nacos.client.common.discovery.model;

import io.microsphere.nacos.client.common.model.Model;

import java.util.List;
import java.util.Objects;

/**
 * The {@link Model model} {@link Class} of {@link Instance Instances} List
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class InstancesList implements Model {

    private static final long serialVersionUID = 8882800045256918229L;

    private String namespaceId;

    private String groupName;

    private String serviceName;

    private Long cacheMillis;

    private String name;

    private String checksum;

    private Long lastRefTime;

    private String clusters;

    private Boolean allIPs;

    private Boolean reachProtectionThreshold;

    private Boolean valid;

    private String dom;

    private Boolean useSpecifiedURL;

    private String env;

    private List<Instance> hosts;

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getCacheMillis() {
        return cacheMillis;
    }

    public void setCacheMillis(Long cacheMillis) {
        this.cacheMillis = cacheMillis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Long getLastRefTime() {
        return lastRefTime;
    }

    public void setLastRefTime(Long lastRefTime) {
        this.lastRefTime = lastRefTime;
    }

    public String getClusters() {
        return clusters;
    }

    public void setClusters(String clusters) {
        this.clusters = clusters;
    }

    public Boolean getAllIPs() {
        return allIPs;
    }

    public void setAllIPs(Boolean allIPs) {
        this.allIPs = allIPs;
    }

    public Boolean getReachProtectionThreshold() {
        return reachProtectionThreshold;
    }

    public void setReachProtectionThreshold(Boolean reachProtectionThreshold) {
        this.reachProtectionThreshold = reachProtectionThreshold;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public Boolean getUseSpecifiedURL() {
        return useSpecifiedURL;
    }

    public void setUseSpecifiedURL(Boolean useSpecifiedURL) {
        this.useSpecifiedURL = useSpecifiedURL;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public List<Instance> getHosts() {
        return hosts;
    }

    public void setHosts(List<Instance> hosts) {
        this.hosts = hosts;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstancesList)) return false;

        InstancesList that = (InstancesList) o;
        return Objects.equals(namespaceId, that.namespaceId) &&
                Objects.equals(groupName, that.groupName) &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(cacheMillis, that.cacheMillis) &&
                Objects.equals(name, that.name) &&
                Objects.equals(checksum, that.checksum) &&
                Objects.equals(lastRefTime, that.lastRefTime) &&
                Objects.equals(clusters, that.clusters) &&
                Objects.equals(allIPs, that.allIPs) &&
                Objects.equals(reachProtectionThreshold, that.reachProtectionThreshold) &&
                Objects.equals(valid, that.valid) &&
                Objects.equals(dom, that.dom) &&
                Objects.equals(useSpecifiedURL, that.useSpecifiedURL) &&
                Objects.equals(env, that.env) &&
                Objects.equals(hosts, that.hosts);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(namespaceId);
        result = 31 * result + Objects.hashCode(groupName);
        result = 31 * result + Objects.hashCode(serviceName);
        result = 31 * result + Objects.hashCode(cacheMillis);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(checksum);
        result = 31 * result + Objects.hashCode(lastRefTime);
        result = 31 * result + Objects.hashCode(clusters);
        result = 31 * result + Objects.hashCode(allIPs);
        result = 31 * result + Objects.hashCode(reachProtectionThreshold);
        result = 31 * result + Objects.hashCode(valid);
        result = 31 * result + Objects.hashCode(dom);
        result = 31 * result + Objects.hashCode(useSpecifiedURL);
        result = 31 * result + Objects.hashCode(env);
        result = 31 * result + Objects.hashCode(hosts);
        return result;
    }
}
