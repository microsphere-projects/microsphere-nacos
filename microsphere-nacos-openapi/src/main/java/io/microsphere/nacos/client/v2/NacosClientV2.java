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
package io.microsphere.nacos.client.v2;

import io.microsphere.nacos.client.Client;
import io.microsphere.nacos.client.common.auth.AuthenticationClient;
import io.microsphere.nacos.client.common.config.ConfigClient;
import io.microsphere.nacos.client.common.discovery.ConsistencyType;
import io.microsphere.nacos.client.common.discovery.InstanceClient;
import io.microsphere.nacos.client.common.discovery.ServiceClient;
import io.microsphere.nacos.client.common.discovery.model.Service;
import io.microsphere.nacos.client.common.namespace.NamespaceClient;
import io.microsphere.nacos.client.common.namespace.model.Namespace;
import io.microsphere.nacos.client.constants.Constants;
import io.microsphere.nacos.client.v1.NacosClient;
import io.microsphere.nacos.client.v2.client.model.ClientDetail;
import io.microsphere.nacos.client.v2.client.model.ClientInfo;
import io.microsphere.nacos.client.v2.client.model.ClientInstance;
import io.microsphere.nacos.client.v2.client.model.ClientSubscriber;

import java.util.List;

import static io.microsphere.nacos.client.constants.Constants.DEFAULT_GROUP_NAME;
import static io.microsphere.nacos.client.constants.Constants.DEFAULT_NAMESPACE_ID;

/**
 * Nacos Client for Open API V2
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see AuthenticationClient
 * @see ConfigClient
 * @see ServiceClient
 * @see InstanceClient
 * @see NamespaceClient
 * @see NacosClient
 * @since 1.0.0
 */
