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
package io.microsphere.nacos.client.v2;

import io.microsphere.nacos.client.Client;
import io.microsphere.nacos.client.common.auth.AuthenticationClient;
import io.microsphere.nacos.client.common.config.ConfigClient;
import io.microsphere.nacos.client.common.discovery.InstanceClient;
import io.microsphere.nacos.client.common.discovery.ServiceClient;
import io.microsphere.nacos.client.common.namespace.NamespaceClient;
import io.microsphere.nacos.client.v1.NacosClient;

import java.util.List;

/**
 * Nacos Client for Open API V2
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see AuthenticationClient
 * @see ConfigClient
 * @see ServiceClient
 * @see InstanceClient
 * @see NamespaceClient
 * @see NacosClient
 * @since 1.0.0
 */
public interface NacosClientV2 extends AuthenticationClient, ConfigClient, ServiceClient, InstanceClient, NamespaceClient,
        Client {

    /**
     * Get all client ids
     *
     * @return non-null
     */
    List<String> getAllClientIds();
}
