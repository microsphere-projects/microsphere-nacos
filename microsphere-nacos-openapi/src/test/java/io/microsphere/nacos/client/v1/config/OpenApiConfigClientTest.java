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
import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.common.config.ConfigClient;
import io.microsphere.nacos.client.common.config.ConfigType;
import io.microsphere.nacos.client.common.config.model.BaseConfig;
import io.microsphere.nacos.client.common.config.model.Config;
import io.microsphere.nacos.client.common.config.model.HistoryConfig;
import io.microsphere.nacos.client.common.config.model.NewConfig;
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.common.config.event.ConfigChangedEvent;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiConfigClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiConfigClient
 * @since 1.0.0
 */
public class OpenApiConfigClientTest extends OpenApiTest {

    public static final String TEST_DATA_ID = "test-config";

    public static final String TEST_CONFIG_CONTENT = "Test Config Content...";

    public static final String TEST_CONFIG_TAG = "test-tag";

    public static final Set<String> TEST_CONFIG_TAGS = new HashSet<>(asList("test-tag-1", "test-tag-2", "test-tag-3"));

    public static final String TEST_CONFIG_APP_NAME = "test-app";

    public static final String TEST_CONFIG_DESCRIPTION = "This is a description for test-config";

    public static final String TEST_CONFIG_OPERATOR = "microsphere-nacos-client";

    public static final String TEST_CONFIG_USE = "test";

    public static final String TEST_CONFIG_EFFECT = "Effect 1";

    public static final String TEST_CONFIG_SCHEMA = "test config schema";

    public static final ConfigType CONFIG_TYPE = ConfigType.TEXT;

    public static final int LONG_POLLING_TIMEOUT = 5000;

    @Override
    protected void customize(NacosClientConfig nacosClientConfig) {
        nacosClientConfig.setLongPollingTimeout(LONG_POLLING_TIMEOUT);
    }

    @Test
    public void test() throws Exception {
        ConfigClient client = new OpenApiConfigClient(this.openApiClient, this.nacosClientConfig);

        AtomicReference<ConfigChangedEvent> eventRef = new AtomicReference<>();
        // Test
        client.addEventListener(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, e -> {
            eventRef.set(e);
        });

        // Test deleteConfig()
        assertTrue(client.deleteConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID));

