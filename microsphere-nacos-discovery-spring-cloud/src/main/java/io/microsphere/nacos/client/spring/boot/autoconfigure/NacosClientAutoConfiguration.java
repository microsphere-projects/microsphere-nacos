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
package io.microsphere.nacos.client.spring.boot.autoconfigure;

import io.microsphere.nacos.client.spring.NacosClientConfiguration;
import io.microsphere.nacos.client.spring.boot.NacosClientProperties;
import io.microsphere.spring.boot.condition.ConditionalOnPropertyPrefix;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static io.microsphere.nacos.client.spring.boot.NacosClientProperties.PREFIX;

/**
 * The Auto-{@link Configuration Configuration} class for Nacos Client
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since Configuration
 */
@ConditionalOnPropertyPrefix(PREFIX)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(NacosClientProperties.class)
@Import(value = {
        NacosClientConfiguration.class
})
public class NacosClientAutoConfiguration {
}
