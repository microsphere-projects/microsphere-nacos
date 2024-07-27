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
package io.microsphere.nacos.client.common.discovery.model;

import io.microsphere.nacos.client.common.model.Model;

/**
 * The {@link Model} {@link Class} for Nacos {@link Instance Instance's} Heartbeat
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class Heartbeat implements Model {

    private int code;

    private long clientBeatInterval;

    private boolean lightBeatEnabled;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getClientBeatInterval() {
        return clientBeatInterval;
    }

    public void setClientBeatInterval(long clientBeatInterval) {
        this.clientBeatInterval = clientBeatInterval;
    }

    public boolean isLightBeatEnabled() {
        return lightBeatEnabled;
    }

    public void setLightBeatEnabled(boolean lightBeatEnabled) {
        this.lightBeatEnabled = lightBeatEnabled;
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
                "code=" + code +
                ", clientBeatInterval=" + clientBeatInterval +
                ", lightBeatEnabled=" + lightBeatEnabled +
                '}';
    }
}
