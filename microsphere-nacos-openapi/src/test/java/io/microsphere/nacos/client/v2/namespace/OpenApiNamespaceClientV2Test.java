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
package io.microsphere.nacos.client.v2.namespace;

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.common.namespace.model.Namespace;
import io.microsphere.nacos.client.common.namespace.NamespaceClient;
import io.microsphere.nacos.client.v1.namespace.OpenApiNamespaceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.microsphere.nacos.client.v1.namespace.OpenApiNamespaceClientTest.MODIFIED_NAMESPACE_DESC;
import static io.microsphere.nacos.client.v1.namespace.OpenApiNamespaceClientTest.MODIFIED_NAMESPACE_NAME;
import static io.microsphere.nacos.client.v1.namespace.OpenApiNamespaceClientTest.NAMESPACE_DESC;
import static io.microsphere.nacos.client.v1.namespace.OpenApiNamespaceClientTest.NAMESPACE_ID;
import static io.microsphere.nacos.client.v1.namespace.OpenApiNamespaceClientTest.NAMESPACE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiNamespaceClientV2} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see NamespaceClient
 * @see OpenApiNamespaceClient
 * @since 1.0.0
 */
public class OpenApiNamespaceClientV2Test extends OpenApiTest {

    private NamespaceClient client;

    @BeforeEach
    public void before() {
        this.client = new OpenApiNamespaceClientV2(this.openApiClient);
        client.deleteNamespace(NAMESPACE_ID);
    }

    @Test
    public void test() {
        NamespaceClient client = this.client;

        // Test getAllNamespace()
        List<Namespace> namespaces = client.getAllNamespaces();
        int namespacesSize = namespaces.size();
        assertNotNull(namespaces);
        assertTrue(namespacesSize > 0);
        Namespace namespace = namespaces.get(0);
        assertEquals("public", namespace.getNamespaceName());

        // Test getNamespace()
        namespace = client.getNamespace(NAMESPACE_ID);

        // Not found
        assertNull(namespace);

        // Test createNamespace()
        assertTrue(client.createNamespace(NAMESPACE_ID, NAMESPACE_NAME, NAMESPACE_DESC));
        namespaces = client.getAllNamespaces();
        namespace = namespaces.get(namespaces.size() - 1);
        assertEquals(NAMESPACE_ID, namespace.getNamespaceId());
        assertEquals(NAMESPACE_NAME, namespace.getNamespaceName());

        // Test updateNamespace()
        for (int i = 0; i < 5; i++) {
            assertTrue(client.updateNamespace(NAMESPACE_ID, MODIFIED_NAMESPACE_NAME, MODIFIED_NAMESPACE_DESC));
        }

        // Test deleteNamespace()
        assertTrue(client.deleteNamespace(NAMESPACE_ID));
    }
}
