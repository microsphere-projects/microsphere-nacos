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

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.common.discovery.model.DeleteInstance;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.NewInstance;
import io.microsphere.nacos.client.v2.client.model.ClientInfo;
import io.microsphere.nacos.client.v2.client.model.ClientInstance;
import io.microsphere.nacos.client.v2.client.model.ClientSubscriber;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.microsphere.nacos.client.v1.discovery.OpenApiInstanceClientTest.TEST_INSTANCE_IP;
import static io.microsphere.nacos.client.v1.discovery.OpenApiInstanceClientTest.TEST_INSTANCE_PORT;
import static io.microsphere.nacos.client.v1.discovery.OpenApiInstanceClientTest.assertBaseInstance;
import static io.microsphere.nacos.client.v1.discovery.OpenApiInstanceClientTest.createInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiNacosClientV2} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiNacosClientV2
 * @since 1.0.0
 */
public class OpenApiNacosClientV2Test extends OpenApiTest {

    private NacosClientV2 client;

    private Instance instance;

    @Override
    protected void setup() {
        this.client = new OpenApiNacosClientV2(this.openApiClient, this.nacosClientConfig);
        this.instance = createInstance();
        this.client.deregister(DeleteInstance.build(this.instance));
    }

    @Test
    public void test() {
        NewInstance newInstance = new NewInstance().from(instance);
        // register
        assertTrue(client.register(newInstance));

        // Test getAllClientIds();
        List<String> allClientIds = client.getAllClientIds();
        assertNotNull(allClientIds);
        assertEquals(1, allClientIds.size());

        // Test getClientInfo()
        String clientId = allClientIds.get(0);
        ClientInfo clientInfo = client.getClientInfo(clientId);
        assertClientInfo(clientInfo, clientId);

        // Test getRegisteredInstances()
        List<ClientInstance> registeredInstances = client.getRegisteredInstances(clientId);
        assertEquals(1, registeredInstances.size());
        ClientInstance registeredInstance = registeredInstances.get(0);
        assertBaseInstance(registeredInstance);

        // Test getSubscribers()
        List<ClientSubscriber> clientSubscribers = client.getSubscribers(clientId);
        assertTrue(clientSubscribers.isEmpty());

        client.getInstancesList(registeredInstance.getNamespaceId(), registeredInstance.getGroupName(), registeredInstance.getServiceName());
        clientSubscribers = client.getSubscribers(clientId);

        // deregister
        assertTrue(client.deregister(DeleteInstance.build(instance)));

    }

    private void assertClientInfo(ClientInfo clientInfo, String clientId) {
        assertEquals(clientId, clientInfo.getClientId());
        assertTrue(clientInfo.isEphemeral());
        assertTrue(clientInfo.getLastUpdatedTime() < System.currentTimeMillis());
        assertEquals("ipPort", clientInfo.getClientType());
        assertEquals(TEST_INSTANCE_IP, clientInfo.getClientIp());
        assertEquals(TEST_INSTANCE_PORT, clientInfo.getClientPort());
        assertNull(clientInfo.getConnectType());
        assertNull(clientInfo.getAppName());
        assertNull(clientInfo.getVersion());
    }
}
