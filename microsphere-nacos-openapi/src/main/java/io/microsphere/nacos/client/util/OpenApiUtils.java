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
package io.microsphere.nacos.client.util;

import io.microsphere.nacos.client.common.discovery.ConsistencyType;
import io.microsphere.nacos.client.common.discovery.model.BaseInstance;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.transport.OpenApiResponse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.microsphere.nacos.client.common.discovery.ConsistencyType.EPHEMERAL;
import static io.microsphere.nacos.client.constants.Constants.DEFAULT_CLUSTER_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CLUSTER_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONSISTENCY_TYPE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCES;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_IP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_PORT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.METADATA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_GROUP_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_NAME;
import static io.microsphere.nacos.client.util.ModelUtils.buildServiceName;
import static java.lang.String.format;

/**
 * The utils for OpenAPI
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiClient
 * @see OpenApiRequest
 * @see OpenApiResponse
 * @since 1.0.0
 */
public abstract class OpenApiUtils {

    /**
     * The response message of OK
     */
    public static final String RESPONSE_MESSAGE_OK = "ok";

    /**
     * Check whether the response message is OK
     *
     * @param openApiClient {@link OpenApiClient}
     * @param request       {@link OpenApiRequest}
     * @return <code>true</code> if OK, otherwise <code>false</code>
     */
    public static boolean executeMessageOK(OpenApiClient openApiClient, OpenApiRequest request) {
        String message = openApiClient.execute(request, String.class);
        return RESPONSE_MESSAGE_OK.equals(message);
    }

    /**
     * Check whether the response message is OK
     *
     * @param openApiClient {@link OpenApiClient}
     * @param request       {@link OpenApiRequest}
     * @return <code>true</code> if OK, otherwise <code>false</code>
     */
    public static boolean executeAsResultMessageOK(OpenApiClient openApiClient, OpenApiRequest request) {
        String message = openApiClient.executeAsResult(request, String.class);
        return RESPONSE_MESSAGE_OK.equals(message);
    }

    /**
     * Create the {@link OpenApiRequest.Builder} for {@link BaseInstance Nacos Service Instance}
     *
     * @param instance {@link BaseInstance Nacos Service Instance}
     * @param endpoint the endpoint of {@link BaseInstance Nacos Service Instance}
     * @param method   {@link HttpMethod}
     * @return {@link OpenApiRequest.Builder}
     */
    public static OpenApiRequest.Builder createRequestBuilder(BaseInstance instance, String endpoint, HttpMethod method) {
        return OpenApiRequest.Builder.create(endpoint)
                .method(method)
                .queryParameter(NAMESPACE_ID, instance.getNamespaceId())
                .queryParameter(SERVICE_GROUP_NAME, instance.getGroupName())
                .queryParameter(SERVICE_NAME, instance.getServiceName())
                .queryParameter(CLUSTER_NAME, instance.getClusterName())
                .queryParameter(INSTANCE_IP, instance.getIp())
                .queryParameter(INSTANCE_PORT, instance.getPort());
    }

    public static OpenApiRequest createBatchMetadataRequest(Iterable<Instance> instances, Map<String, String> metadata,
                                                            ConsistencyType consistencyType, String endpoint, HttpMethod method) {
        OpenApiRequest.Builder requestBuilder = OpenApiRequest.Builder.create(endpoint)
                .method(method);

        Set<String> namespaceIds = new HashSet<>(2);
        Set<String> serviceNames = new HashSet<>(2);
        Set<String> groupNames = new HashSet<>(2);
        List<Map<String, String>> instanceMaps = new LinkedList<>();

        boolean isV1Endpoint = endpoint.startsWith("/v1/");

        consistencyType = consistencyType == null ? EPHEMERAL : consistencyType;

        for (Instance instance : instances) {
            String namespaceId = instance.getNamespaceId();
            String groupName = instance.getGroupName();
            String serviceName = isV1Endpoint ? buildServiceName(groupName, instance.getServiceName()) : instance.getServiceName();
            validateDuplication(instance, namespaceIds, "namespaceId", namespaceId);
            validateDuplication(instance, groupNames, "groupName", groupName);
            validateDuplication(instance, serviceNames, "serviceName", serviceName);
            Map<String, String> instanceMap = buildInstanceMap(instance, consistencyType);
            instanceMaps.add(instanceMap);
        }

        String namespaceId = namespaceIds.iterator().next();
        String groupName = groupNames.iterator().next();
        String serviceName = serviceNames.iterator().next();

        requestBuilder
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(SERVICE_GROUP_NAME, groupName)
                .queryParameter(SERVICE_NAME, serviceName)
                .queryParameter(CONSISTENCY_TYPE, consistencyType)
                .queryParameter(INSTANCES, instanceMaps)
                .queryParameter(METADATA, metadata);

        return requestBuilder.build();
    }

    private static void validateDuplication(Instance instance, Set<String> values, String key, String value) {
        values.add(value);
        if (values.size() > 1) {
            String errorMessage = format("Instance[ip : %s , port : %d] with duplicated '%s' : %s",
                    instance.getIp(), instance.getPort(), key, value);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static Map<String, String> buildInstanceMap(Instance instance, ConsistencyType consistencyType) {
        Map<String, String> map = new HashMap<>(4);
        boolean ephemeral = instance.getEphemeral() == null ? EPHEMERAL.equals(consistencyType) : instance.getEphemeral();
        String clusterName = instance.getClusterName() == null ? DEFAULT_CLUSTER_NAME : instance.getClusterName();
        map.put("ip", instance.getIp());
        map.put("port", String.valueOf(instance.getPort()));
        map.put("ephemeral", String.valueOf(ephemeral));
        map.put("clusterName", clusterName);
        return map;
    }
}
