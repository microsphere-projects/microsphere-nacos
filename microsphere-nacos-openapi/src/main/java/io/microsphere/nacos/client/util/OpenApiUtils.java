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
package io.microsphere.nacos.client.util;

import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.transport.OpenApiResponse;

/**
 * The utils for OpenAPI
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiClient
 * @see OpenApiRequest
 * @see OpenApiResponse
 * @since 1.0.0
 */
public abstract class OpenApiUtils {

    /**
     * The response message of OK
     */
    public static final String RESPONSE_MESSAGE_OK = "ok";


    /**
     * Check whether the response message is OK
     *
     * @param openApiClient {@link OpenApiClient}
     * @param request       {@link OpenApiRequest}
     * @return <code>true</code> if OK, otherwise <code>false</code>
     */
    public static boolean executeOkResponse(OpenApiClient openApiClient, OpenApiRequest request) {
        String message = openApiClient.execute(request, String.class);
        return RESPONSE_MESSAGE_OK.equals(message);
    }

    /**
     * Check whether the response message is OK
     *
     * @param openApiClient {@link OpenApiClient}
     * @param request       {@link OpenApiRequest}
     * @return <code>true</code> if OK, otherwise <code>false</code>
     */
    public static boolean executeAsResultOkResponse(OpenApiClient openApiClient, OpenApiRequest request) {
        String message = openApiClient.executeAsResult(request, String.class);
        return RESPONSE_MESSAGE_OK.equals(message);
    }
}
