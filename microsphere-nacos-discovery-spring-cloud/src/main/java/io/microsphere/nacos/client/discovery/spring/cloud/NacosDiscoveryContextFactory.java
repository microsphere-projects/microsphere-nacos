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
package io.microsphere.nacos.client.discovery.spring.cloud;

import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

import static io.microsphere.nacos.client.spring.boot.NacosClientProperties.PREFIX;
import static io.microsphere.nacos.client.spring.util.NacosClientUtils.getNacosCilentPropertySource;

/**
 * {@link NamedContextFactory} Class for Nacos Discovery
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since NamedContextFactory
 */
public class NacosDiscoveryContextFactory extends NamedContextFactory<NacosDiscoverySpecification> {

    private final ConfigurableApplicationContext parentContext;

    private static final String NACOS_DISCOVERY_PROPERTY_NAME = "nacos-discovery";

    public NacosDiscoveryContextFactory(ConfigurableApplicationContext parentContext) {
        super(NacosDiscoveryConfiguration.class, NACOS_DISCOVERY_PROPERTY_NAME, PREFIX + "name");
        setApplicationContext(parentContext);
        this.parentContext = parentContext;
    }

    @Override
    public GenericApplicationContext buildContext(String name) {
        GenericApplicationContext context = super.buildContext(name);
        PropertySource nacosClientPropertySource = getNacosCilentPropertySource(this.parentContext.getEnvironment(), name);
        ConfigurableEnvironment environment = context.getEnvironment();
        environment.getPropertySources().addAfter(NACOS_DISCOVERY_PROPERTY_NAME, nacosClientPropertySource);
        return context;
    }
}
