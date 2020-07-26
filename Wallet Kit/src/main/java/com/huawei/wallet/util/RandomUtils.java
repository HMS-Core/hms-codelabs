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

import org.bouncycastle.util.encoders.Hex;

/**
 * Methods to generate random bytes or random Strings.
 *
 * @since 2019-12-12
 */
public class RandomUtils {
    private RandomUtils() {
    }

    /**
     * Generate random byte.
     *
     * @param byteSize byte length.
     * @return the generated random bytes.
     */
    public static byte[] generateSecureRandomByte(int byteSize) {
        return CommCryptUtil.genSecureRandomByte(byteSize);
    }

    /**
     * Generate random hex string.
     *
     * @param size string length.
     * @return the generated random hex string.
     */
    public static String generateSecureRandomFactor(int size) {
        byte[] factor = generateSecureRandomByte(size);
        return Hex.toHexString(factor);
    }
}
