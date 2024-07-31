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
package io.microsphere.nacos.client.v2.client.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.microsphere.nacos.client.io.GsonDeserializer;
import io.microsphere.nacos.client.v2.client.model.ClientInstance;

import java.lang.reflect.Type;

/**
 * The {@link GsonDeserializer} class for {@link ClientInstance}
 * <p>
 * The sample JSON data:
 * <pre>
 * {
 *  "namespace": "public",
 *  "group": "DEFAULT_GROUP",
 *  "serviceName": "nacos.test.1",
 *  "registeredInstance": {
 *      "ip": "10.128.164.35",
 *      "port": 9950,
 *      "cluster": "DEFAULT"
 *  }
 * }
 * </pre>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ClientInstance
 * @see GsonDeserializer
 * @since 1.0.0
 */
public class ClientInstanceDeserializer extends GsonDeserializer<ClientInstance> {

    protected static final String NAMESPACE_MEMBER_NAME = "namespace";

    protected static final String GROUP_MEMBER_NAME = "group";

    protected static final String SERVICE_NAME_MEMBER_NAME = "serviceName";

    protected static final String REGISTERED_INSTANCE_MEMBER_NAME = "registeredInstance";

    protected static final String REGISTERED_INSTANCE_IP_MEMBER_NAME = "ip";

    protected static final String REGISTERED_INSTANCE_PORT_MEMBER_NAME = "port";

    protected static final String REGISTERED_INSTANCE_CLUSTER_MEMBER_NAME = "cluster";

    @Override
    protected ClientInstance deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement registerInstance = jsonObject.get(REGISTERED_INSTANCE_MEMBER_NAME);

        if (registerInstance == null) { // No registeredInstance member found
            return super.deserialize(json, typeOfT);
        }

        JsonObject registerInstanceJsonObject = registerInstance.getAsJsonObject();

        String namespaceId = getString(jsonObject, NAMESPACE_MEMBER_NAME);
        String groupName = getString(jsonObject, GROUP_MEMBER_NAME);
        String serviceName = getString(jsonObject, SERVICE_NAME_MEMBER_NAME);
        String ip = getString(registerInstanceJsonObject, REGISTERED_INSTANCE_IP_MEMBER_NAME);
        int port = getInteger(registerInstanceJsonObject, REGISTERED_INSTANCE_PORT_MEMBER_NAME);
        String cluster = getString(registerInstanceJsonObject, REGISTERED_INSTANCE_CLUSTER_MEMBER_NAME);

        ClientInstance clientInstance = new ClientInstance();
        clientInstance.setNamespaceId(namespaceId);
        clientInstance.setGroupName(groupName);
        clientInstance.setClusterName(cluster);
        clientInstance.setServiceName(serviceName);
        clientInstance.setIp(ip);
        clientInstance.setPort(port);

        return clientInstance;
    }
}