public interface NacosClientV2 extends AuthenticationClient, ConfigClient, ServiceClient, InstanceClient, NamespaceClient,
        Client {

    /**
     * Get all client ids
     *
     * @return non-null
     */
    List<String> getAllClientIds();

    /**
     * Get an instance of {@link ClientDetail} by the specified client id
     *
     * @param clientId the id of {@link ClientDetail}
     * @return an instance of {@link ClientDetail} if found
     */
    ClientDetail getClientDetail(String clientId);

    /**
     * Get the registered {@link List list} of {@link ClientInstance instances} from the specified client id
     *
     * @param clientId the id of {@link ClientDetail}
     * @return the non-null {@link List list} of {@link ClientInstance instances}
     */
    List<ClientInstance> getRegisteredInstances(String clientId);

    /**
     * Get the {@link List list} of {@link ClientSubscriber subscribers} from the specified client id
     *
     * @param clientId the id of {@link ClientDetail}
     * @return the non-null {@link List list} of {@link ClientInstance instances}
     */
    List<ClientSubscriber> getSubscribers(String clientId);

    /**
     * Get the registered {@link List list} of {@link ClientInfo} by the specified ephemeral service's name
     * under the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} and {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"}
     *
     * @param serviceName the name of {@link Service}.
     * @return non-null
     */
    default List<ClientInfo> getRegisteredClients(String serviceName) {
        return getRegisteredClients(DEFAULT_GROUP_NAME, serviceName);
    }

    /**
     * Get the registered {@link List list} of {@link ClientInfo} by the specified group name and ephemeral service's name
     * under the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     *
     * @param groupName   (optional) the name of group, if not specified, the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"} will be used.
     * @param serviceName the name of {@link Service}.
     * @return non-null
     */
    default List<ClientInfo> getRegisteredClients(String groupName, String serviceName) {
        return getRegisteredClients(DEFAULT_NAMESPACE_ID, groupName, serviceName);
    }

    /**
     * Get the registered {@link List list} of {@link ClientInfo} by the specified namespace id, group name and ephemeral service's name
     *
     * @param namespaceId (optional) {@link Namespace#getNamespaceId() the id of namespace}, if not specified,
     *                    the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param groupName   (optional) the name of group, if not specified, the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"} will be used.
     * @param serviceName the name of {@link Service}.
     * @return non-null
     */
    default List<ClientInfo> getRegisteredClients(String namespaceId, String groupName, String serviceName) {
        return getRegisteredClients(namespaceId, groupName, serviceName, ConsistencyType.EPHEMERAL);
    }

    /**
     * Get the registered {@link List list} of {@link ClientInfo} from the specified <code>namespaceId</code>, <code>groupName</code>,
     * <code>serviceName</code> and <code>ephemeral</code>
     *
     * @param namespaceId     (optional) {@link Namespace#getNamespaceId() the id of namespace}, if not specified,
     *                        the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param groupName       (optional) the name of group, if not specified, the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"} will be used.
     * @param serviceName     the name of {@link Service}.
     * @param consistencyType (optional) the type of consistency, if not specified, the {@link ConsistencyType#EPHEMERAL "ephemeral"} will be used.
     * @return non-null
     */
    default List<ClientInfo> getRegisteredClients(String namespaceId, String groupName, String serviceName, ConsistencyType consistencyType) {
        return getRegisteredClients(namespaceId, groupName, serviceName, consistencyType, null, null);
    }

    /**
     * Get the registered {@link List list} of {@link ClientInfo} from the specified <code>namespaceId</code>, <code>groupName</code>,
     * <code>serviceName</code>, <code>ephemeral</code>, <code>ip</code> and <code>port</code>
     *
     * @param namespaceId     (optional) {@link Namespace#getNamespaceId() the id of namespace}, if not specified,
     *                        the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param groupName       (optional) the name of group, if not specified, the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"} will be used.
     * @param serviceName     the name of {@link Service}.
     * @param consistencyType (optional) the type of consistency, if not specified, the {@link ConsistencyType#EPHEMERAL "ephemeral"} will be used.
     * @param ip              (optional) the IP of instance
     * @param port            (optional) the port of instance
     * @return non-null
     */
    List<ClientInfo> getRegisteredClients(String namespaceId, String groupName, String serviceName, ConsistencyType consistencyType, String ip, Integer port);

    /**
     * Get the subscribed {@link List list} of {@link ClientInfo} by the specified group name and ephemeral service's name
     * under the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace}
     *
     * @param groupName   (optional) the name of group, if not specified, the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"} will be used.
     * @param serviceName the name of {@link Service}.
     * @return non-null
     */
    default List<ClientInfo> getSubscribedClients(String groupName, String serviceName) {
        return getSubscribedClients(DEFAULT_NAMESPACE_ID, groupName, serviceName);
    }

    /**
     * Get the subscribed {@link List list} of {@link ClientInfo} by the specified namespace id, group name and ephemeral service's name
     *
     * @param namespaceId (optional) {@link Namespace#getNamespaceId() the id of namespace}, if not specified,
     *                    the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param groupName   (optional) the name of group, if not specified, the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"} will be used.
     * @param serviceName the name of {@link Service}.
     * @return non-null
     */
    default List<ClientInfo> getSubscribedClients(String namespaceId, String groupName, String serviceName) {
        return getSubscribedClients(namespaceId, groupName, serviceName, ConsistencyType.EPHEMERAL);
    }

    /**
     * Get the subscribed {@link List list} of {@link ClientInfo} from the specified <code>namespaceId</code>, <code>groupName</code>,
     * <code>serviceName</code> and <code>ephemeral</code>
     *
     * @param namespaceId     (optional) {@link Namespace#getNamespaceId() the id of namespace}, if not specified,
     *                        the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param groupName       (optional) the name of group, if not specified, the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"} will be used.
     * @param serviceName     the name of {@link Service}.
     * @param consistencyType (optional) the type of consistency, if not specified, the {@link ConsistencyType#EPHEMERAL "ephemeral"} will be used.
     * @return non-null
     */
    default List<ClientInfo> getSubscribedClients(String namespaceId, String groupName, String serviceName, ConsistencyType consistencyType) {
        return getSubscribedClients(namespaceId, groupName, serviceName, consistencyType, null, null);
    }

    /**
     * Get the subscribed {@link List list} of {@link ClientInfo} from the specified <code>namespaceId</code>, <code>groupName</code>,
     * <code>serviceName</code>, <code>ephemeral</code>, <code>ip</code> and <code>port</code>
     *
     * @param namespaceId     (optional) {@link Namespace#getNamespaceId() the id of namespace}, if not specified,
     *                        the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param groupName       (optional) the name of group, if not specified, the {@link Constants#DEFAULT_GROUP_NAME "DEFAULT_GROUP"} will be used.
     * @param serviceName     the name of {@link Service}.
     * @param consistencyType (optional) the type of consistency, if not specified, the {@link ConsistencyType#EPHEMERAL "ephemeral"} will be used.
     * @param ip              (optional) the IP of instance
     * @param port            (optional) the port of instance
     * @return non-null
     */
    List<ClientInfo> getSubscribedClients(String namespaceId, String groupName, String serviceName, ConsistencyType consistencyType, String ip, Integer port);
}
