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
import io.microsphere.nacos.client.common.discovery.model.BaseInstance;
import io.microsphere.nacos.client.common.discovery.model.GenericInstance;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.InstancesList;
import io.microsphere.nacos.client.common.discovery.model.NewInstance;
import io.microsphere.nacos.client.common.discovery.model.UpdateHealthInstance;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;

import java.util.Map;

import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CLUSTER_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_ENABLED;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_EPHEMERAL;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_HEALTHY;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_IP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_PORT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_WEIGHT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.METADATA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_GROUP_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_NAME;
import static io.microsphere.nacos.client.util.OpenApiUtils.executeAsResultMessageOK;

/**
 * The {@link InstanceClientV2} for <a href="https://nacos.io/en/docs/latest/manual/user/open-api">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClientV2
 * @see OpenApiClient
 * @since 1.0.0
 */
public class OpenApiInstanceClientV2 implements InstanceClientV2 {

    public static final String INSTANCE_ENDPOINT = "/v2/ns/instance";

    public static final String INSTANCES_LIST_ENDPOINT = "/v2/ns/instance/list";

    public static final String INSTANCE_HEALTH_ENDPOINT = "/v2/ns/health/instance";

    private static final String METADATA_BATCH_ENDPOINT = "/v2/ns/instance/metadata/batch";

    private final OpenApiClient openApiClient;

    private final NacosClientConfig nacosClientConfig;

    public OpenApiInstanceClientV2(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        this.openApiClient = openApiClient;
        this.nacosClientConfig = nacosClientConfig;
    }

    @Override
    public boolean register(NewInstance instance) {
        OpenApiRequest request = buildInstanceRequest(instance, HttpMethod.POST);
        return executeAsBoolean(request);
    }

    @Override
    public boolean deregister(NewInstance instance) {
        OpenApiRequest request = buildInstanceRequest(instance, HttpMethod.DELETE);
        return executeAsBoolean(request);
    }

    @Override
    public boolean refresh(NewInstance instance) {
        OpenApiRequest request = buildInstanceRequest(instance, HttpMethod.PUT);
        return executeAsBoolean(request);
    }

    @Override
    public Instance getInstance(String namespaceId, String groupName, String clusterName, String serviceName, String ip, int port) {
        OpenApiRequest request = OpenApiRequest.Builder.create(INSTANCE_ENDPOINT)
                .method(HttpMethod.GET)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(SERVICE_GROUP_NAME, groupName)
                .queryParameter(CLUSTER_NAME, clusterName)
                .queryParameter(SERVICE_NAME, serviceName)
                .queryParameter(INSTANCE_IP, ip)
                .queryParameter(INSTANCE_PORT, port)
                .build();
        Instance instance = this.openApiClient.executeAsResult(request, Instance.class);
        instance.setNamespaceId(namespaceId);
        return instance;
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


    private OpenApiRequest buildInstanceRequest(NewInstance instance, HttpMethod method) {
        return requestBuilder(instance, method).build();
    }

    private OpenApiRequest.Builder requestBuilder(NewInstance instance, HttpMethod method) {
        return requestBuilder((GenericInstance) instance, method)
                .queryParameter(INSTANCE_HEALTHY, instance.getHealthy());
    }

    private OpenApiRequest buildHealthRequest(UpdateHealthInstance instance, HttpMethod method) {
        return OpenApiRequest.Builder.create(INSTANCE_HEALTH_ENDPOINT)
                .method(method)
                .queryParameter(NAMESPACE_ID, instance.getNamespaceId())
                .queryParameter(SERVICE_GROUP_NAME, instance.getGroupName())
                .queryParameter(SERVICE_NAME, instance.getServiceName())
                .queryParameter(CLUSTER_NAME, instance.getClusterName())
                .queryParameter(INSTANCE_IP, instance.getIp())
                .queryParameter(INSTANCE_PORT, instance.getPort())
                .queryParameter(INSTANCE_HEALTHY, instance.isHealthy())
                .build();
    }

    private OpenApiRequest.Builder requestBuilder(GenericInstance instance, HttpMethod method) {
        return requestBuilder((BaseInstance) instance, method)
                .queryParameter(INSTANCE_WEIGHT, instance.getWeight())
                .queryParameter(INSTANCE_ENABLED, instance.getEnabled())
                .queryParameter(INSTANCE_EPHEMERAL, instance.getEphemeral())
                .queryParameter(METADATA, instance.getMetadata());
    }

    private OpenApiRequest.Builder requestBuilder(BaseInstance instance, HttpMethod method) {
        return OpenApiRequest.Builder.create(INSTANCE_ENDPOINT)
                .method(method)
                .queryParameter(NAMESPACE_ID, instance.getNamespaceId())
                .queryParameter(SERVICE_GROUP_NAME, instance.getGroupName())
                .queryParameter(SERVICE_NAME, instance.getServiceName())
                .queryParameter(CLUSTER_NAME, instance.getClusterName())
                .queryParameter(INSTANCE_IP, instance.getIp())
                .queryParameter(INSTANCE_PORT, instance.getPort());
    }

    private boolean executeAsBoolean(OpenApiRequest request) {
        return executeAsResultMessageOK(this.openApiClient, request);
    }
}
