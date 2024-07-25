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

import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.constants.Constants;
import io.microsphere.nacos.client.v1.config.ConfigClient;
import io.microsphere.nacos.client.common.config.model.Config;
import io.microsphere.nacos.client.common.config.model.HistoryConfig;
import io.microsphere.nacos.client.v1.namespace.model.Namespace;

import static io.microsphere.nacos.client.constants.Constants.DEFAULT_GROUP_NAME;
import static io.microsphere.nacos.client.constants.Constants.DEFAULT_NAMESPACE_ID;
import static io.microsphere.nacos.client.constants.Constants.PAGE_NUMBER;

/**
 * The Client for Nacos {@link Config} V2
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Config
 * @see ConfigClient
 * @since 1.0.0
 */
public interface ConfigClientV2 {

    /**
     * The default page size
     */
    int DEFAULT_PAGE_SIZE = 100;

    /**
     * The maximum page size
     */
    int MAX_PAGE_SIZE = 500;

    /**
     * Get the content of {@link Config} from the specified {@code dataId} for
     * the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} and
     * the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"}
     *
     * @param dataId the data id of {@link Config}
     * @return the content of {@link Config} if found, otherwise {@code null}
     */
    default String getConfigContent(String dataId) {
        return getConfigContent(DEFAULT_GROUP_NAME, dataId);
    }

    /**
     * Get the content of {@link Config} from the specified {@code group} and {@code dataId} for
     * the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     *
     * @param group  the group of {@link Config}
     * @param dataId the data id of {@link Config}
     * @return the content of {@link Config} if found, otherwise {@code null}
     */
    default String getConfigContent(String group, String dataId) {
        return getConfigContent(DEFAULT_NAMESPACE_ID, group, dataId);
    }

    /**
     * Get the content of {@link Config} from the specified {@code namespaceId}, {@code group} and {@code dataId}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}, (optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @return the content of {@link Config} if found, otherwise {@code null}
     */
    default String getConfigContent(String namespaceId, String group, String dataId) {
        return getConfigContent(namespaceId, group, dataId, null);
    }

    /**
     * Get the content of {@link Config} from the specified {@code namespaceId}, {@code group}, {@code dataId} and {@code tag}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}, (optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @param tag         the tag of {@link Config}
     * @return the content of {@link Config} if found, otherwise {@code null}
     */
    String getConfigContent(String namespaceId, String group, String dataId, String tag);

    /**
     * Publish(or Update) the content of {@link Config} with {@code group} and {@code dataId} to
     * the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} and
     * the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"}
     *
     * @param dataId  the data id of {@link Config}
     * @param content the content of {@link Config}
     * @return <code>true</code> if publish successfully, otherwise <code>false</code>
     */
    default boolean publishConfigContent(String dataId, String content) {
        return publishConfigContent(DEFAULT_NAMESPACE_ID, DEFAULT_GROUP_NAME, dataId, content);
    }

    /**
     * Publish(or Update) the content of {@link Config} with {@code group} and {@code dataId} to
     * the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     *
     * @param group   the group of {@link Config}
     * @param dataId  the data id of {@link Config}
     * @param content the content of {@link Config}
     * @return <code>true</code> if publish successfully, otherwise <code>false</code>
     */
    default boolean publishConfigContent(String group, String dataId, String content) {
        return publishConfigContent(DEFAULT_NAMESPACE_ID, group, dataId, content);
    }

    /**
     * Publish(or Update) the content of {@link Config} with the specified {@code namespaceId}, {@code group} and
     * {@code dataId}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}(optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @param content     the content of {@link Config}
     * @return <code>true</code> if publish successfully, otherwise <code>false</code>
     */
    default boolean publishConfigContent(String namespaceId, String group, String dataId, String content) {
        Config config = new Config();
        config.setNamespaceId(namespaceId);
        config.setGroup(group);
        config.setDataId(dataId);
        config.setContent(content);
        return publishConfig(config);
    }

    /**
     * Publish(or Update) a {@link Config Config}
     *
     * @param config a {@link Config Config}
     * @return <code>true</code> if publish successfully, otherwise <code>false</code>
     */
    boolean publishConfig(Config config);

    /**
     * Delete the {@link Config} with the specified {@code group} and {@code dataId} from
     * the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} and
     * the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP" group}
     *
     * @param dataId the data id of {@link Config}
     * @return <code>true</code> if delete successfully, otherwise <code>false</code>
     */
    default boolean deleteConfig(String dataId) {
        return deleteConfig(DEFAULT_GROUP_NAME, dataId);
    }

    /**
     * Delete the {@link Config} with the specified {@code group} and {@code dataId} from
     * the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     *
     * @param group  the group of {@link Config}
     * @param dataId the data id of {@link Config}
     * @return <code>true</code> if delete successfully, otherwise <code>false</code>
     */
    default boolean deleteConfig(String group, String dataId) {
        return deleteConfig(DEFAULT_NAMESPACE_ID, group, dataId);
    }

    /**
     * Delete the {@link Config} with the specified {@code namespaceId}, {@code group} and {@code dataId}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}(optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @return <code>true</code> if delete successfully, otherwise <code>false</code>
     */
    default boolean deleteConfig(String namespaceId, String group, String dataId) {
        return deleteConfig(namespaceId, group, dataId, null);
    }

