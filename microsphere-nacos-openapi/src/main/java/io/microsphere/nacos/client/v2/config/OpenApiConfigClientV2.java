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
package io.microsphere.nacos.client.v2.config;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.OpenApiVersion;
import io.microsphere.nacos.client.common.config.ConfigClient;
import io.microsphere.nacos.client.common.config.ConfigType;
import io.microsphere.nacos.client.common.config.model.Config;
import io.microsphere.nacos.client.common.config.model.NewConfig;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.config.OpenApiConfigClient;

import static io.microsphere.nacos.client.OpenApiVersion.V1;
import static io.microsphere.nacos.client.OpenApiVersion.V2;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.*;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;

/**
 * The {@link ConfigClient} for <a href="https://nacos.io/en/docs/v2/open-api/#configuration-management">Open API V2 - Configuration Management</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ConfigClient
 * @see OpenApiConfigClient
 * @since 1.0.0
 */
public class OpenApiConfigClientV2 extends OpenApiConfigClient implements ConfigClient {

    protected static final String V2_CONFIG_ENDPOINT = "/cs/config";

    protected static final String V2_CONFIG_HISTORY_LIST_ENDPOINT = "/cs/history/list";

    public OpenApiConfigClientV2(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        super(openApiClient, nacosClientConfig);
    }

    @Override
    public OpenApiVersion getOpenApiVersion() {
        return V2;
    }

    @Override
    public Config getConfig(String namespaceId, String group, String dataId) {
        // No getConfig endpoint is not found in Open API V2
        String configEndpoint = V1.getEndpointPath() + CONFIG_ENDPOINT;
        OpenApiRequest request = OpenApiRequest.Builder.create(configEndpoint)
                .method(GET)
                .queryParameter(CONFIG_TENANT, namespaceId)
                .queryParameter(CONFIG_GROUP, group)
                .queryParameter(CONFIG_DATA_ID, dataId)
                .queryParameter(SHOW, "all")
                .build();
        return this.openApiClient.execute(request, Config.class);
    }

    @Override
    public boolean publishConfigContent(String namespaceId, String group, String dataId, String content, ConfigType configType) {
        NewConfig newConfig = new NewConfig();
        newConfig.setNamespaceId(namespaceId);
        newConfig.setGroup(group);
        newConfig.setDataId(dataId);
        newConfig.setContent(content);
        if (configType != null) {
            newConfig.setType(configType);
        }
        return publishConfig(newConfig);
    }

    @Override
    public boolean publishConfig(NewConfig newConfig) {
        String namespaceId = newConfig.getNamespaceId();
        String group = newConfig.getGroup();
        String dataId = newConfig.getDataId();
        String content = newConfig.getContent();
        String tags = collectionToCommaDelimitedString(newConfig.getTags());
        String appName = newConfig.getAppName();
        String operator = newConfig.getOperator();
        String description = newConfig.getDescription();
        String use = newConfig.getUse();
        String effect = newConfig.getEffect();
        String schema = newConfig.getSchema();
        ConfigType configType = newConfig.getType();
        String type = configType == null ? null : configType.getValue();
        OpenApiRequest request = configRequestBuilder(namespaceId, group, dataId, null, POST)
                .queryParameter(CONFIG_CONTENT, content)
                .queryParameter(CONFIG_TAGS_V2, tags)
                .queryParameter(CONFIG_APP, appName)
                .queryParameter(OPERATOR_V2, operator)
                .queryParameter(DESCRIPTION, description)
                .queryParameter(CONFIG_USE, use)
                .queryParameter(CONFIG_EFFECT, effect)
                .queryParameter(CONFIG_SCHEMA, schema)
                .queryParameter(CONFIG_TYPE, type.toLowerCase())
                .build();
        return response(request, Boolean.class);
    }

    @Override
    protected OpenApiRequest.Builder requestBuilder(String endpoint, String namespaceId, String group, String dataId,
                                                    String tag, HttpMethod method) {
        return OpenApiRequest.Builder.create(endpoint).method(method)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(CONFIG_GROUP, group)
                .queryParameter(CONFIG_DATA_ID, dataId)
                .queryParameter(CONFIG_TAG, tag)
                ;
    }

    @Override
    protected String getConfigEndpoint() {
        return getEndpointPath() + V2_CONFIG_ENDPOINT;
    }

    @Override
    protected String getConfigHistoryListEndpoint() {
        return getEndpointPath() + V2_CONFIG_HISTORY_LIST_ENDPOINT;
    }
}

