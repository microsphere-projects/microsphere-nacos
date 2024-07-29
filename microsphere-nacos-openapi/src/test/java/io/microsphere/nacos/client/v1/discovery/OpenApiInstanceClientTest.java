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
package io.microsphere.nacos.client.v1.discovery;

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.common.discovery.InstanceClient;
import io.microsphere.nacos.client.common.discovery.model.BaseInstance;
import io.microsphere.nacos.client.common.discovery.model.BatchMetadataResult;
import io.microsphere.nacos.client.common.discovery.model.DeleteInstance;
import io.microsphere.nacos.client.common.discovery.model.Heartbeat;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.discovery.model.InstancesList;
import io.microsphere.nacos.client.common.discovery.model.NewInstance;
import io.microsphere.nacos.client.common.discovery.model.QueryInstance;
import io.microsphere.nacos.client.common.discovery.model.UpdateHealthInstance;
import io.microsphere.nacos.client.common.discovery.model.UpdateInstance;
import io.microsphere.nacos.client.transport.OpenApiClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.microsphere.nacos.client.ErrorCode.INTERNAL_SERVER_ERROR;
import static io.microsphere.nacos.client.v1.discovery.ServiceClientTest.TEST_CLUSTER;
import static io.microsphere.nacos.client.v1.discovery.ServiceClientTest.TEST_SERVICE_NAME;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiInstanceClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClient
 * @see OpenApiInstanceClient
 * @since 1.0.0
 */
public class OpenApiInstanceClientTest extends OpenApiTest {

    public static final String TEST_INSTANCE_IP = "127.0.0.1";

    public static final int TEST_INSTANCE_PORT = 8080;

    public static final double TEST_INSTANCE_WEIGHT = 100.00;

    public static final boolean TEST_INSTANCE_ENABLED = true;

    public static final boolean TEST_INSTANCE_HEALTHY = true;

    public static final boolean TEST_INSTANCE_EPHEMERAL = true;

    public static final Map<String, String> TEST_INSTANCE_METADATA = singletonMap("test-key", "test-value");

    private InstanceClient client;

    private Instance instance;

    @BeforeEach
    public void before() {
        this.client = new OpenApiInstanceClient(this.openApiClient, this.nacosClientConfig);
        this.instance = createInstance();
        DeleteInstance deleteInstance = new DeleteInstance();
        deleteInstance.from(instance);
        this.client.deregister(deleteInstance);
    }

    @Test
    public void test() {

        // Test register()
        NewInstance newInstance = new NewInstance().from(instance);
        assertTrue(client.register(newInstance));
        assertBaseInstance(instance);
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
        assertEquals(INTERNAL_SERVER_ERROR, exception.getErrorCode());


        // Test getInstance()
        await(5);
        QueryInstance queryInstance = new QueryInstance().from(instance);
        Instance exsitedInstance = client.getInstance(queryInstance);
        assertBaseInstance(exsitedInstance);
        assertEquals(newInstance.getIp(), exsitedInstance.getIp());
        assertEquals(newInstance.getPort(), exsitedInstance.getPort());
        assertEquals(newInstance.getServiceName(), exsitedInstance.getServiceName());
        assertEquals(newInstance.getClusterName(), exsitedInstance.getClusterName());
        assertEquals(newInstance.getHealthy(), exsitedInstance.getHealthy());
        assertEquals(newInstance.getWeight(), exsitedInstance.getWeight());


        // Test refresh()
        instance.setWeight(50.0);
        UpdateInstance updateInstance = new UpdateInstance().from(instance);
        assertTrue(client.refresh(updateInstance));

        await(5);
        exsitedInstance = client.getInstance(queryInstance);
        assertEquals(updateInstance.getIp(), exsitedInstance.getIp());
        assertEquals(updateInstance.getPort(), exsitedInstance.getPort());
        assertEquals(updateInstance.getServiceName(), exsitedInstance.getServiceName());
        assertEquals(updateInstance.getClusterName(), exsitedInstance.getClusterName());
        assertEquals(updateInstance.getWeight(), exsitedInstance.getWeight());
        assertEquals(instance.getHealthy(), exsitedInstance.getHealthy());


        // Test getInstancesList()
        await(5);
        InstancesList instancesList = client.getInstancesList(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_SERVICE_NAME);
        assertEquals(TEST_SERVICE_NAME, instancesList.getDom());
        assertEquals(3000, instancesList.getCacheMillis());
        assertFalse(instancesList.getUseSpecifiedURL());
        assertEquals("test-group@@test-service", instancesList.getName());
        assertNotNull(instancesList.getChecksum());
        assertNotNull(instancesList.getLastRefTime());
        assertNotNull(instancesList.getEnv());
        assertTrue(instancesList.getClusters().isEmpty());
        List<Instance> instances = instancesList.getHosts();
        assertFalse(instances.isEmpty());
        instances.forEach(OpenApiInstanceClientTest::assertBaseInstance);


        // Test batchUpdateMetadata()
        Map<String, String> metadata = singletonMap("test-key-2", "test-value-2");
        BatchMetadataResult result = client.batchUpdateMetadata(asList(exsitedInstance), metadata);
        assertFalse(result.getUpdated().isEmpty());

        exsitedInstance = client.getInstance(queryInstance);
        Map<String, String> metadata1 = exsitedInstance.getMetadata();
        assertEquals("test-value", metadata1.get("test-key"));
        assertEquals("test-value-2", metadata1.get("test-key-2"));


        // Test batchDeleteMetadata()
        result = client.batchDeleteMetadata(asList(exsitedInstance), metadata);
        assertFalse(result.getUpdated().isEmpty());


        // Test deregister()
        DeleteInstance deleteInstance = new DeleteInstance().from(instance);
        assertTrue(client.deregister(deleteInstance));
    }

    public static void assertBaseInstance(BaseInstance instance) {
        assertEquals(TEST_NAMESPACE_ID, instance.getNamespaceId());
        assertEquals(TEST_GROUP_NAME, instance.getGroupName());
        assertEquals(TEST_SERVICE_NAME, instance.getServiceName());
    }

    public static Instance createInstance() {
        Instance instance = new Instance();
        instance.setNamespaceId(TEST_NAMESPACE_ID);
        instance.setGroupName(TEST_GROUP_NAME);
        instance.setServiceName(TEST_SERVICE_NAME);
        instance.setClusterName(TEST_CLUSTER);
        instance.setIp(TEST_INSTANCE_IP);
        instance.setPort(TEST_INSTANCE_PORT);
        instance.setWeight(TEST_INSTANCE_WEIGHT);
        instance.setEnabled(TEST_INSTANCE_ENABLED);
        instance.setHealthy(TEST_INSTANCE_HEALTHY);
        instance.setEphemeral(TEST_INSTANCE_EPHEMERAL);
        instance.setMetadata(TEST_INSTANCE_METADATA);
        return instance;
    }
}
