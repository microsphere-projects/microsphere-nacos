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
import io.microsphere.nacos.client.common.discovery.ServiceClient;
import io.microsphere.nacos.client.common.discovery.model.Selector;
import io.microsphere.nacos.client.common.discovery.model.Service;
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.transport.OpenApiClientException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.microsphere.nacos.client.constants.Constants.PAGE_NUMBER;
import static io.microsphere.nacos.client.constants.Constants.PAGE_SIZE;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiServiceClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServiceClient
 * @see OpenApiServiceClient
 * @since 1.0.0
 */
public class OpenApiServiceClientTest extends OpenApiTest {

    public static final String TEST_SERVICE_NAME = "test-service";

    public static final String TEST_CLUSTER = "DEFAULT";

    static final Set<String> TEST_CLUSTERS = singleton(TEST_CLUSTER);

    private ServiceClient client;

    @Override
    protected void setup() {
        client = createServiceClient();
        try {
            client.deleteService(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_SERVICE_NAME);
        } catch (Throwable ignore) {
        }
    }

    protected ServiceClient createServiceClient() {
        return new OpenApiServiceClient(this.openApiClient, this.nacosClientConfig);
    }

    @Test
    public void test() {

        // Test getServiceNames()
        Page<String> page = client.getServiceNames(TEST_NAMESPACE_ID, TEST_GROUP_NAME);
        List<String> serviceNames = page.getElements();

        assertEquals(PAGE_NUMBER, page.getPageNumber());
        assertEquals(PAGE_SIZE, page.getPageSize());
        assertTrue(page.isEmpty());
        assertTrue(serviceNames.isEmpty());

        // Test createService()
        // Bug? failed response from Nacos Server
        Service service = createService();
        assertTrue(client.createService(service));

        // Test getService()
        Service testService = client.getService(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_SERVICE_NAME);
        assertEquals(TEST_NAMESPACE_ID, testService.getNamespaceId());
        assertEquals(TEST_GROUP_NAME, testService.getGroupName());
        assertEquals(TEST_SERVICE_NAME, testService.getName());
        assertEquals(0.0f, testService.getProtectThreshold());
        assertEquals("none", testService.getSelector().getType());


        // Test UpdateService();
        service.setProtectThreshold(0.5f);
        assertTrue(client.updateService(service));

        testService = client.getService(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_SERVICE_NAME);
        assertEquals(0.5f, testService.getProtectThreshold());

        // Test DeleteService()
        assertTrue(client.deleteService(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_SERVICE_NAME));

        // Test getService() : not found
        OpenApiClientException failure = null;
        try {
            testService = client.getService(TEST_NAMESPACE_ID, TEST_SERVICE_NAME);
        } catch (OpenApiClientException e) {
            failure = e;
        }

        assertNotNull(failure);
//        assertEquals(INTERNAL_SERVER_ERROR, failure.getErrorCode());
        assertNotNull(failure.getErrorCode());
    }

    private Service createService() {
        Service service = new Service();
        service.setNamespaceId(TEST_NAMESPACE_ID);
        service.setGroupName(TEST_GROUP_NAME);
        service.setName(TEST_SERVICE_NAME);
        service.setProtectThreshold(0.0f);
        Selector selector = new Selector();
        selector.setType("none");
        service.setSelector(selector);
        Map<String, String> metadata = singletonMap("servce.name", TEST_SERVICE_NAME);
        service.setMetadata(metadata);
        return service;
    }
}
