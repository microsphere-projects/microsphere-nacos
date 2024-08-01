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
package io.microsphere.nacos.client;

import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiHttpClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.TimeUnit;

/**
 * Abstract Test class for Open API
 * The sub-class must
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiHttpClient
 * @since 1.0.0
 */
public abstract class OpenApiTest {

    protected static final String TEST_NAMESPACE_ID = "test";

    protected static final String TEST_GROUP_NAME = "test-group";

    protected static final String SERVER_ADDRESS_PROPERTY_NAME = "SERVER_ADDRESS";

    protected static final String SERVER_ADDRESS = System.getenv(SERVER_ADDRESS_PROPERTY_NAME);

    protected static final String USER_NAME = "nacos";

    protected static final String PASSWORD = "nacos";

    protected OpenApiClient openApiClient;

    protected NacosClientConfig nacosClientConfig;

    protected static GenericContainer nacosServer;

    @BeforeAll
    public static void beforeAll() {
        if (SERVER_ADDRESS == null) {
            nacosServer = new GenericContainer(DockerImageName.parse("nacos/nacos-server:latest"))
                    .withEnv("MODE", "standalone")
                    .withExposedPorts(8848);
            nacosServer.start();
        }
    }

    @AfterAll
    public static void afterAll() {
        if (nacosServer != null) {
            nacosServer.stop();
        }
    }

    protected static String getServerAddress() {
        if (nacosServer == null) {
            return SERVER_ADDRESS;
        } else {
            return nacosServer.getHost() + ":" + nacosServer.getFirstMappedPort();
        }
    }

    @BeforeEach
    public void init() {
        NacosClientConfig config = new NacosClientConfig();
        config.setServerAddress(getServerAddress());
        customize(config);
        this.openApiClient = new OpenApiHttpClient(config);
        this.nacosClientConfig = config;

        setup();
    }

    /**
     * Customize the {@link NacosClientConfig}
     *
     * @param nacosClientConfig {@link NacosClientConfig}
     */
    protected void customize(NacosClientConfig nacosClientConfig) {
    }

    protected void setup() {
    }

    protected void await(long waitTimeInSeconds) {
        long waitTime = TimeUnit.SECONDS.toMillis(waitTimeInSeconds);
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void destroy() throws Exception {
        if (openApiClient != null) {
            openApiClient.close();
        }
    }
}
