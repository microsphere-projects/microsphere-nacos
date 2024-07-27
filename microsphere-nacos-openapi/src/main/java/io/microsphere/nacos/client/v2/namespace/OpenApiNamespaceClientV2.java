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
package io.microsphere.nacos.client.v2.namespace;

import io.microsphere.nacos.client.common.namespace.model.Namespace;
import io.microsphere.nacos.client.common.namespace.model.NamespacesList;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiClientException;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.common.namespace.NamespaceClient;

import java.util.List;

import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_DESCRIPTION;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_NAME;

/**
 * The {@link NamespaceClient} for <a href="https://nacos.io/en/docs/v1/open-api/#namespace">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see NamespaceClient
 * @since 1.0.0
 */
public class OpenApiNamespaceClientV2 implements NamespaceClient {

    public static final String NAMESPACE_ENDPOINT_V2 = "/v2/console/namespace";

    public static final String NAMESPACES_LIST_ENDPOINT_V2 = "/v2/console/namespace/list";

    private final OpenApiClient openApiClient;

    public OpenApiNamespaceClientV2(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public List<Namespace> getAllNamespaces() {
        OpenApiRequest request = OpenApiRequest.Builder.create(NAMESPACES_LIST_ENDPOINT_V2)
                .build();
        NamespacesList namespacesList = this.openApiClient.execute(request, NamespacesList.class);
        return namespacesList.getData();
    }

    @Override
    public Namespace getNamespace(String namespaceId) {
        OpenApiRequest request = buildNamespaceRequest(GET, namespaceId);
        Namespace namespace = null;
        try {
            namespace = this.openApiClient.executeAsResult(request, Namespace.class);
        } catch (OpenApiClientException e) {
            // TODO log
        }
        return namespace;
    }

    @Override
    public boolean createNamespace(String namespaceId, String namespaceName, String namespaceDesc) {
        OpenApiRequest request = buildNamespaceRequest(POST, namespaceId, namespaceName, namespaceDesc);
        return executeAsBoolean(request);
    }

    @Override
    public boolean updateNamespace(String namespaceId, String namespaceName, String namespaceDesc) {
        OpenApiRequest request = buildNamespaceRequest(PUT, namespaceId, namespaceName, namespaceDesc);
        return executeAsBoolean(request);
    }

    @Override
    public boolean deleteNamespace(String namespaceId) {
        OpenApiRequest request = buildNamespaceRequest(DELETE, namespaceId);
        return executeAsBoolean(request);
    }

    private OpenApiRequest buildNamespaceRequest(HttpMethod method, String namespaceId) {
        return buildNamespaceRequest(method, namespaceId, null, null);
    }

    private boolean executeAsBoolean(OpenApiRequest request) {
        return this.openApiClient.executeAsResult(request, Boolean.class);
    }

    private OpenApiRequest buildNamespaceRequest(HttpMethod method, String namespaceId, String namespaceName, String namespaceDesc) {
        return OpenApiRequest.Builder.create(NAMESPACE_ENDPOINT_V2)
                .method(method)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(NAMESPACE_NAME, namespaceName)
                .queryParameter(NAMESPACE_DESCRIPTION, namespaceDesc)
                .build();
    }
}