    /**
     * Delete the {@link Config} with the specified {@code namespaceId}, {@code group}, {@code dataId} and {@code tag}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}(optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @param tag         the tag of {@link Config}
     * @return <code>true</code> if delete successfully, otherwise <code>false</code>
     */
    boolean deleteConfig(String namespaceId, String group, String dataId, String tag);

    /**
     * Get the pagination of {@link HistoryConfig HistoryConfigs} by the specified namespaceId and group and dataId
     * using the configured page number and page size, from the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     * and the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP" group}
     *
     * @param dataId the data id of {@link Config}
     * @return non-null {@link Page< HistoryConfig >}
     */
    default Page<HistoryConfig> getHistoryConfigs(String dataId) {
        return getHistoryConfigs(DEFAULT_GROUP_NAME, dataId);
    }

    /**
     * Get the pagination of {@link HistoryConfig HistoryConfigs} by the specified namespaceId and group and dataId
     * using the configured page number and page size, from the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     *
     * @param group  the group of {@link Config}
     * @param dataId the data id of {@link Config}
     * @return non-null {@link Page< HistoryConfig >}
     */
    default Page<HistoryConfig> getHistoryConfigs(String group, String dataId) {
        return getHistoryConfigs(null, group, dataId);
    }

    /**
     * Get the pagination of {@link HistoryConfig HistoryConfigs} by the specified namespaceId and group and dataId
     * using the configured page number and page size
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}(optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @return non-null {@link Page< HistoryConfig >}
     */
    default Page<HistoryConfig> getHistoryConfigs(String namespaceId, String group, String dataId) {
        return getHistoryConfigs(namespaceId, group, dataId, PAGE_NUMBER, DEFAULT_PAGE_SIZE);
    }

    /**
     * Get the pagination of {@link HistoryConfig HistoryConfigs} by the specified namespaceId and group and dataId
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}(optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @param pageNumber  the number of page, starts with 1
     * @param pageSize    the expected size of one page
     * @return non-null {@link Page< HistoryConfig >}
     * @throws IllegalArgumentException if the {@code pageNumber} or {@code pageSize} is less than 1,
     *                                  and the {@code pageSize} is greater than {@link #MAX_PAGE_SIZE 500}
     */
    Page<HistoryConfig> getHistoryConfigs(String namespaceId, String group, String dataId, int pageNumber, int pageSize);

    /**
     * Get the {@link HistoryConfig HistoryConfig} by the specified {@code namespaceId}, {@code group}, {@code dataId}
     * and {@code revision} from the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} and the
     * {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP" group}
     *
     * @param dataId   the data id of {@link Config}
     * @param revision {@link HistoryConfig#getRevision() the revision of HistoryConfig}
     * @return {@link HistoryConfig} if found, otherwise <code>null</code>
     */
    default HistoryConfig getHistoryConfig(String dataId, long revision) {
        return getHistoryConfig(DEFAULT_GROUP_NAME, dataId, revision);
    }

    /**
     * Get the {@link HistoryConfig HistoryConfig} by the specified {@code namespaceId}, {@code group}, {@code dataId}
     * and {@code revision} from the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     *
     * @param group    the group of {@link Config}
     * @param dataId   the data id of {@link Config}
     * @param revision {@link HistoryConfig#getRevision() the revision of HistoryConfig}
     * @return {@link HistoryConfig} if found, otherwise <code>null</code>
     */
    default HistoryConfig getHistoryConfig(String group, String dataId, long revision) {
        return getHistoryConfig(DEFAULT_NAMESPACE_ID, group, dataId, revision);
    }

    /**
     * Get the {@link HistoryConfig HistoryConfig} by the specified {@code namespaceId}, {@code group}, {@code dataId}
     * and {@code revision}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}(optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @param revision    {@link HistoryConfig#getRevision() the revision of HistoryConfig}
     * @return {@link HistoryConfig} if found, otherwise <code>null</code>
     */
    HistoryConfig getHistoryConfig(String namespaceId, String group, String dataId, long revision);

    /**
     * Get the {@link HistoryConfig HistoryConfig} by the specified {@code namespaceId}, {@code group}, {@code dataId}
     * and {@code id} from the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} and the
     * {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP" group}
     *
     * @param dataId the data id of {@link Config}
     * @param id     {@link Config#getId()  the id of Config}
     * @return {@link HistoryConfig} if found, otherwise <code>null</code>
     */
    default HistoryConfig getPreviousHistoryConfig(String dataId, String id) {
        return getPreviousHistoryConfig(DEFAULT_GROUP_NAME, dataId, id);
    }

    /**
     * Get the {@link HistoryConfig HistoryConfig} by the specified {@code namespaceId}, {@code group}, {@code dataId}
     * and {@code id} from the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     *
     * @param group  the group of {@link Config}
     * @param dataId the data id of {@link Config}
     * @param id     {@link Config#getId()  the id of Config}
     * @return {@link HistoryConfig} if found, otherwise <code>null</code>
     */
    default HistoryConfig getPreviousHistoryConfig(String group, String dataId, String id) {
        return getPreviousHistoryConfig(DEFAULT_NAMESPACE_ID, group, dataId, id);
    }

    /**
     * Get the {@link HistoryConfig HistoryConfig} by the specified {@code namespaceId}, {@code group}, {@code dataId}
     * and {@code id}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}(optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @param id          {@link Config#getId()  the id of Config}
     * @return {@link HistoryConfig} if found, otherwise <code>null</code>
     * @since Nacos 1.4.0
     */
    HistoryConfig getPreviousHistoryConfig(String namespaceId, String group, String dataId, String id);
}