        // Test getConfig()
        Config config = client.getConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID);
        assertNull(config);

        // test publishConfig() to create a new config
        NewConfig newConfig = createNewConfig();
        assertTrue(client.publishConfig(newConfig));

        awaitEvent(eventRef);
        assertConfigChangedEvent(eventRef, ConfigChangedEvent.Kind.CREATED, newConfig.getContent());


        // test publishConfigContent() to update the content
        String newContent = "New Content for testing...";
        assertTrue(client.publishConfigContent(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, newContent));

        awaitEvent(eventRef);
        assertConfigChangedEvent(eventRef, ConfigChangedEvent.Kind.MODIFIED, newContent);

        // test getHistoryConfigs()
        Page<HistoryConfig> page = client.getHistoryConfigs(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID);
        assertTrue(page.isFirst());
        assertEquals(1, page.getPageNumber());
        assertEquals(ConfigClient.DEFAULT_PAGE_SIZE, page.getPageSize());
        List<HistoryConfig> historyConfigs = page.getElements();
        assertTrue(historyConfigs.size() > 1);
        HistoryConfig historyConfig = historyConfigs.get(0);
        assertHistoryConfig(historyConfig);

        // test getHistoryConfig();
        HistoryConfig historyConfig1 = client.getHistoryConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, historyConfig.getRevision());
        assertHistoryConfig(historyConfig1);
        assertEquals(historyConfig.getRevision(), historyConfig1.getRevision());
        assertEquals(historyConfig.getNid(), historyConfig1.getNid());
        assertEquals(historyConfig.getLastRevision(), historyConfig1.getLastRevision());
        assertEquals(historyConfig.getOperator(), historyConfig1.getOperator());
        assertEquals(historyConfig.getOperatorIp(), historyConfig1.getOperatorIp());
        assertEquals(historyConfig.getOperationType(), historyConfig1.getOperationType());
        assertEquals(historyConfig.getCreatedTime(), historyConfig1.getCreatedTime());
        assertEquals(historyConfig.getLastModifiedTime(), historyConfig1.getLastModifiedTime());
        assertNotNull(historyConfig1.getMd5());
        assertNotNull(historyConfig1.getContent());


        config = client.getConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID);
        String id = config.getId();
        assertConfig(config);

        // Test getPreviousHistoryConfig()
        HistoryConfig historyConfig2 = client.getPreviousHistoryConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, id);
        assertEquals(config.getNamespaceId(), historyConfig2.getNamespaceId());
        assertEquals(config.getGroup(), historyConfig2.getGroup());
        assertEquals(config.getDataId(), historyConfig2.getDataId());
        assertEquals(config.getAppName(), historyConfig2.getAppName());

        // Test deleteConfig()
        assertTrue(client.deleteConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID));

        awaitEvent(eventRef);
        assertConfigChangedEvent(eventRef, ConfigChangedEvent.Kind.DELETED, newContent);
    }

    private void assertConfigChangedEvent(AtomicReference<ConfigChangedEvent> eventRef, ConfigChangedEvent.Kind kind, String content) {
        ConfigChangedEvent event = eventRef.get();
        assertEquals(TEST_NAMESPACE_ID, event.getNamespaceId());
        assertEquals(TEST_GROUP_NAME, event.getGroup());
        assertEquals(TEST_DATA_ID, event.getDataId());
        assertEquals(content, event.getContent());
        assertEquals(kind, event.getKind());
        eventRef.set(null);
    }

    private void awaitEvent(AtomicReference<ConfigChangedEvent> eventRef) throws InterruptedException {
        while (eventRef.get() == null) {
            Thread.sleep(1000);
        }
    }

    private void assertHistoryConfig(HistoryConfig historyConfig) {
        assertBaseConfig(historyConfig);
        assertNotNull(historyConfig.getRevision());
        assertNotNull(historyConfig.getLastRevision());
        assertNotNull(historyConfig.getOperationType());
    }

    private void assertConfig(Config config) {
        assertBaseConfig(config);
        assertNotNull(config.getId());
        assertNotNull(config.getContent());
        assertNotNull(config.getDescription());
        assertNotNull(config.getTags());
        assertNotNull(config.getMd5());
        assertNotNull(config.getUse());
        assertNotNull(config.getEffect());
        assertNotNull(config.getSchema());
        assertNotNull(config.getType());
    }

    private void assertBaseConfig(BaseConfig config) {
        assertEquals(TEST_NAMESPACE_ID, config.getNamespaceId());
        assertEquals(TEST_GROUP_NAME, config.getGroup());
        assertEquals(TEST_DATA_ID, config.getDataId());
        assertEquals(TEST_CONFIG_APP_NAME, config.getAppName());
        assertNotNull(config.getOperatorIp());
        assertNotNull(config.getOperator());
        assertNotNull(config.getCreatedTime());
        assertNotNull(config.getLastModifiedTime());
    }

    private NewConfig createNewConfig() {
        NewConfig newConfig = new NewConfig();
        newConfig.setNamespaceId(TEST_NAMESPACE_ID);
        newConfig.setGroup(TEST_GROUP_NAME);
        newConfig.setDataId(TEST_DATA_ID);
        newConfig.setContent(TEST_CONFIG_CONTENT);
        newConfig.setTags(TEST_CONFIG_TAGS);
        newConfig.setAppName(TEST_CONFIG_APP_NAME);
        newConfig.setDescription(TEST_CONFIG_DESCRIPTION);
        newConfig.setOperator(TEST_CONFIG_OPERATOR);
        newConfig.setUse(TEST_CONFIG_USE);
        newConfig.setEffect(TEST_CONFIG_EFFECT);
        newConfig.setSchema(TEST_CONFIG_SCHEMA);
        newConfig.setType(CONFIG_TYPE);
        return newConfig;
    }
}
