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
package io.microsphere.nacos.client.common.namespace.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.microsphere.nacos.client.common.namespace.NamespaceClient;
import io.microsphere.nacos.client.common.namespace.model.Namespace;
import io.microsphere.nacos.client.io.GsonDeserializer;

import java.lang.reflect.Type;

/**
 * The {@link GsonDeserializer} class for {@link Namespace}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Namespace
 * @see NamespaceClient#getNamespace(String)
 * @see GsonDeserializer
 * @since 1.0.0
 */
public class NamespaceDeserializer extends GsonDeserializer<Namespace> {

    private static final String NAMESPACE_ID_MEMBER_NAME = "namespace";

    private static final String NAMESPACE_NAME_MEMBER_NAME = "namespaceShowName";

    private static final String NAMESPACE_DESC_MEMBER_NAME = "namespaceDesc";

    private static final String QUOTA_MEMBER_NAME = "quota";

    private static final String CONFIG_COUNT_MEMBER_NAME = "configCount";

    private static final String TYPE_MEMBER_NAME = "type";

    @Override
    protected Namespace deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String namespaceId = getString(jsonObject, NAMESPACE_ID_MEMBER_NAME);
        String namespaceName = getString(jsonObject, NAMESPACE_NAME_MEMBER_NAME);
        String namespaceDesc = getString(jsonObject, NAMESPACE_DESC_MEMBER_NAME);
        Integer quota = getInteger(jsonObject, QUOTA_MEMBER_NAME);
        Integer configCount = getInteger(jsonObject, CONFIG_COUNT_MEMBER_NAME);
        Integer type = getInteger(jsonObject, TYPE_MEMBER_NAME);
        Namespace namespace = new Namespace();
        namespace.setNamespaceId(namespaceId);
        namespace.setNamespaceName(namespaceName);
        namespace.setNamespaceDesc(namespaceDesc);
        namespace.setQuota(quota);
        namespace.setConfigCount(configCount);
        namespace.setType(type);
        return namespace;
    }
}
