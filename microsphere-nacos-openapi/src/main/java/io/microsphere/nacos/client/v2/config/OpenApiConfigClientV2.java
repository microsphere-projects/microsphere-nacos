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
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.common.model.StringResult;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.config.model.Config;
import io.microsphere.nacos.client.v1.config.model.HistoryConfig;
import io.microsphere.nacos.client.v1.config.model.HistoryConfigPage;

import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.APP_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_CONTENT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_DATA_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_EFFECT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_GROUP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_REVISION;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_SCHEMA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TAG;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TAGS_V2;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_USE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.DESCRIPTION;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.OPERATOR_V2;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PAGE_NUMBER;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PAGE_SIZE;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;

/**
 * The {@link ConfigClientV2} for for <a href="https://nacos.io/en/docs/v2/open-api/#configuration-management">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ConfigClientV2
 * @since 1.0.0
 */
public class OpenApiConfigClientV2 implements ConfigClientV2 {

    private static final String CONFIG_ENDPOINT = "/v2/cs/config";

    private static final String CONFIG_HISTORY_ENDPOINT = "/v2/cs/history";

    private static final String CONFIG_HISTORY_LIST_ENDPOINT = "/v2/cs/history/list";

    private static final String CONFIG_HISTORY_PREVIOUS_ENDPOINT = "/v2/cs/history/previous";

    private final OpenApiClient openApiClient;

    private final NacosClientConfig nacosClientConfig;

    public OpenApiConfigClientV2(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        this.openApiClient = openApiClient;
        this.nacosClientConfig = nacosClientConfig;
    }

    @Override
    public String getConfigContent(String namespaceId, String group, String dataId, String tag) {
        OpenApiRequest request = buildGetConfigRequest(namespaceId, group, dataId, tag);
        StringResult result = openApiClient.execute(request, StringResult.class);
        return result.isSuccess() ? result.getData() : null;
    }

    @Override
    public boolean publishConfig(Config config) {
        String namespaceId = config.getNamespaceId();
        String group = config.getGroup();
        String dataId = config.getDataId();
        String content = config.getContent();
        String tags = collectionToCommaDelimitedString(config.getTags());
        String appName = config.getAppName();
        String operator = config.getOperator();
        String description = config.getDescription();
        String use = config.getUse();
        String effect = config.getEffect();
        String schema = config.getSchema();
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
        return responseBoolean(request);
    }


    @Override
    public boolean deleteConfig(String namespaceId, String group, String dataId, String tag) {
        OpenApiRequest request = configRequestBuilder(namespaceId, group, dataId, tag, DELETE).build();
        return responseBoolean(request);
    }

    @Override
    public Page<HistoryConfig> getHistoryConfigs(String namespaceId, String group, String dataId, int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            throw new IllegalArgumentException("The argument 'pageNumber' must be greater than 0");
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("The argument 'pageSize' must be greater than 0");
        }
        if (pageSize > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("The argument 'pageSize' must less than or equal 1");
        }

        OpenApiRequest request = requestBuilder(CONFIG_HISTORY_LIST_ENDPOINT, namespaceId, group, dataId, GET)
                .queryParameter(PAGE_NUMBER, pageNumber)
                .queryParameter(PAGE_SIZE, pageSize)
                .build();

        HistoryConfigPage page = this.openApiClient.executeAsResult(request, HistoryConfigPage.class);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public HistoryConfig getHistoryConfig(String namespaceId, String group, String dataId, long revision) {
        OpenApiRequest request = requestBuilder(CONFIG_HISTORY_ENDPOINT, namespaceId, group, dataId, GET)
                .queryParameter(CONFIG_REVISION, revision)
                .build();
        return responseHistoryConfig(request);
    }

    @Override
    public HistoryConfig getPreviousHistoryConfig(String namespaceId, String group, String dataId, String id) {
        OpenApiRequest request = requestBuilder(CONFIG_HISTORY_PREVIOUS_ENDPOINT, namespaceId, group, dataId, GET)
                .queryParameter(CONFIG_ID, id)
                .build();
        return responseHistoryConfig(request);
    }

    private OpenApiRequest buildGetConfigRequest(String namespaceId, String group, String dataId, String tag) {
        return configRequestBuilder(namespaceId, group, dataId, tag, GET)
                .build();
    }

    private OpenApiRequest.Builder configRequestBuilder(String namespaceId, String group, String dataId, String tag, HttpMethod method) {
        return requestBuilder(CONFIG_ENDPOINT, namespaceId, group, dataId, tag, method);
    }

    private OpenApiRequest.Builder requestBuilder(String endpoint, String namespaceId, String group, String dataId,
                                                  HttpMethod method) {
        return requestBuilder(endpoint, namespaceId, group, dataId, null, method);
    }

    private OpenApiRequest.Builder requestBuilder(String endpoint, String namespaceId, String group, String dataId,
                                                  String tag, HttpMethod method) {
        return OpenApiRequest.Builder.create(endpoint).method(method)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(CONFIG_GROUP, group)
                .queryParameter(CONFIG_DATA_ID, dataId)
                .queryParameter(CONFIG_TAG, tag)
                ;
    }

    private boolean responseBoolean(OpenApiRequest request) {
        return this.openApiClient.executeAsResult(request, Boolean.class);
    }

    private HistoryConfig responseHistoryConfig(OpenApiRequest request) {
        return this.openApiClient.executeAsResult(request, HistoryConfig.class);
    }
}

