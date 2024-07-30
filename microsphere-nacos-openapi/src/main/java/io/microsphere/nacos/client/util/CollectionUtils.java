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

/**
 * The Utility class for {@link Collection}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Collection
 * @since 1.0.0
 */
public abstract class CollectionUtils {

    /**
     * Get the size of the {@link Collection}
     *
     * @param collection the {@link Collection}
     * @return the size of the {@link Collection}
     */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }
}
