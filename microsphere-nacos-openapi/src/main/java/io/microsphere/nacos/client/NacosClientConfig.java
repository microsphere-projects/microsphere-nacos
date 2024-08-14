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
package io.microsphere.nacos.client;

import java.io.Serializable;
import java.util.Objects;

import static io.microsphere.nacos.client.constants.Constants.APPLICATION_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONNECTION_TIMEOUT;
import static io.microsphere.nacos.client.constants.Constants.DEFAULT_FETCHING_CONFIG_THREAD_NAME;
import static io.microsphere.nacos.client.constants.Constants.DEFAULT_LISTENING_CONFIG_THREAD_NAME;
import static io.microsphere.nacos.client.constants.Constants.DEFAULT_PUBLISHING_CONFIG_EVENT_THREAD_NAME;
import static io.microsphere.nacos.client.constants.Constants.ENCODING;
import static io.microsphere.nacos.client.constants.Constants.EVENT_PROCESSING_TIMEOUT;
import static io.microsphere.nacos.client.constants.Constants.LONG_POLLING_TIMEOUT;
import static io.microsphere.nacos.client.constants.Constants.MAX_CONNECTIONS;
import static io.microsphere.nacos.client.constants.Constants.MAX_PER_ROUTE_CONNECTIONS;
import static io.microsphere.nacos.client.constants.Constants.READ_TIMEOUT;

/**
 * The Nacos Client Config
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
public class NacosClientConfig implements Serializable {

    private static final long serialVersionUID = 6094172977732048929L;

    /**
     * The name of Nacos Client Config
     */
    private String name;

    /**
     * The application name
     */
    private String applicationName = APPLICATION_NAME;

    /**
     * The Nacos naming server address
     */
    private String serverAddress;

    /**
     * The Nacos Open API service scheme
     */
    private String scheme = "http";

    /**
     * The Nacos Open API service context path
     */
    private String contextPath = "/nacos";

    /**
     * The Nacos authentication user name
     */
    private String userName;

    /**
     * The Nacos authentication password
     */
    private String password;

    /**
     * The access key for namespace.
     */
    private String accessKey;

    /**
     * The secret key for namespace.
     */
    private String secretKey;

    /**
     * The maximum number of connections for Nacos Client
     */
    private int maxConnections = MAX_CONNECTIONS;

    /**
     * The maximum number of connections per route for Nacos Client
     */
    private int maxPerRoute = MAX_PER_ROUTE_CONNECTIONS;

    /**
     * The connection timeout for Nacos Client
     */
    private int connectionTimeout = CONNECTION_TIMEOUT;

    /**
     * The read timeout for Nacos Client
     */
    private int readTimeout = READ_TIMEOUT;

    /**
     * The long polling timeout for Nacos Client
     */
    private int longPollingTimeout = LONG_POLLING_TIMEOUT;

    /**
     * The event processing timeout for Nacos Client
     */
    private int eventProcessingTimeout = EVENT_PROCESSING_TIMEOUT;

    /**
     * The thread name of config for fetching
     */
    private String fetchingConfigThreadName = DEFAULT_FETCHING_CONFIG_THREAD_NAME;

    /**
     * The thread name of config for listening
     */
    private String listenerConfigThreadName = DEFAULT_LISTENING_CONFIG_THREAD_NAME;

    /**
     * The thread name of config for publishing
     */
    private String publishingConfigEventThreadName = DEFAULT_PUBLISHING_CONFIG_EVENT_THREAD_NAME;

    /**
     * The encoding for Nacos Client
     */
    private String encoding = ENCODING;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getLongPollingTimeout() {
        return longPollingTimeout;
    }

    public void setLongPollingTimeout(int longPollingTimeout) {
        this.longPollingTimeout = longPollingTimeout;
    }

    public int getEventProcessingTimeout() {
        return eventProcessingTimeout;
    }

    public void setEventProcessingTimeout(int eventProcessingTimeout) {
        this.eventProcessingTimeout = eventProcessingTimeout;
    }

    public String getFetchingConfigThreadName() {
        return fetchingConfigThreadName;
    }

    public void setFetchingConfigThreadName(String fetchingConfigThreadName) {
        this.fetchingConfigThreadName = fetchingConfigThreadName;
    }

    public String getListenerConfigThreadName() {
        return listenerConfigThreadName;
    }

    public void setListenerConfigThreadName(String listenerConfigThreadName) {
        this.listenerConfigThreadName = listenerConfigThreadName;
    }

    public String getPublishingConfigEventThreadName() {
        return publishingConfigEventThreadName;
    }

    public void setPublishingConfigEventThreadName(String publishingConfigEventThreadName) {
        this.publishingConfigEventThreadName = publishingConfigEventThreadName;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String toString() {
        return "NacosClientConfig{" +
                "name='" + name + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", serverAddress='" + serverAddress + '\'' +
                ", scheme='" + scheme + '\'' +
                ", contextPath='" + contextPath + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", maxConnections=" + maxConnections +
                ", maxPerRoute=" + maxPerRoute +
                ", connectionTimeout=" + connectionTimeout +
                ", readTimeout=" + readTimeout +
                ", longPollingTimeout=" + longPollingTimeout +
                ", eventProcessingTimeout=" + eventProcessingTimeout +
                ", fetchingConfigThreadName='" + fetchingConfigThreadName + '\'' +
                ", listenerConfigThreadName='" + listenerConfigThreadName + '\'' +
                ", publishingConfigEventThreadName='" + publishingConfigEventThreadName + '\'' +
                ", encoding='" + encoding + '\'' +
                '}';
    }
}
