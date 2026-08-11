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

import io.microsphere.nacos.client.common.discovery.InstanceClient;
import io.microsphere.nacos.client.common.discovery.ServiceClient;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.InstancesList;
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.spring.boot.NacosClientProperties;
import io.microsphere.nacos.client.transport.OpenApiHttpClient;
import io.microsphere.nacos.client.v1.discovery.OpenApiInstanceClient;
import io.microsphere.nacos.client.v1.discovery.OpenApiServiceClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@link DiscoveryClient} class for Nacos Discovery
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see InstanceClient
 * @see ServiceClient
 * @since 1.0.0
 */
public class NacosDiscoveryClient implements DiscoveryClient {

    private final ServiceClient serviceClient;

    private final InstanceClient instanceClient;

    private final NacosClientProperties nacosClientProperties;

    private final String namespaceId;

    public NacosDiscoveryClient(OpenApiHttpClient openApiHttpClient, NacosClientProperties nacosClientProperties) {
        this.serviceClient = new OpenApiServiceClient(openApiHttpClient, nacosClientProperties);
        this.instanceClient = new OpenApiInstanceClient(openApiHttpClient, nacosClientProperties);
        this.nacosClientProperties = nacosClientProperties;
        this.namespaceId = nacosClientProperties.getDiscovery().getNamespaceId();
    }

    @Override
    public String description() {
        return "DiscoveryClient - Nacos";
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        String serviceName = serviceId;
        InstancesList instancesList = instanceClient.getInstancesList(namespaceId, serviceName);
        List<Instance> instances = instancesList.getHosts();
        return instances.stream().map(NacosServiceInstance::new).collect(Collectors.toList());
    }

    @Override
    public List<String> getServices() {
        List<String> allServiceNames = new LinkedList<>();

        int pageNumber = 0;
        Page<String> serviceNames = serviceClient.getServiceNames(namespaceId, pageNumber);

        allServiceNames.addAll(serviceNames.getElements());

        while (serviceNames.hasNext()) {
            pageNumber = serviceNames.getPageSize();
            serviceNames = serviceClient.getServiceNames(namespaceId, pageNumber);
            allServiceNames.addAll(serviceNames.getElements());
        }

        return allServiceNames;
    }
}
