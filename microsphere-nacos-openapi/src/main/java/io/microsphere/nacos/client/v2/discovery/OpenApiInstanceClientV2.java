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
package io.microsphere.nacos.client.v2.discovery;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.common.discovery.ConsistencyType;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.InstancesList;
import io.microsphere.nacos.client.common.discovery.model.UpdateHealthInstance;
import io.microsphere.nacos.client.transport.OpenApiClient;

import java.util.Map;

/**
 * The {@link InstanceClientV2} for <a href="https://nacos.io/en/docs/latest/manual/user/open-api">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClientV2
 * @see OpenApiClient
 * @since 1.0.0
 */
public class OpenApiInstanceClientV2 implements InstanceClientV2 {

    private final OpenApiClient openApiClient;

    private final NacosClientConfig nacosClientConfig;

    public OpenApiInstanceClientV2(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        this.openApiClient = openApiClient;
        this.nacosClientConfig = nacosClientConfig;
    }

    @Override
    public boolean register(Instance instance) {
        return false;
    }

    @Override
    public boolean deregister(Instance instance) {
        return false;
    }

    @Override
    public boolean refresh(Instance instance) {
        return false;
    }

    @Override
    public Instance getInstance(String namespaceId, String groupName, String clusterName, String serviceName, String ip, int port) {
        return null;
    }

    @Override
    public InstancesList getInstancesList(String namespaceId, String groupName, String clusterName, String serviceName, String ip, Integer port, Boolean healthyOnly, String app) {
        return null;
    }

    @Override
    public boolean sendHeartbeat(Instance instance) {
        return false;
    }

    @Override
    public boolean updateHealth(UpdateHealthInstance updateHealthInstance) {
        return false;
    }

    @Override
    public boolean batchUpdateMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType) {
        return false;
    }

    @Override
    public boolean batchDeleteMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType) {
        return false;
    }
}
