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
package io.microsphere.nacos.client.v1.config;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.common.OpenApiTemplateClient;
import io.microsphere.nacos.client.common.config.ConfigClient;
import io.microsphere.nacos.client.common.config.ConfigType;
import io.microsphere.nacos.client.common.config.event.ConfigChangedListener;
import io.microsphere.nacos.client.common.config.model.Config;
import io.microsphere.nacos.client.common.config.model.HistoryConfig;
import io.microsphere.nacos.client.common.config.model.HistoryConfigPage;
import io.microsphere.nacos.client.common.config.model.NewConfig;
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;

import static io.microsphere.nacos.client.constants.Constants.SEARCH_PARAM_VALUE;
import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_APP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_CONTENT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_DATA_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_EFFECT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_GROUP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_REVISION;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_SCHEMA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_SEARCH;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TAG;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TAGS;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TENANT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TYPE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_USE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.DESCRIPTION;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.OPERATOR;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PAGE_NUMBER;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PAGE_SIZE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SHOW;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;

/**
 * The {@link ConfigClient} for for <a href="https://nacos.io/en/docs/open-api/#configuration-management">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ConfigClient
 * @see OpenApiTemplateClient
 * @since 1.0.0
 */
public class OpenApiConfigClient extends OpenApiTemplateClient implements ConfigClient {

    protected static final String CONFIG_ENDPOINT = "/cs/configs";

    protected static final String CONFIG_HISTORY_ENDPOINT = "/cs/history";

    protected static final String CONFIG_HISTORY_PREVIOUS_ENDPOINT = "/cs/history/previous";

    protected final ConfigListenerManager configListenerManager;

    public OpenApiConfigClient(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        super(openApiClient, nacosClientConfig);
        this.configListenerManager = new ConfigListenerManager(this, openApiClient, nacosClientConfig);
    }

    @Override
    public String getConfigContent(String namespaceId, String group, String dataId, String tag) {
        OpenApiRequest request = buildGetConfigRequest(namespaceId, group, dataId, tag, false);
        return response(request, String.class);
    }

    @Override
    public Config getConfig(String namespaceId, String group, String dataId) {
        OpenApiRequest request = buildGetConfigRequest(namespaceId, group, dataId, null, true);
        return response(request, Config.class);
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
                .queryParameter(CONFIG_TAGS, tags)
                .queryParameter(CONFIG_APP, appName)
                .queryParameter(OPERATOR, operator)
                .queryParameter(DESCRIPTION, description)
                .queryParameter(CONFIG_USE, use)
                .queryParameter(CONFIG_EFFECT, effect)
                .queryParameter(CONFIG_SCHEMA, schema)
                .queryParameter(CONFIG_TYPE, type).build();
        return response(request, Boolean.class);
    }

    @Override
    public boolean deleteConfig(String namespaceId, String group, String dataId, String tag) {
        OpenApiRequest request = configRequestBuilder(namespaceId, group, dataId, tag, DELETE).build();
        return response(request, Boolean.class);
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

        OpenApiRequest request = requestBuilder(getConfigHistoryListEndpoint(), namespaceId, group, dataId, GET)
                .queryParameter(CONFIG_SEARCH, SEARCH_PARAM_VALUE)
                .queryParameter(PAGE_NUMBER, pageNumber)
                .queryParameter(PAGE_SIZE, pageSize)
                .build();

        HistoryConfigPage page = response(request, HistoryConfigPage.class);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public HistoryConfig getHistoryConfig(String namespaceId, String group, String dataId, long revision) {
        OpenApiRequest request = requestBuilder(getConfigHistoryEndpoint(), namespaceId, group, dataId, GET)
                .queryParameter(CONFIG_REVISION, revision)
                .build();
        return response(request, HistoryConfig.class);
    }

    @Override
    public HistoryConfig getPreviousHistoryConfig(String namespaceId, String group, String dataId, String id) {
        OpenApiRequest request = requestBuilder(getConfigHistoryPreviousEndpoint(), namespaceId, group, dataId, GET)
                .queryParameter(CONFIG_ID, id)
                .build();
        return response(request, HistoryConfig.class);
    }

    @Override
    public void addEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener) {
        this.configListenerManager.addEventListener(namespaceId, group, dataId, listener);
    }

    @Override
    public void removeEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener) {
        this.configListenerManager.removeEventListener(namespaceId, group, dataId, listener);
    }

    protected OpenApiRequest buildGetConfigRequest(String namespaceId, String group, String dataId, String tag, boolean showDetails) {
        return configRequestBuilder(namespaceId, group, dataId, tag, GET)
                .queryParameter(SHOW, showDetails ? "all" : null)
                .build();
    }

    protected OpenApiRequest.Builder configRequestBuilder(String namespaceId, String group, String dataId, String tag, HttpMethod method) {
        return requestBuilder(getConfigEndpoint(), namespaceId, group, dataId, tag, method);
    }

    protected OpenApiRequest.Builder requestBuilder(String endpoint, String namespaceId, String group, String dataId, HttpMethod method) {
        return requestBuilder(endpoint, namespaceId, group, dataId, null, method);
    }

    protected OpenApiRequest.Builder requestBuilder(String endpoint, String namespaceId, String group, String dataId, String tag, HttpMethod method) {
        return OpenApiRequest.Builder.create(endpoint).method(method)
                .queryParameter(CONFIG_TENANT, namespaceId)
                .queryParameter(CONFIG_GROUP, group)
                .queryParameter(CONFIG_DATA_ID, dataId)
                .queryParameter(CONFIG_TAG, tag)
                ;
    }

    protected String getConfigEndpoint() {
        return getEndpointPath() + CONFIG_ENDPOINT;
    }

    protected String getConfigHistoryEndpoint() {
        return getEndpointPath() + CONFIG_HISTORY_ENDPOINT;
    }

    protected String getConfigHistoryListEndpoint() {
        return getEndpointPath() + CONFIG_HISTORY_ENDPOINT;
    }

    protected String getConfigHistoryPreviousEndpoint() {
        return getEndpointPath() + CONFIG_HISTORY_PREVIOUS_ENDPOINT;
    }
}

