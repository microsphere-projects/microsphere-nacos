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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * The utility class for JSON
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
public abstract class JsonUtils {

    private static final String SEPARATOR = ",";

    private static final String JSON_START = "{";

    private static final String JSON_END = "}";

    private static final String ARRAY_JSON_START = "[";

    private static final String ARRAY_JSON_END = "]";

    private static final String EMPTY_JSON = JSON_START + JSON_END;

    private static final String EMPTY_ARRAY_JSON = ARRAY_JSON_START + ARRAY_JSON_END;


    public static String toJSON(Collection<?> elements) {
        int size = elements == null ? 0 : elements.size();
        if (size < 1) {
            return EMPTY_ARRAY_JSON;
        }
        StringJoiner jsonBuilder = new StringJoiner(SEPARATOR, ARRAY_JSON_START, ARRAY_JSON_END);

        for (Object element : elements) {
            jsonBuilder.add(toJSON(element));
        }

        return jsonBuilder.toString();
    }

    public static String toJSON(Object object) {
        if (object instanceof Collection) {
            return toJSON((Collection<?>) object);
        } else if (object instanceof Map) {
            return toJSON((Map<String, String>) object);
        }
    }

    public static String toJSON(List<Map<String, String>> maps) {
        int size = maps == null ? 0 : maps.size();
        if (size < 1) {
            return "[]";
        }

        StringJoiner jsonBuilder = new StringJoiner(",", "[", "]");
        for (int i = 0; i < size; i++) {
            Map<String, String> map = maps.get(i);
            jsonBuilder.add(toJSON(map));
        }

        return jsonBuilder.toString();
    }

    /**
     * Create a new JSON String from the specified {@link Map}
     *
     * @param map {@link Map}
     * @return non-null
     */
    public static String toJSON(Map<?, ?> map) {
        int size = map == null ? 0 : map.size();
        if (size < 1) {
            return EMPTY_JSON;
        }

        StringBuilder jsonBuilder = new StringBuilder(32 * size);

        int cursor = 0;
        jsonBuilder.append("{");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            jsonBuilder.append('"')
                    .append(key).append('"').append(':').append('"').append(value).append('"');
            if (++cursor < size) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
