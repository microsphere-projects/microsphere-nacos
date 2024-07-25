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

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.common.config.model.Config;
import io.microsphere.nacos.client.common.config.model.HistoryConfig;
import io.microsphere.nacos.client.common.model.Page;
import org.junit.jupiter.api.Test;

import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_CONFIG_APP_NAME;
import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_CONFIG_CONTENT;
import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_CONFIG_DESCRIPTION;
import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_CONFIG_EFFECT;
import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_CONFIG_OPERATOR;
import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_CONFIG_SCHEMA;
import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_CONFIG_TAGS;
import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_CONFIG_USE;
import static io.microsphere.nacos.client.v1.config.ConfigClientTest.TEST_DATA_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The {@link ConfigClientV2} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ConfigClientV2
 * @since 1.0.0
 */
public class ConfigClientV2Test extends OpenApiTest {

    @Test
    public void test() {
        ConfigClientV2 client = new OpenApiConfigClientV2(this.openApiClient, this.nacosClientConfig);

        // Delete first
        assertTrue(client.deleteConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID));

        // Test publishConfigContent()
        assertTrue(client.publishConfigContent(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, TEST_CONFIG_CONTENT));

        String newContent = "New Content for testing...";
        assertTrue(client.publishConfigContent(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, newContent));

        // Test publishConfig()
        Config config = newConfig();
        assertTrue(client.publishConfig(config));

        // Test getConfigContent()
        String content = client.getConfigContent(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID);
        assertTrue(TEST_CONFIG_CONTENT.equals(content));

        // Test getHistoryConfigs
        Page<HistoryConfig> historyConfigs = client.getHistoryConfigs(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID);
        assertFalse(historyConfigs.isEmpty());
        for (HistoryConfig historyConfig : historyConfigs.getElements()) {
            assertEquals(TEST_NAMESPACE_ID, historyConfig.getNamespaceId());
            assertEquals(TEST_GROUP_NAME, historyConfig.getGroup());
            assertEquals(TEST_DATA_ID, historyConfig.getDataId());
            // Test getHistoryConfig
            assertNotNull(client.getHistoryConfig(historyConfig.getNamespaceId(), historyConfig.getGroup(), historyConfig.getDataId(), historyConfig.getRevision()));
        }

        // Test deleteConfig()
        assertTrue(client.deleteConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID));

    }

    private Config newConfig() {
        Config config = new Config();
        config.setNamespaceId(TEST_NAMESPACE_ID);
        config.setGroup(TEST_GROUP_NAME);
        config.setDataId(TEST_DATA_ID);
        config.setContent(TEST_CONFIG_CONTENT);
        config.setTags(TEST_CONFIG_TAGS);
        config.setAppName(TEST_CONFIG_APP_NAME);
        config.setOperator(TEST_CONFIG_OPERATOR);
        config.setDescription(TEST_CONFIG_DESCRIPTION);
        config.setEffect(TEST_CONFIG_EFFECT);
        config.setUse(TEST_CONFIG_USE);
        config.setSchema(TEST_CONFIG_SCHEMA);
        return config;
    }
}
