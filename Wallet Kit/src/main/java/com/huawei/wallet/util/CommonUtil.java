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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

/**
 * Common utility class.
 *
 * @since 2019-07-29
 */
public abstract class CommonUtil {
    /**
     * ObjectMapper define
     */
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Check if a string is null or empty.
     *
     * @param str the string.
     * @return if the string is null or empty.
     */
    public static boolean isNull(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * Check if an object is null.
     *
     * @param object the object.
     * @return if the object is null.
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Check if a list is null or empty.
     *
     * @param list the list.
     * @return if the list is null or empty.
     */
    public static boolean isNull(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Get a file's absolute path.
     *
     * @param file the file path.
     * @return the absolute path.
     */
    public static String getAbsolutePath(String file) {
        URL url = CommonUtil.class.getResource("/" + file);

        if (null != url) {
            return url.getPath();
        } else {
            return "";
        }
    }

    /**
     * Transfer an object to a JSON string.
     *
     * @param obj the object.
     * @return the JSON string.
     */
    public static String toJson(Object obj) {
        return toJson(obj, JsonInclude.Include.NON_NULL);
    }

    /**
     * Transfer an object to a JSON string.
     *
     * @param obj the object.
     * @param inclusion JSON inclusion rule.
     * @return the JSON string.
     */
    public static String toJson(Object obj, JsonInclude.Include inclusion) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }

        MAPPER.setSerializationInclusion(inclusion);

        try {
            return MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Read a JSON file and return its content.
     *
     * @param filename JSON filename.
     * @return the JSON string.
     */
    public static String readJSONFile(String filename) {
        String filePath = CommonUtil.getAbsolutePath("hmspass/" + filename);
        File walletObjectFile = new File(filePath);
        InputStream inputStream = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        StringBuffer sb = new StringBuffer();
        try {
            inputStream = new FileInputStream(walletObjectFile);
            reader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(reader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found. Filename: " + filePath);
        } catch (IOException e) {
            System.out.println("Read file failed. FileName: " + filePath);
        } finally {
            try {
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Read file failed. FileName: " + filePath);
            }
        }
        return sb.toString();
    }
}
