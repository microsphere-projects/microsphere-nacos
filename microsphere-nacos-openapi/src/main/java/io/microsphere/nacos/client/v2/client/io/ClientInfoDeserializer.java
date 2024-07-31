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
import io.microsphere.nacos.client.v2.client.model.ClientInfo;

import java.lang.reflect.Type;

/**
 * The {@link GsonDeserializer} {@link Class} for {@link ClientInfo}
 * The sample JSON data :
 * <pre>
 * {
 * "clientId": "1664527125645_127.0.0.1_4443",
 * "ip": "10.128.164.35",
 * "port": 0
 * }
 * </pre>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ClientInfo
 * @see GsonDeserializer
 * @since 1.0.0
 */
public class ClientInfoDeserializer extends GsonDeserializer<ClientInfo> {

    protected static final String CLIENT_ID_MEMBER_NAME = "clientId";

    protected static final String IP_MEMBER_NAME = "ip";

    protected static final String PORT_MEMBER_NAME = "port";

    @Override
    protected ClientInfo deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String clientId = getString(jsonObject, CLIENT_ID_MEMBER_NAME);
        String ip = getString(jsonObject, IP_MEMBER_NAME);
        int port = getInteger(jsonObject, PORT_MEMBER_NAME);
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClientId(clientId);
        clientInfo.setClientIp(ip);
        clientInfo.setClientPort(port);
        return clientInfo;
    }
}
