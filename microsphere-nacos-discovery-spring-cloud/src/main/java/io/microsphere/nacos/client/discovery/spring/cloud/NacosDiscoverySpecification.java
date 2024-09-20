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
package io.microsphere.nacos.client.discovery.spring.cloud;

import org.springframework.cloud.context.named.NamedContextFactory;

import java.util.Arrays;
import java.util.Objects;

/**
 * {@link NamedContextFactory.Specification} Class for Nacos Discovery
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since NamedContextFactory.Specification
 */
public class NacosDiscoverySpecification implements NamedContextFactory.Specification {

    private String name;

    private Class<?>[] configuration;

    NacosDiscoverySpecification() {
    }

    public NacosDiscoverySpecification(String name, Class<?>... configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?>[] getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Class<?>[] configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NacosDiscoverySpecification that = (NacosDiscoverySpecification) o;
        return Objects.equals(this.name, that.name) && Arrays.equals(this.configuration, that.configuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.configuration);
    }

    @Override
    public String toString() {
        return new StringBuilder("NacosDiscoverySpecification{").append("name='").append(this.name).append("', ")
                .append("configuration=").append(Arrays.toString(this.configuration)).append("}").toString();
    }
}
