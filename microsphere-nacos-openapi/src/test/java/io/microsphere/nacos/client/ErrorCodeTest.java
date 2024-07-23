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

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.microsphere.nacos.client.ErrorCode.BAD_REQUEST;
import static io.microsphere.nacos.client.ErrorCode.FORBIDDEN;
import static io.microsphere.nacos.client.ErrorCode.INTERNAL_SERVER_ERROR;
import static io.microsphere.nacos.client.ErrorCode.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@link ErrorCode} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ErrorCode
 * @since 1.0.0
 */
public class ErrorCodeTest {

    static {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void testGetValue() {
        assertErrorCodeValue(BAD_REQUEST, 400);
        assertErrorCodeValue(FORBIDDEN, 403);
        assertErrorCodeValue(NOT_FOUND, 404);
        assertErrorCodeValue(INTERNAL_SERVER_ERROR, 500);
    }

    @Test
    public void testGetReason() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            assertNotNull(errorCode.getReason());
        }
    }

    @Test
    public void testGetMessage() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            String message = errorCode.getMessage();
            String localedMessage = errorCode.getMessage(Locale.SIMPLIFIED_CHINESE);
            assertNotNull(message);
            assertNotNull(localedMessage);
            assertNotEquals(message, localedMessage);
        }
    }

    private void assertErrorCodeValue(ErrorCode errorCode, int value) {
        assertEquals(value, errorCode.getValue());
    }
}
