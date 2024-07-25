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
package io.microsphere.nacos.client.v2.discovery;

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.NewInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.microsphere.nacos.client.v1.discovery.InstanceClientTest.assertBaseInstance;
import static io.microsphere.nacos.client.v1.discovery.InstanceClientTest.createInstance;
import static io.microsphere.nacos.client.v1.discovery.ServiceClientTest.TEST_CLUSTER;
import static io.microsphere.nacos.client.v1.discovery.ServiceClientTest.TEST_SERVICE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link InstanceClientV2} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClientV2
 * @since 1.0.0
 */
public class InstanceClientV2Test extends OpenApiTest {

    private Instance instance;

    @BeforeEach
    public void before() {
        this.instance = createInstance();
    }

    @Test
    public void test() {
        InstanceClientV2 client = new OpenApiInstanceClientV2(this.openApiClient, this.nacosClientConfig);

        // Test register()
        NewInstance newInstance = new NewInstance().from(instance);
        assertTrue(client.register(newInstance));
        assertBaseInstance(instance);
        assertEquals(TEST_CLUSTER, instance.getClusterName());

        // Test refresh()
        newInstance.setWeight(50.0);
        assertTrue(client.refresh(newInstance));

        // Test getInstance()
        Instance instance1 = client.getInstance(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_CLUSTER, TEST_SERVICE_NAME, newInstance.getIp(), newInstance.getPort());
        assertEquals(newInstance.getNamespaceId(), instance1.getNamespaceId());
        assertEquals(newInstance.getGroupName(), instance1.getGroupName());
        assertEquals(newInstance.getClusterName(), instance1.getClusterName());
        assertEquals(newInstance.getServiceName(), instance1.getServiceName());
        assertEquals(newInstance.getIp(), instance1.getIp());
        assertEquals(newInstance.getPort(), instance1.getPort());
        assertEquals(newInstance.getHealthy(), instance1.getHealthy());
        assertEquals(newInstance.getWeight(), instance1.getWeight());

        // Test deregister()
        assertTrue(client.deregister(instance));
    }
}
