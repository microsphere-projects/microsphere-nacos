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

import io.microsphere.nacos.client.util.ErrorCodeControl;

import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * The Enumeration for Nacos OpenAPI Error-Code
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
public enum ErrorCode {

    // Nacos 1.x Errors

    BAD_REQUEST(400, "Bad Request"),

    FORBIDDEN(403, "Forbidden"),

    NOT_FOUND(404, "Not Found"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // Nacos 2.x Errors (10000 - 30000)

    PARAMETER_MISSING(10000, "Parameter Missing"),

    ACCESS_DENIED(10001, "Access Denied"),

    DATA_ACCESS_ERROR(10002, "Data Access Error"),

    TENANT_PARAMETER_ERROR(20001, "'tenant' Parameter Error"),

    PARAMETER_VALIDATE_ERROR(20002, "Parameter Validate Error"),

    MEDIA_TYPE_ERROR(20003, "MediaType Error"),

    RESOURCE_NOT_FOUND(20004, "Resource Not Found"),

    RESOURCE_CONFLICT(20005, "Resource Conflict"),

    CONFIG_LISTENER_IS_NULL(20006, "Config Listener is NULL"),

    CONFIG_LISTENER_ERROR(20007, "Config Listener Error"),

    INVALID_DATA_ID(20008, "Invalid Data Id"),

    PARAMETER_MISMATCH(20009, "Parameter Mismatch"),

    SERVICE_NAME_ERROR(21000, "Service Name Error"),

    WEIGHT_ERROR(21001, "Weight Error"),

    INSTANCE_METADATA_ERROR(21002, "Instance Metadata Error"),

    INSTANCE_NOT_FOUND(21003, "Instance not found"),

    INSTANCE_ERROR(21004, "Instance error"),

    SERVICE_METADATA_ERROR(21005, "Service Metadata Error"),

    SELECTOR_ERROR(21006, "Selector Error"),

    SERVICE_ALREADY_EXIST(21007, "Service Already Exist"),

    SERVICE_NOT_EXIST(21008, "Service Not Exist"),

    SERVICE_DELETE_FAILURE(21009, "Service Delete Failure"),

    HEALTHY_PARAM_MISS(21010, "Healthy Param Miss"),

    HEALTH_CHECK_STILL_RUNNING(21011, "Health Check Still Running"),

    ILLEGAL_NAMESPACE(22000, "Illegal Namespace"),

    NAMESPACE_NOT_EXIST(22001, "Namespace Not Exist"),

    NAMESPACE_ALREADY_EXIST(22002, "Namespace Already Exist"),

    ILLEGAL_STATE(23000, "Illegal State"),

    NODE_INFO_ERROR(23001, "Node Info Error"),

    NODE_DOWN_FAILURE(23002, "Node Down Failure"),

    SERVER_ERROR(30000, "Server Error"),

    // Nacos Client Errors (40000 - 49999)

    CLIENT_ERROR(40000, "Client Error"),

    IO_ERROR(40001, "I/O Error"),

    SERIALIZATION_ERROR(40002, "Serialization Error"),

    DESERIALIZATION_ERROR(40003, "Deserialization Error"),

    ;


    private static final String ERROR_CODE_MESSAGES_BASE_NAME = "META-INF/error-code/messages";

    private final int value;

    private final String reason;

    ErrorCode(int value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    /**
     * @return The value of {@link ErrorCode}
     */
    public int getValue() {
        return value;
    }

    /**
     * @return The description of {@link ErrorCode}
     */
    public String getReason() {
        return reason;
    }

    /**
     * Get the message for {@link ErrorCode} with {@link Locale#getDefault()}
     *
     * @return the message for {@link ErrorCode}
     * @see ResourceBundle
     * @see ErrorCodeResourceBundleControlProvider
     */
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    /**
     * Get the message for {@link ErrorCode}
     *
     * @param locale {@link Locale}
     * @return the message for {@link ErrorCode}
     * @see ResourceBundle
     * @see ErrorCodeResourceBundleControlProvider
     */
    public String getMessage(Locale locale) {
        ResourceBundle resourceBundle = getBundle(ERROR_CODE_MESSAGES_BASE_NAME, locale, ErrorCodeControl.INSTANCE);
        String key = getMessageKey();
        return resourceBundle.getString(key);
    }

    public static ErrorCode valueOf(int value) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.value == value) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("Invalid ErrorCode value : " + value);
    }

    private String getMessageKey() {
        return String.valueOf(this.value);
    }
}
