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

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static io.microsphere.nacos.client.OpenApiTest.SERVER_ADDRESS_PROPERTY_NAME;
import static io.microsphere.nacos.client.OpenApiTest.getServerAddress;
import static org.junit.platform.commons.util.StringUtils.isNotBlank;

/**
 * The Junit5 Extension of TestContainers for Open API
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see BeforeAllCallback
 * @see AfterAllCallback
 * @since 1.0.0
 */
public class OpenApiTestContainersExtension implements BeforeAllCallback, AfterAllCallback {

    protected GenericContainer nacosServer;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (isNotBlank(getServerAddress())) { // Use the external Nacos Server
            return;
        }
        String nacosServerImage = extensionContext.getTestClass().map(testClass -> {
            String className = testClass.getName();
            return className.contains(".v1.") ? "nacos/nacos-server:v1.4.7" : "nacos/nacos-server:latest";
        }).get();
        nacosServer = new GenericContainer(DockerImageName.parse(nacosServerImage))
                .withEnv("MODE", "standalone")
                .withEnv("NACOS_AUTH_TOKEN","SecretKey012345678901234567890123456789012345678901234567890123456789")
                .withExposedPorts(8848);
        nacosServer.start();
        String serverAddress = nacosServer.getHost() + ":" + nacosServer.getFirstMappedPort();
        System.setProperty(SERVER_ADDRESS_PROPERTY_NAME, serverAddress);
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (nacosServer != null) {
            nacosServer.stop();
        }
    }

}
