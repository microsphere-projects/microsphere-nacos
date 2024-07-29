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
package io.microsphere.nacos.client.v1.discovery;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.common.OpenApiTemplateClient;
import io.microsphere.nacos.client.common.discovery.ConsistencyType;
import io.microsphere.nacos.client.common.discovery.InstanceClient;
import io.microsphere.nacos.client.common.discovery.model.BaseInstance;
import io.microsphere.nacos.client.common.discovery.model.BatchMetadataResult;
import io.microsphere.nacos.client.common.discovery.model.DeleteInstance;
import io.microsphere.nacos.client.common.discovery.model.GenericInstance;
import io.microsphere.nacos.client.common.discovery.model.Heartbeat;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.InstancesList;
import io.microsphere.nacos.client.common.discovery.model.NewInstance;
import io.microsphere.nacos.client.common.discovery.model.QueryInstance;
import io.microsphere.nacos.client.common.discovery.model.Service;
import io.microsphere.nacos.client.common.discovery.model.UpdateHealthInstance;
import io.microsphere.nacos.client.common.discovery.model.UpdateInstance;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.transport.OpenApiRequestParam;
import io.microsphere.nacos.client.util.ModelUtils;

import java.util.Map;

import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.APP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CLUSTERS;
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
import static io.microsphere.nacos.client.util.ModelUtils.completeInstances;
import static io.microsphere.nacos.client.util.ModelUtils.getHeartbeatMap;
import static io.microsphere.nacos.client.util.ModelUtils.setPropertyIfAbsent;
import static io.microsphere.nacos.client.util.OpenApiUtils.createBatchMetadataRequest;
import static io.microsphere.nacos.client.util.OpenApiUtils.createRequestBuilder;
import static io.microsphere.nacos.client.util.OpenApiUtils.executeAsMessageOK;
import static io.microsphere.nacos.client.util.StringUtils.isBlank;

/**
 * The {@link Service} {@link Instance} for <a href="https://nacos.io/en/docs/v1/open-api/#service-discovery">Open API V1 - Service Discovery</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClient
 * @see OpenApiClient
 * @since 1.0.0
 */
public class OpenApiInstanceClient extends OpenApiTemplateClient implements InstanceClient {

    protected static final String INSTANCE_ENDPOINT = "/ns/instance";

    protected static final String INSTANCES_LIST_ENDPOINT = INSTANCE_ENDPOINT + "/list";

    protected static final String INSTANCE_HEARTBEAT_ENDPOINT = INSTANCE_ENDPOINT + "/beat";

    protected static final String INSTANCE_METADATA_BATCH_ENDPOINT = INSTANCE_ENDPOINT + "/metadata/batch";

    protected static final String INSTANCE_HEALTH_ENDPOINT = "/ns/health/instance";

    public OpenApiInstanceClient(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        super(openApiClient, nacosClientConfig);
    }

    @Override
    public boolean register(NewInstance newInstance) {
        OpenApiRequest request = instanceRequestBuilder(newInstance, POST).build();
        return responseBoolean(request);
    }

    @Override
    public boolean deregister(DeleteInstance instance) {
        OpenApiRequest request = instanceRequestBuilder(instance, DELETE).build();
        return responseBoolean(request);
    }

    @Override
    public boolean refresh(UpdateInstance updateInstance) {
        OpenApiRequest request = instanceRequestBuilder(updateInstance, PUT).build();
        return responseBoolean(request);
    }

    @Override
    public Instance getInstance(QueryInstance queryInstance) {
        OpenApiRequest request = instanceRequestBuilder(queryInstance, GET).build();
        Instance instance = response(request, Instance.class);
        completeInstance(instance, queryInstance);
        return instance;
    }

