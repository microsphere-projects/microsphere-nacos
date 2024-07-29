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
package io.microsphere.nacos.client.common;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.OpenApiVersion;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;

import java.lang.reflect.Type;
import java.util.Objects;

import static io.microsphere.nacos.client.OpenApiVersion.V1;
import static io.microsphere.nacos.client.OpenApiVersion.V2;

/**
 * The abstract template class of {@link OpenApiClient} is extended by Client implementations
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiClient
 * @since 1.0.0
 */
public abstract class OpenApiTemplateClient {

    protected final OpenApiClient openApiClient;

    protected final NacosClientConfig nacosClientConfig;

    protected OpenApiTemplateClient(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        Objects.requireNonNull(openApiClient, "The 'openApiClient' argument must not be null!");
        Objects.requireNonNull(nacosClientConfig, "The 'nacosClientConfig' argument must not be null!");
        this.openApiClient = openApiClient;
        this.nacosClientConfig = nacosClientConfig;
    }

    /**
     * Get the {@link OpenApiClient}
     *
     * @return non-null
     */
    public OpenApiClient getOpenApiClient() {
        return openApiClient;
    }

    /**
     * Get the {@link NacosClientConfig}
     *
     * @return non-null
     */
    public NacosClientConfig getNacosClientConfig() {
        return nacosClientConfig;
    }

    /**
     * Get the {@link OpenApiVersion}
     *
     * @return non-null
     */
    public OpenApiVersion getOpenApiVersion() {
        return V1;
    }

    /**
     * Get the endpoint path
     *
     * @return non-null
     */
    protected String getEndpointPath() {
        return getOpenApiVersion().getEndpointPath();
    }

    /**
     * Whether the {@link OpenApiVersion} is {@link OpenApiVersion#V1}
     *
     * @return {@code true} if the {@link OpenApiVersion} is {@link OpenApiVersion#V1}
     */
    protected boolean isOpenApiV1() {
        return V1.equals(getOpenApiVersion());
    }

    /**
     * Whether the {@link OpenApiVersion} is {@link OpenApiVersion#V2}
     *
     * @return {@code true} if the {@link OpenApiVersion} is {@link OpenApiVersion#V2}
     */
    protected boolean isOpenApiV2() {
        return V2.equals(getOpenApiVersion());
    }

    /**
     * Response as the specified type
     *
     * @param request     {@link OpenApiRequest}
     * @param payloadType the type of payload
     * @param <T>         the type of result
     * @return the result
     */
    protected <T> T response(OpenApiRequest request, Type payloadType) {
        if (isOpenApiV1()) {
            return this.openApiClient.execute(request, payloadType);
        }
        return this.openApiClient.executeAsResult(request, payloadType);
    }
}
