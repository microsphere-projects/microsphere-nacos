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

import io.microsphere.nacos.client.common.discovery.model.Instance;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.Map;

/**
 * The {@link ServiceInstance} class for Nacos Discovery
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since ServiceInstance
 */
public class NacosServiceInstance implements ServiceInstance {

    private final Instance instance;

    public NacosServiceInstance(Instance instance) {
        this.instance = instance;
    }

    @Override
    public String getInstanceId() {
        return this.instance.getInstanceId();
    }

    @Override
    public String getServiceId() {
        return this.instance.getService();
    }

    @Override
    public String getHost() {
        return this.instance.getIp();
    }

    @Override
    public int getPort() {
        return this.instance.getPort();
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public URI getUri() {
        return DefaultServiceInstance.getUri(this);
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.instance.getMetadata();
    }

    @Override
    public String getScheme() {
        return "http";
    }

    public Instance getInstance() {
        return instance;
    }
}
