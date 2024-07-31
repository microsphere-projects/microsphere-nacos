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
import io.microsphere.nacos.client.constants.Constants;

/**
 * The Nacos Discoverable {@link Model} {@link Class}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public abstract class DiscoverableModel implements Model {

    private static final long serialVersionUID = 719819197200005111L;

    /**
     * The ID of namespace (optional)
     * If not specified, the default value is "public"
     *
     * @see Constants#DEFAULT_NAMESPACE_ID
     */
    protected String namespaceId;

    /**
     * The Cluster name (optional)
     * If not specified, the default value is "DEFAULT"
     *
     * @see Constants#DEFAULT_CLUSTER_NAME
     */
    protected String clusterName;

    /**
     * The Group name (optional)
     * If not specified, the default value is "DEFAULT_GROUP"
     *
     * @see Constants#DEFAULT_GROUP_NAME
     */
    protected String groupName;

    /**
     * The Service name (required)
     */
    protected String serviceName;

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
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

    @Override
    public String toString() {
        return "DiscoverableModel{" +
                "namespaceId='" + namespaceId + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
