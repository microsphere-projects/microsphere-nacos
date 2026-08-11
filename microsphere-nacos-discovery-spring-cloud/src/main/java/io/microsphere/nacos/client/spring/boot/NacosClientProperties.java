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
package io.microsphere.nacos.client.spring.boot;

import io.microsphere.nacos.client.NacosClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static io.microsphere.nacos.client.constants.Constants.PROPERTY_NAME_PREFIX;
import static io.microsphere.nacos.client.spring.boot.NacosClientProperties.PREFIX;

/**
 * The {@link ConfigurationProperties @ConfigurationProperties} class for Nacos Client
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since NacosClientConfig
 */
@ConfigurationProperties(prefix = PREFIX)
public class NacosClientProperties extends NacosClientConfig {

    public static final String PREFIX = PROPERTY_NAME_PREFIX;

    private Discovery discovery = new Discovery();

    public Discovery getDiscovery() {
        return discovery;
    }

    public void setDiscovery(Discovery discovery) {
        this.discovery = discovery;
    }

    public static class Discovery {

        private String namespaceId;

        public String getNamespaceId() {
            return namespaceId;
        }

        public void setNamespaceId(String namespaceId) {
            this.namespaceId = namespaceId;
        }
    }
}
