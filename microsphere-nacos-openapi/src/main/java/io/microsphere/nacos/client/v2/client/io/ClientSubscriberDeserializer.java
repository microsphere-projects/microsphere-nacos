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
import io.microsphere.nacos.client.v2.client.model.ClientSubscriber;

import java.lang.reflect.Type;

/**
 * The {@link GsonDeserializer} class for {@link ClientSubscriber}
 * <p>
 * The sample JSON data:
 * <pre>
 * {
 *  "namespace": "public",
 *  "group": "DEFAULT_GROUP",
 *  "serviceName": "nacos.test.1",
 *  "subscriberInfo": {
 *      "app": "unknown",
 *      "agent": "Nacos-Java-Client:v2.1.0",
 *      "addr": "10.128.164.35"
 *  }
 * }
 * </pre>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ClientSubscriber
 * @see GsonDeserializer
 * @since 1.0.0
 */
public class ClientSubscriberDeserializer extends GsonDeserializer<ClientSubscriber> {

    protected static final String NAMESPACE_MEMBER_NAME = "namespace";

    protected static final String GROUP_MEMBER_NAME = "group";

    protected static final String SERVICE_NAME_MEMBER_NAME = "serviceName";

    protected static final String SUBSCRIBER_INFO_MEMBER_NAME = "subscriberInfo";

    protected static final String SUBSCRIBER_INFO_APP_MEMBER_NAME = "app";

    protected static final String SUBSCRIBER_INFO_AGENT_MEMBER_NAME = "agent";

    protected static final String SUBSCRIBER_INFO_ADDRESS_MEMBER_NAME = "addr";

    @Override
    protected ClientSubscriber deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement subscriberInfo = jsonObject.get(SUBSCRIBER_INFO_MEMBER_NAME);

        if (subscriberInfo == null) { // No registeredInstance member found
            return super.deserialize(json, typeOfT);
        }

        JsonObject subscriberInfoJsonObject = subscriberInfo.getAsJsonObject();

        String namespaceId = getString(jsonObject, NAMESPACE_MEMBER_NAME);
        String groupName = getString(jsonObject, GROUP_MEMBER_NAME);
        String serviceName = getString(jsonObject, SERVICE_NAME_MEMBER_NAME);
        String app = getString(subscriberInfoJsonObject, SUBSCRIBER_INFO_APP_MEMBER_NAME);
        String agent = getString(subscriberInfoJsonObject, SUBSCRIBER_INFO_AGENT_MEMBER_NAME);
        String address = getString(subscriberInfoJsonObject, SUBSCRIBER_INFO_ADDRESS_MEMBER_NAME);

        ClientSubscriber clientSubscriber = new ClientSubscriber();
        clientSubscriber.setNamespaceId(namespaceId);
        clientSubscriber.setGroupName(groupName);
        clientSubscriber.setServiceName(serviceName);
        clientSubscriber.setApplication(app);
        clientSubscriber.setAgent(agent);
        clientSubscriber.setAddress(address);

        return clientSubscriber;
    }
}
