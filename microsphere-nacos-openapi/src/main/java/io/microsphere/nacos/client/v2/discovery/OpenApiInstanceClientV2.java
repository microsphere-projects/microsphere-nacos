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
import io.microsphere.nacos.client.OpenApiVersion;
import io.microsphere.nacos.client.common.discovery.InstanceClient;
import io.microsphere.nacos.client.common.discovery.model.Heartbeat;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.discovery.OpenApiInstanceClient;

import static io.microsphere.nacos.client.OpenApiVersion.V2;
import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.HEARTBEAT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_NAME;
import static io.microsphere.nacos.client.util.ModelUtils.buildServiceName;
import static io.microsphere.nacos.client.util.ModelUtils.getHeartbeatMap;
import static io.microsphere.nacos.client.util.OpenApiUtils.createRequestBuilder;
import static io.microsphere.nacos.client.util.OpenApiUtils.executeAsResultMessageOK;

/**
 * The {@link InstanceClient} for <a href="https://nacos.io/en/docs/latest/manual/user/open-api">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClient
 * @see OpenApiInstanceClient
 * @see OpenApiClient
 * @since 1.0.0
 */
public class OpenApiInstanceClientV2 extends OpenApiInstanceClient {

    public OpenApiInstanceClientV2(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        super(openApiClient, nacosClientConfig);
    }

    @Override
    public OpenApiVersion getOpenApiVersion() {
        return V2;
    }

    @Override
    public Heartbeat sendHeartbeat(Instance instance) {
        OpenApiRequest request = createRequestBuilder(getInstanceHeartbeatEndpoint(), PUT, instance)
                .queryParameter(SERVICE_NAME, buildServiceName(instance.getGroupName(), instance.getServiceName()))
                .queryParameter(HEARTBEAT, getHeartbeatMap(instance))
                .build();
        return this.openApiClient.execute(request, Heartbeat.class);
    }

    protected boolean responseBoolean(OpenApiRequest request) {
        return executeAsResultMessageOK(this.openApiClient, request);
    }
}
