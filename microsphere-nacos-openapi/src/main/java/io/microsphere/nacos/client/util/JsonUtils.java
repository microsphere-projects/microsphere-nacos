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

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static io.microsphere.nacos.client.util.CollectionUtils.size;
import static java.beans.Introspector.decapitalize;
import static java.beans.Introspector.getBeanInfo;
import static java.util.Arrays.asList;

/**
 * The utility class for JSON
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
public abstract class JsonUtils {

    private static final String QUOTE = "\"";

    private static final String COLON = ":";

    private static final String SEPARATOR = ",";

    private static final String JSON_START = "{";

    private static final String JSON_END = "}";

    private static final String ARRAY_JSON_START = "[";

    private static final String ARRAY_JSON_END = "]";

    private static final String EMPTY_JSON = JSON_START + JSON_END;

    private static final String EMPTY_ARRAY_JSON = ARRAY_JSON_START + ARRAY_JSON_END;

    public static String toJSON(Object[] elements) {
        int size = elements == null ? 0 : elements.length;
        if (size < 1) {
            return EMPTY_ARRAY_JSON;
        }
        return toJSON(asList(elements));
    }

    public static String toJSON(Collection<?> elements) {
        int size = size(elements);
        if (size < 1) {
            return EMPTY_ARRAY_JSON;
        }
        return toJSON((Iterable<?>) elements);
    }

    public static String toJSON(Iterable<?> elements) {
        if (elements == null) {
            return EMPTY_ARRAY_JSON;
        }

        StringJoiner jsonBuilder = new StringJoiner(SEPARATOR, ARRAY_JSON_START, ARRAY_JSON_END);

        for (Object element : elements) {
            jsonBuilder.add(String.valueOf(toValue(element)));
        }

        return jsonBuilder.toString();
    }

    public static String toJSON(Object object) {
        if (object instanceof Collection) {
            return toJSON((Collection<?>) object);
        } else if (object instanceof Map) {
            return toJSON((Map<String, String>) object);
        }
        String json = null;
        try {
            BeanInfo beanInfo = getBeanInfo(object.getClass(), Object.class);
            json = toJSON(object, beanInfo);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    static String toJSON(Object object, BeanInfo beanInfo) throws Throwable {
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        int size = propertyDescriptors == null ? 0 : propertyDescriptors.length;
        if (size < 1) {
            return EMPTY_JSON;
        }
        Map<String, Object> fieldsMap = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
            Method method = propertyDescriptor.getReadMethod();
            if (method != null) {
                String key = decapitalize(propertyDescriptor.getName());
                Object value = method.invoke(object);
                fieldsMap.put(key, value);
            }
        }
        return toJSON(fieldsMap);
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
        jsonBuilder.append(JSON_START);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            jsonBuilder.append(toEntry(key, value));
            if (++cursor < size) {
                jsonBuilder.append(SEPARATOR);
            }
        }

        jsonBuilder.append(JSON_END);
        return jsonBuilder.toString();
    }

    static String toEntry(Object key, Object value) {
        return toKey(key) + COLON + toValue(value);
    }

    static String toKey(Object key) {
        return QUOTE + key + QUOTE;
    }

    static Object toValue(Object value) {
        if (value instanceof CharSequence) {
            return QUOTE + value + QUOTE;
        } else if (value instanceof Number) {
            return value;
        } else if (value instanceof Boolean) {
            return value;
        } else if (value instanceof Collection) {
            return toJSON((Collection<?>) value);
        } else if (value instanceof Map) {
            return toJSON((Map<String, String>) value);
        }
        return toJSON(value);
    }
}
