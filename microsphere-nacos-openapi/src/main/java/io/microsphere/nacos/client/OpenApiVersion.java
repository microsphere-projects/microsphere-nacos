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

/**
 * The enumeration of OpenAPI Versions
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Enum
 * @since 1.0.0
 */
public enum OpenApiVersion {

    V1("/v1"),

    V2("/v2");

    private final String endpointPath;

    OpenApiVersion(String endpointPath) {
        this.endpointPath = endpointPath;
    }

    /**
     * Gets the endpoint path
     *
     * @return non-null
     */
    public String getEndpointPath() {
        return endpointPath;
    }

    public static OpenApiVersion getVersion(String endpoint) {
        for (OpenApiVersion version : values()) {
            if (endpoint.startsWith(version.endpointPath)) {
                return version;
            }
        }
        return null;
    }
}
