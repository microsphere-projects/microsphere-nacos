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
import io.microsphere.nacos.client.common.discovery.model.Heartbeat;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.InstancesList;
import io.microsphere.nacos.client.common.discovery.model.NewInstance;
import io.microsphere.nacos.client.common.discovery.model.UpdateHealthInstance;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;

import java.util.Map;

import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.APP_V2;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CLUSTER_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.HEARTBEAT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_ENABLED;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_EPHEMERAL;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_HEALTHY;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_HEALTHY_ONLY;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_IP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_PORT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_WEIGHT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.METADATA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_GROUP_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_NAME;
import static io.microsphere.nacos.client.util.ModelUtils.buildServiceName;
import static io.microsphere.nacos.client.util.ModelUtils.completeInstance;
import static io.microsphere.nacos.client.util.ModelUtils.completeInstances;
import static io.microsphere.nacos.client.util.ModelUtils.getHeartbeatMap;
import static io.microsphere.nacos.client.util.ModelUtils.setPropertyIfAbsent;
import static io.microsphere.nacos.client.util.OpenApiUtils.createBatchMetadataRequest;
import static io.microsphere.nacos.client.util.OpenApiUtils.createRequestBuilder;
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

    public static final String INSTANCES_LIST_ENDPOINT = INSTANCE_ENDPOINT + "/list";

    public static final String INSTANCE_HEARTBEAT_ENDPOINT = INSTANCE_ENDPOINT + "/beat";

    private static final String INSTANCE_METADATA_BATCH_ENDPOINT = INSTANCE_ENDPOINT + "/metadata/batch";

    public static final String INSTANCE_HEALTH_ENDPOINT = "/v2/ns/health/instance";

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
        completeInstance(instance, namespaceId, groupName, serviceName);
        return instance;
    }

    @Override
    public InstancesList getInstancesList(String namespaceId, String groupName, String clusterName, String serviceName, String ip, Integer port, Boolean healthyOnly, String app) {
        OpenApiRequest request = OpenApiRequest.Builder.create(INSTANCES_LIST_ENDPOINT)
                .method(HttpMethod.GET)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(SERVICE_GROUP_NAME, groupName)
                .queryParameter(CLUSTER_NAME, clusterName)
                .queryParameter(SERVICE_NAME, serviceName)
                .queryParameter(INSTANCE_IP, ip)
                .queryParameter(INSTANCE_PORT, port)
                .queryParameter(INSTANCE_HEALTHY_ONLY, healthyOnly)
                .queryParameter(APP_V2, app)
                .build();

        InstancesList instancesList = this.openApiClient.executeAsResult(request, InstancesList.class);

        setPropertyIfAbsent(namespaceId, instancesList::getNamespaceId, instancesList::setNamespaceId);
        setPropertyIfAbsent(groupName, instancesList::getGroupName, instancesList::setGroupName);
        setPropertyIfAbsent(serviceName, instancesList::getServiceName, instancesList::setServiceName);

        completeInstances(instancesList.getHosts(), namespaceId, groupName, serviceName);

        return instancesList;
    }

    @Override
    public Heartbeat sendHeartbeat(Instance instance) {
        OpenApiRequest request = createRequestBuilder(instance, INSTANCE_HEARTBEAT_ENDPOINT, PUT)
                .queryParameter(SERVICE_NAME, buildServiceName(instance.getGroupName(), instance.getServiceName()))
                .queryParameter(HEARTBEAT, getHeartbeatMap(instance))
                .build();
        return this.openApiClient.execute(request, Heartbeat.class);
    }

    @Override
    public boolean updateHealth(UpdateHealthInstance updateHealthInstance) {
        OpenApiRequest request = buildHealthRequest(updateHealthInstance, PUT);
        return executeAsBoolean(request);
    }

    @Override
    public boolean batchUpdateMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType) {
        return batchMetadata(instances, metadata, consistencyType, PUT);
    }

    @Override
    public boolean batchDeleteMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType) {
        return batchMetadata(instances, metadata, consistencyType, DELETE);
    }

    private boolean batchMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType, HttpMethod method) {
        OpenApiRequest request = createBatchMetadataRequest(instances, metadata, consistencyType, INSTANCE_METADATA_BATCH_ENDPOINT, method);
        return executeAsBoolean(request);
    }

    private OpenApiRequest buildInstanceRequest(NewInstance instance, HttpMethod method) {
        return requestBuilder(instance, method).build();
    }

    private OpenApiRequest.Builder requestBuilder(NewInstance instance, HttpMethod method) {
        return requestBuilder((GenericInstance) instance, method)
                .queryParameter(INSTANCE_HEALTHY, instance.getHealthy());
    }

    private OpenApiRequest buildHealthRequest(UpdateHealthInstance instance, HttpMethod method) {
        return createRequestBuilder(instance, INSTANCE_ENDPOINT, method)
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
        return createRequestBuilder(instance, INSTANCE_ENDPOINT, method);
    }

    private boolean executeAsBoolean(OpenApiRequest request) {
        return executeAsResultMessageOK(this.openApiClient, request);
    }
}
