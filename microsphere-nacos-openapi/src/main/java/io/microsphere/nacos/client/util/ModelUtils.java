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

import io.microsphere.nacos.client.common.discovery.model.BaseInstance;
import io.microsphere.nacos.client.common.discovery.model.Instance;
import io.microsphere.nacos.client.common.model.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.microsphere.nacos.client.constants.Constants.GROUP_SERVICE_NAME_SEPARATOR;

/**
 * The Utilities for Nacos {@link Model}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public abstract class ModelUtils {

    /**
     * Set the property if absent
     *
     * @param newValue the new newValue
     * @param getter   the getter
     * @param setter   the setter
     * @param <V>      the type of newValue
     * @return {@code true} if the newValue is set
     */
    public static <V> boolean setPropertyIfAbsent(V newValue, Supplier<V> getter, Consumer<V> setter) {
        boolean set = false;
        V oldValue = getter.get();
        if (oldValue == null || !Objects.equals(oldValue, newValue)) {
            setter.accept(newValue);
            set = true;
        }
        return set;
    }

    /**
     * @param instances
     * @param namespaceId
     * @param groupName
     * @param serviceName
     */
    public static void completeInstances(Iterable<Instance> instances, String namespaceId, String groupName, String serviceName) {
        for (Instance instance : instances) {
            completeInstance(instance, namespaceId, groupName, serviceName);
        }
    }

    /**
     * @param instance
     * @param namespaceId
     * @param groupName
     * @param serviceName
     */
    public static void completeInstance(Instance instance, String namespaceId, String groupName, String serviceName) {
        setPropertyIfAbsent(namespaceId, instance::getNamespaceId, instance::setNamespaceId);
        setPropertyIfAbsent(groupName, instance::getGroupName, instance::setGroupName);
        setPropertyIfAbsent(serviceName, instance::getServiceName, instance::setServiceName);

        resolveInstanceProperties(instance);
    }


    /**
     * Resolve the {@link BaseInstance} properties
     *
     * @param instance {@link BaseInstance}
     */
    public static void resolveInstanceProperties(BaseInstance instance) {
        String serviceName = instance.getServiceName();
        resolveInstanceProperties(instance, serviceName);
    }

    /**
     * Resolve the {@link BaseInstance} properties
     *
     * @param instance {@link BaseInstance}
     */
    public static void resolveInstanceProperties(Instance instance) {
        String serviceName = instance.getService();
        if (serviceName == null) {
            serviceName = instance.getServiceName();
        }
        resolveInstanceProperties(instance, serviceName);
    }

    public static String buildServiceName(String groupName, String serviceName) {
        return groupName == null ? serviceName : groupName + GROUP_SERVICE_NAME_SEPARATOR + serviceName;
    }

    public static Map<Object, Object> getHeartbeatMap(Instance instance) {
        Map<Object, Object> heartbeanMap = new HashMap<>(8);
        Map<String, String> metadata = instance.getMetadata();
        heartbeanMap.put("ip", instance.getIp());
        heartbeanMap.put("port", instance.getPort());
        heartbeanMap.put("serviceName", instance.getServiceName());
        heartbeanMap.put("cluster", instance.getClusterName());
        heartbeanMap.put("weight", instance.getWeight());
        heartbeanMap.put("metadata", metadata);
        return heartbeanMap;
    }

    static void resolveInstanceProperties(BaseInstance instance, String serviceName) {
        if (serviceName != null) {
            int index = serviceName.indexOf(GROUP_SERVICE_NAME_SEPARATOR);
            if (index > -1) {
                String groupName = serviceName.substring(0, index);
                serviceName = serviceName.substring(index + 2);
                instance.setGroupName(groupName);
                instance.setServiceName(serviceName);
            }
        }
    }
}
