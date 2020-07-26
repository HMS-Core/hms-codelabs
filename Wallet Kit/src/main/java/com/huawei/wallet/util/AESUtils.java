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

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES-encryption utility class.
 *
 * @since 2019-12-12
 */
public class AESUtils {
    private static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    public AESUtils() {
    }

    /**
     * AES-GCM encryption.
     *
     * @param plainData the data to be encrypted.
     * @param secretKeyStr encryption secret key.
     * @param iv encryption random iv.
     * @return the encrypted string.
     */
    public static String encryptByGcm(String plainData, String secretKeyStr, byte[] iv) {
        try {
            byte[] secretKeyByte = secretKeyStr.getBytes(StandardCharsets.UTF_8);
            byte[] plainByte = plainData.getBytes(Charsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(secretKeyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", BOUNCY_CASTLE_PROVIDER);
            AlgorithmParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(1, secretKey, spec);
            byte[] fBytes = cipher.doFinal(plainByte);
            return new String(Hex.encodeHex(fBytes, false));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *Generate encryption random iv.
     *
     * @param size iv length
     * @return encryption the byte array.
     */
    public static byte[] getIvByte(int size) {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[size];
            sr.nextBytes(bytes);
            return bytes;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
