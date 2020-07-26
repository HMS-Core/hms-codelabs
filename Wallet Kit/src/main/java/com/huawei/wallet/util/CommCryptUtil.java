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

import java.security.SecureRandom;

/**
 * Other utility methods.
 *
 * @since 2019-12-12
 */
public final class CommCryptUtil {
    public CommCryptUtil() {
    }

    /**
     * Generate random bytes.
     *
     * @param byteSize byte array length.
     * @return generated random byte array.
     */
    public static byte[] genSecureRandomByte(int byteSize) {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[byteSize];
        sr.nextBytes(bytes);
        return bytes;
    }
}
