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
package io.microsphere.nacos.client.discovery.spring.cloud.autoconfigure;

import io.microsphere.nacos.client.discovery.spring.cloud.NacosDiscoveryConfiguration;
import io.microsphere.nacos.client.discovery.spring.cloud.NacosDiscoveryContextFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static io.microsphere.nacos.client.spring.util.NacosClientUtils.NACOS_CLIENTS_PROPERTY_NAME_PREFIX;

/**
 * The Auto-Configuration class for Nacos Discovery
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since Configuration
 */
@Configuration(proxyBeanMethods = false)
@Import(value = {
        NacosDiscoveryConfiguration.class,
        NacosDiscoveryAutoConfiguration.Multiple.class
})
public class NacosDiscoveryAutoConfiguration {

    @ConditionalOnProperty(prefix = NACOS_CLIENTS_PROPERTY_NAME_PREFIX)
    public static class Multiple {

        @Bean
        public NacosDiscoveryContextFactory nacosDiscoveryContextFactory(ConfigurableApplicationContext parentContext) {
            return new NacosDiscoveryContextFactory(parentContext);
        }
    }

}
