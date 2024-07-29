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
import io.microsphere.nacos.client.common.config.ConfigClient;
import io.microsphere.nacos.client.common.config.ConfigType;
import io.microsphere.nacos.client.common.config.model.NewConfig;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.config.OpenApiConfigClient;

import java.lang.reflect.Type;

import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.APP_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_CONTENT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_DATA_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_EFFECT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_GROUP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_SCHEMA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TAG;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TAGS_V2;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_USE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.DESCRIPTION;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.OPERATOR_V2;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;

/**
 * The {@link ConfigClient} for <a href="https://nacos.io/en/docs/v2/open-api/#configuration-management">Open API V2</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ConfigClient
 * @since 1.0.0
 */
public class OpenApiConfigClientV2 extends OpenApiConfigClient implements ConfigClient {

    public static final String V2_CONFIG_ENDPOINT = "/v2/cs/config";

    public static final String V2_CONFIG_HISTORY_ENDPOINT = "/v2/cs/history";

    public static final String V2_CONFIG_HISTORY_LIST_ENDPOINT = "/v2/cs/history/list";

    public static final String V2_CONFIG_HISTORY_PREVIOUS_ENDPOINT = "/v2/cs/history/previous";

    public OpenApiConfigClientV2(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        super(openApiClient, nacosClientConfig);
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
        OpenApiRequest request = configRequestBuilder(namespaceId, group, dataId, null, POST)
                .queryParameter(CONFIG_CONTENT, content)
                .queryParameter(CONFIG_TAGS_V2, tags)
                .queryParameter(APP_NAME, appName)
                .queryParameter(OPERATOR_V2, operator)
                .queryParameter(DESCRIPTION, description)
                .queryParameter(CONFIG_USE, use)
                .queryParameter(CONFIG_EFFECT, effect)
                .queryParameter(CONFIG_SCHEMA, schema)
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
        return V2_CONFIG_ENDPOINT;
    }

    @Override
    protected String getConfigHistoryEndpoint() {
        return V2_CONFIG_HISTORY_ENDPOINT;
    }

    @Override
    protected String getConfigHistoryListEndpoint() {
        return V2_CONFIG_HISTORY_LIST_ENDPOINT;
    }

    @Override
    protected String getConfigHistoryPreviousEndpoint() {
        return V2_CONFIG_HISTORY_PREVIOUS_ENDPOINT;
    }

    @Override
    protected <T> T response(OpenApiRequest request, Type dataType) {
        return this.openApiClient.executeAsResult(request, dataType);
    }
}

