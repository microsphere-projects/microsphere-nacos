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
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@link OpenApiNacosClientV2} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiNacosClientV2
 * @since 1.0.0
 */
public class OpenApiNacosClientV2Test extends OpenApiTest {

    private NacosClientV2 nacosClientV2;

    @Override
    protected void setup() {
        this.nacosClientV2 = new OpenApiNacosClientV2(this.openApiClient, this.nacosClientConfig);
    }

    @Test
    public void test() {

        // Test getAllClientIds();
        List<String> allClientIds = nacosClientV2.getAllClientIds();
        assertNotNull(allClientIds);
    }
}
