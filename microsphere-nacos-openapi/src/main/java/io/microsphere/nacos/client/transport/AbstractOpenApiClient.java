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
package io.microsphere.nacos.client.transport;

import io.microsphere.nacos.client.ErrorCode;
import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.common.model.Result;
import io.microsphere.nacos.client.io.DeserializationException;
import io.microsphere.nacos.client.io.Deserializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static io.microsphere.nacos.client.ErrorCode.CLIENT_ERROR;
import static io.microsphere.nacos.client.ErrorCode.DESERIALIZATION_ERROR;
import static io.microsphere.nacos.client.transport.OpenApiRequest.Builder.from;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.ACCESS_TOKEN;
import static io.microsphere.nacos.client.util.IOUtils.readAsString;
import static io.microsphere.nacos.client.util.StringUtils.isBlank;
import static java.lang.String.format;

/**
 * The Abstract {@link OpenApiClient}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiClient
 * @since 1.0.0
 */
public abstract class AbstractOpenApiClient implements OpenApiClient {

    private final NacosClientConfig nacosClientConfig;

    public AbstractOpenApiClient(NacosClientConfig nacosClientConfig) {
        this.nacosClientConfig = nacosClientConfig;
    }

    @Override
    public final OpenApiResponse execute(OpenApiRequest request) throws OpenApiClientException {
        String accessToken = getAccessToken();
        OpenApiRequest openApiRequest = accessToken == null ? request :
                from(request).queryParameter(ACCESS_TOKEN, accessToken).build();
        return doExecute(openApiRequest);
    }

    @Override
    public <T> T execute(OpenApiRequest request, Type payloadType) throws OpenApiClientException {
        Deserializer deserializer = getDeserializer();
        T payload = null;
        int code = 0;
        String errorMessge = null;
        try {
            OpenApiResponse response = execute(request);
            int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                if (payloadType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) payloadType;
                    Type rawType = parameterizedType.getRawType();
                    if (Result.class.equals(rawType)) {
                        Result result = deserializer.deserialize(response.getContent(), payloadType);
                        if (result.isSuccess()) {
                            payload = (T) result.getData();
                        }
                    }
                } else {
                    payload = deserializer.deserialize(response.getContent(), payloadType);
                }

                return payload;
            }

            String statusMessage = response.getStatusMessage();
            code = statusCode;
            errorMessge = isBlank(statusMessage) ? readAsString(response.getContent(), getEncoding()) : statusMessage;
        } catch (DeserializationException e) {
            String errorMessage = format("The payload[%s] can't be deserialized", payloadType);
            throw new OpenApiClientException(DESERIALIZATION_ERROR, errorMessage, e);
        } catch (Throwable e) {
            throw new OpenApiClientException(CLIENT_ERROR, e.getMessage(), e);
        }

        ErrorCode errorCode = ErrorCode.valueOf(code);
        errorMessge = isBlank(errorMessge) ? errorCode.getMessage() : errorMessge;
        String errorMessage = format("The Open API request[%s] is invalid , response status[code : %d , message : %s]",
                request, code, errorMessge);
        throw new OpenApiClientException(errorCode, errorMessage);
    }

    /**
     * Execute the {@link OpenApiRequest}
     *
     * @param request the {@link OpenApiRequest}
     * @return the {@link OpenApiResponse}
     * @throws OpenApiClientException
     */
    protected abstract OpenApiResponse doExecute(OpenApiRequest request) throws OpenApiClientException;

    /**
     * Get the access token
     *
     * @return null if not exists
     */
    protected abstract String getAccessToken();

    public final NacosClientConfig getNacosClientConfig() {
        return this.nacosClientConfig;
    }

    @Override
    public String getEncoding() {
        return this.nacosClientConfig.getEncoding();
    }
}
