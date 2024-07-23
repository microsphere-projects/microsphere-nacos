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

import io.microsphere.nacos.client.ErrorCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * {@link ResourceBundle.Control} for {@link ErrorCode}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ResourceBundle.Control
 * @see ErrorCode
 * @since 1.0.0
 */
public class ErrorCodeControl extends ResourceBundle.Control {

    public static final ResourceBundle.Control INSTANCE = new ErrorCodeControl();

    @Override
    public List<String> getFormats(String baseName) {
        return FORMAT_PROPERTIES;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader classLoader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");

        ResourceBundle bundle = null;

        try (InputStream stream = getInputStream(resourceName, classLoader, reload);
             Reader reader = new InputStreamReader(stream, "UTF-8");
        ) {
            bundle = new PropertyResourceBundle(reader);
        } catch (IOException e) {
            throw e;
        }
        return bundle;
    }

    private InputStream getInputStream(String resourceName, ClassLoader classLoader, boolean reload) throws IOException {
        InputStream is = null;
        if (reload) {
            URL url = classLoader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    // Disable caches to get fresh data for
                    // reloading.
                    connection.setUseCaches(false);
                    is = connection.getInputStream();
                }
            }
        } else {
            is = classLoader.getResourceAsStream(resourceName);
        }
        return is;
    }
}
