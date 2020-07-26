/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.huawei.wallet.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Methods to load a configuration file.
 *
 * @since 2019-11-06
 */
public final class ConfigHelper {
    /**
     * Configuration filename.
     */
    private final static String SYSTEM_CONFIG = "release.config.properties";

    /**
     * Singleton pattern instance.
     */
    private static ConfigHelper config;

    private Map<String, String> params;

    private ConfigHelper() {
        load();
    }

    /**
     * Singleton pattern implementation.
     *
     * @return the singleton instance.
     */
    public static ConfigHelper instants() {
        if (null == config) {
            config = new ConfigHelper();
        }
        return config;
    }

    /*
     * Load a configuration file.
     */
    @SuppressWarnings({"unchecked"})
    private void load() {
        if (null == this.params) {
            this.params = new HashMap<String, String>();
        } else {
            this.params.clear();
        }
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(SYSTEM_CONFIG);
        try {
            properties.load(inputStream);
            Iterator it = properties.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (null != key && !key.toString().equals("")) {
                    String value = properties.getProperty(key.toString());
                    params.put(key.toString().trim(), value.trim());
                }
            }
        } catch (IOException e) {
            return;
        }
    }

    /**
     * Get a specific value in a configuration file by its key.
     *
     * @param key the key to the value.
     * @return the value string.
     */
    public String getValue(String key) {
        return this.params.get(key);
    }
}