    @Override
    public InstancesList getInstancesList(String namespaceId, String groupName, String clusterName, String serviceName,
                                          String ip, Integer port, Boolean healthyOnly, String app) {

        OpenApiRequestParam clusterParam = isOpenApiV1() ? CLUSTERS : CLUSTER_NAME;

        OpenApiRequest request = OpenApiRequest.Builder.create(getInstancesListEndpoint())
                .method(HttpMethod.GET)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(SERVICE_GROUP_NAME, groupName)
                .queryParameter(clusterParam, clusterName)
                .queryParameter(SERVICE_NAME, serviceName)
                .queryParameter(INSTANCE_IP, ip)
                .queryParameter(INSTANCE_PORT, port)
                .queryParameter(INSTANCE_HEALTHY_ONLY, healthyOnly)
                .queryParameter(APP, app)
                .build();

        InstancesList instancesList = response(request, InstancesList.class);

        setPropertyIfAbsent(namespaceId, instancesList::getNamespaceId, instancesList::setNamespaceId);
        setPropertyIfAbsent(groupName, instancesList::getGroupName, instancesList::setGroupName);
        setPropertyIfAbsent(serviceName, instancesList::getServiceName, instancesList::setServiceName);

        // the "clusters" is "" in the V1 Endpoint, but is "DEFAULT" in the V2 Endpoint
        if (isBlank(instancesList.getClusters())) {
            instancesList.setClusters(clusterName);
        }

        // the "dom" is serviceName in the V1 Endpoint, but is null in the V2 Endpoint
        if (isBlank(instancesList.getDom())) {
            instancesList.setDom(serviceName);
        }

        completeInstances(instancesList.getHosts(), namespaceId, groupName, serviceName);

        return instancesList;
    }

    @Override
    public Heartbeat sendHeartbeat(Instance instance) {
        OpenApiRequest request = createRequestBuilder(getInstanceHeartbeatEndpoint(), PUT, instance)
                .queryParameter(HEARTBEAT, getHeartbeatMap(instance))
                .build();
        return response(request, Heartbeat.class);
    }

    @Override
    public boolean updateHealth(UpdateHealthInstance updateHealthInstance) {
        OpenApiRequest request = buildHealthRequest(updateHealthInstance, PUT);
        return responseBoolean(request);
    }

    @Override
    public BatchMetadataResult batchUpdateMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType) {
        return batchMetadata(instances, metadata, consistencyType, PUT);
    }

    @Override
    public BatchMetadataResult batchDeleteMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType) {
        return batchMetadata(instances, metadata, consistencyType, DELETE);
    }

    private BatchMetadataResult batchMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType, HttpMethod method) {
        OpenApiRequest request = createBatchMetadataRequest(instances, metadata, consistencyType, getInstanceMetadataBatchEndpoint(), method);
        return response(request, BatchMetadataResult.class);
    }

    private OpenApiRequest buildHealthRequest(UpdateHealthInstance instance, HttpMethod method) {
        return createRequestBuilder(getInstanceHealthEndpoint(), method, instance)
                .queryParameter(INSTANCE_HEALTHY, instance.isHealthy())
                .build();
    }

    private OpenApiRequest.Builder instanceRequestBuilder(NewInstance instance, HttpMethod method) {
        return instanceRequestBuilder((GenericInstance) instance, method)
                .queryParameter(INSTANCE_HEALTHY, instance.getHealthy());
    }

    protected OpenApiRequest.Builder instanceRequestBuilder(GenericInstance instance, HttpMethod method) {
        return instanceRequestBuilder((BaseInstance) instance, method)
                .queryParameter(INSTANCE_WEIGHT, instance.getWeight())
                .queryParameter(INSTANCE_ENABLED, instance.getEnabled())
                .queryParameter(INSTANCE_EPHEMERAL, instance.getEphemeral())
                .queryParameter(METADATA, instance.getMetadata());
    }

    protected OpenApiRequest.Builder instanceRequestBuilder(BaseInstance instance, HttpMethod method) {
        return createRequestBuilder(getInstanceEndpoint(), method, instance);
    }

    protected void completeInstance(Instance instance, BaseInstance baseInstance) {
        String namespaceId = baseInstance.getNamespaceId();
        String groupName = baseInstance.getGroupName();
        String serviceName = baseInstance.getServiceName();
        ModelUtils.completeInstance(instance, namespaceId, groupName, serviceName);
    }

    protected String getInstanceEndpoint() {
        return getEndpointPath() + INSTANCE_ENDPOINT;
    }

    protected String getInstancesListEndpoint() {
        return getEndpointPath() + INSTANCES_LIST_ENDPOINT;
    }

    protected String getInstanceHeartbeatEndpoint() {
        return getEndpointPath() + INSTANCE_HEARTBEAT_ENDPOINT;
    }

    protected String getInstanceMetadataBatchEndpoint() {
        return getEndpointPath() + INSTANCE_METADATA_BATCH_ENDPOINT;
    }

    protected String getInstanceHealthEndpoint() {
        return getEndpointPath() + INSTANCE_HEALTH_ENDPOINT;
    }

    protected boolean responseBoolean(OpenApiRequest request) {
        return executeAsMessageOK(this.openApiClient, request);
    }
}
