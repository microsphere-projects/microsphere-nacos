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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The utility class for {@link Type}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Type
 * @since 1.0.0
 */
public abstract class TypeUtils {

    /**
     * Create an instance of {@link ParameterizedTypeImpl}
     *
     * @param rawType             the raw type
     * @param actualTypeArguments the actual type arguments
     * @return non-null
     */
    public static ParameterizedType ofParameterizedType(Type rawType, Type... actualTypeArguments) {
        return new ParameterizedTypeImpl(rawType, actualTypeArguments);
    }

    static class ParameterizedTypeImpl implements ParameterizedType {

        private final Type rawType;

        private final Type ownerType;

        private final Type[] actualTypeArguments;

        public ParameterizedTypeImpl(Type rawType, Type... actualTypeArguments) {
            this(rawType, rawType, actualTypeArguments);
        }

        public ParameterizedTypeImpl(Type rawType, Type ownerType, Type... actualTypeArguments) {
            this.rawType = rawType;
            this.ownerType = ownerType;
            this.actualTypeArguments = actualTypeArguments;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return actualTypeArguments;
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }
    }
}
