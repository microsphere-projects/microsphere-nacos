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
import io.microsphere.nacos.client.common.discovery.model.BatchMetadataResult;
import io.microsphere.nacos.client.common.discovery.model.Heartbeat;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.InstancesList;
import io.microsphere.nacos.client.common.discovery.model.NewInstance;
import io.microsphere.nacos.client.common.discovery.model.UpdateHealthInstance;
import io.microsphere.nacos.client.transport.OpenApiClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.microsphere.nacos.client.ErrorCode.BAD_REQUEST;
import static io.microsphere.nacos.client.util.ModelUtils.buildServiceName;
import static io.microsphere.nacos.client.v1.discovery.InstanceClientTest.assertBaseInstance;
import static io.microsphere.nacos.client.v1.discovery.InstanceClientTest.createInstance;
import static io.microsphere.nacos.client.v1.discovery.ServiceClientTest.TEST_CLUSTER;
import static io.microsphere.nacos.client.v1.discovery.ServiceClientTest.TEST_SERVICE_NAME;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        assertBaseInstance(this.instance);
        assertEquals(TEST_CLUSTER, instance.getClusterName());


        // Test sendHeartbeat()
        Heartbeat heartbeat = client.sendHeartbeat(instance);
        assertTrue(heartbeat.getCode() > 0);
        assertTrue(heartbeat.getClientBeatInterval() > 0);
        assertTrue(heartbeat.isLightBeatEnabled());


        // Test updateHealth()
        OpenApiClientException exception = null;
        try {
            UpdateHealthInstance updateHealthInstance = new UpdateHealthInstance(true).from(instance);
            client.updateHealth(updateHealthInstance);
        } catch (OpenApiClientException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(BAD_REQUEST, exception.getErrorCode());


        // Test refresh()
        newInstance.setWeight(50.0);
        assertTrue(client.refresh(newInstance));

        String ip = newInstance.getIp();
        int port = newInstance.getPort();


        // Test getInstance()
        Instance exsitedInstance = client.getInstance(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_CLUSTER, TEST_SERVICE_NAME, ip, port);
        assertInstance(newInstance, exsitedInstance);


        // Test getInstancesList()
        InstancesList instancesList = client.getInstancesList(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_CLUSTER, TEST_SERVICE_NAME, ip, port);
        assertEquals(TEST_NAMESPACE_ID, instancesList.getNamespaceId());
        assertEquals(TEST_GROUP_NAME, instancesList.getGroupName());
        assertEquals(TEST_CLUSTER, instancesList.getClusters());
        assertEquals(TEST_SERVICE_NAME, instancesList.getServiceName());
        assertEquals(buildServiceName(TEST_GROUP_NAME, TEST_SERVICE_NAME), instancesList.getName());
        assertNotNull(instancesList.getCacheMillis());
        assertNotNull(instancesList.getLastRefTime());
        assertNotNull(instancesList.getChecksum());
        assertNotNull(instancesList.getAllIPs());
        assertNotNull(instancesList.getReachProtectionThreshold());
        assertNotNull(instancesList.getValid());

        List<Instance> instances = instancesList.getHosts();
        assertEquals(1, instances.size());
        Instance instance1 = instances.get(0);
        assertInstance(newInstance, instance1);

        // Test batchUpdateMetadata()
        Map<String, String> metadata = singletonMap("test-key-2", "test-value-2");
        BatchMetadataResult result = client.batchUpdateMetadata(asList(instance1), metadata);
        // FIXME: The result is different in the OpenAPI V1
        assertTrue(result.getUpdated().isEmpty());

        exsitedInstance = client.getInstance(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_CLUSTER, TEST_SERVICE_NAME, ip, port);
        Map<String, String> metadata1 = exsitedInstance.getMetadata();
        assertEquals("test-value", metadata1.get("test-key"));
        assertEquals("test-value-2", metadata1.get("test-key-2"));


        // Test batchDeleteMetadata()
        result = client.batchDeleteMetadata(asList(instance1), metadata);
        // FIXME: The result is different in the OpenAPI V1
        assertTrue(result.getUpdated().isEmpty());


        // Test deregister()
        assertTrue(client.deregister(this.instance));
    }

    private void assertInstance(NewInstance one, NewInstance another) {
        assertEquals(one.getNamespaceId(), another.getNamespaceId());
        assertEquals(one.getGroupName(), another.getGroupName());
        assertEquals(one.getClusterName(), another.getClusterName());
        assertEquals(one.getServiceName(), another.getServiceName());
        assertEquals(one.getIp(), another.getIp());
        assertEquals(one.getPort(), another.getPort());
        assertEquals(one.getHealthy(), another.getHealthy());
        assertEquals(one.getWeight(), another.getWeight());
    }
}
