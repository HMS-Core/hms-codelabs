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

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * JWE utility class.
 *
 * @since 2020-03-02
 */
public class JweUtil {

    /**
     * Generate a JSON Web Encryption (JWE).
     *
     * @param jwePrivateKey private key used to sign JWE content.
     * @param payload information plain text. It can be a list of instance IDs or an instance.
     * @return return a map containing a content string and a signature string.
     */
    public static String generateJwe(String jwePrivateKey, String payload) {
        // Part 1: JWE Header.
        // This header contains information about encryption and compression algorithms. It's a constant String.
        System.out.println("Part 1:");
        JweHeader jweHeader = getHeader();
        String jweHeaderEncode = getEncodeHeader(jweHeader);
        System.out.println("Encoded header: " + jweHeaderEncode);

        // Part 2: JWE Encrypted Key
        // Generate a 16-byte session key to encrypt payload data. Then convert it to a Hex String.
        System.out.println("Part 2:");
        String sessionKey = RandomUtils.generateSecureRandomFactor(16);
        // Encrypt the session key Hex String with Huawei's fixed sessionKeyPublicKey using
        // RSA/NONE/OAEPwithSHA-256andMGF1Padding algorithm, and then do base64 encoding to it.
        System.out.println("sessionKey: " + sessionKey);
        String sessionKeyPublicKey =
            "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAgBJB4usbO33Xg5vhJqfHJsMZj44f7rxpjRuPhGy37bUBjSLXN+dS6HpxnZwSVJCtmiydjl3Inq3Mzu4SCGxfb9RIjqRRfHA7ab5p3JnJVQfTEHMHy8XcABl6EPYIJMh26kztPOKU2Mkn6yhRaCurhVUD3n9bD8omiNrR4rg442AJlNamA7vgKs65AoqBuU4NBkGHg0VWWpEHCUx/xyX6hIwqc1aD7P2f62ZHsKpNZBOek/riWhaVx3dTAa9ZS+Av3IGLOZiplhYIow9f8dlWyqs8nff9FZoJO03QhXLvOORT+lPAkW6gFzaoeMaGb40HakkZn3uvlAEKrKrtR0rZEok+N1hnboaAu8oaKK0rF1W6iNrXcFrO0rcrCsFTVF8qCa/1dFmIXwUd2M6cUzT9W0YkNyb6ZBbwEhjwBL4DNW4JfeF2Dzj0eZYlSuYV7e7e1e+XEO8lwPLAiy4bEFAWCaeuDVIhbIoBaU6xHNVQoyzct98gaOYxE4mVDqAUVmhfAgMBAAE=";
        String encryptedKeyEncode = getEncryptedKey(sessionKey, sessionKeyPublicKey);
        System.out.println("Encrypted sessionKey: " + encryptedKeyEncode);

        // Part 3: JWE IV
        // Generate a 12-byte iv. Then convert it to a Hex String, and then do base64 encoding to the Hex String.
        System.out.println("Part 3:");
        byte[] iv = AESUtils.getIvByte(12);
        String ivHexStr = new String(Hex.encodeHex(iv, false));
        String ivEncode = Base64.encodeBase64URLSafeString(ivHexStr.getBytes(StandardCharsets.UTF_8));
        System.out.println("Encoded iv: " + ivEncode);

        // Part 4: JWE Cipher Text
        // Encrypt the payload with sessionKey and iv using AES/GCM/NoPadding algorithm. Encode the cipher text into a
        // Hex String. Then do gzip compression and base64 encoding to the Hex String.
        System.out.println("Part 4:");
        System.out.println("payload: " + payload);
        String cipherTextEncode = getCipherText(payload, sessionKey, iv);
        System.out.println("Compressed and encoded cipher text: " + cipherTextEncode);

        // Part 5: JWE Signature
        // Use your own private key to sign the content with SHA256withRSA, then do base64 encoding to it.
        // HMS wallet server will use the public key you provided on AGC to verify signatures.
        System.out.println("Part 5:");
        String signature = getSignature(jwePrivateKey, sessionKey, payload, jweHeaderEncode, ivEncode);
        System.out.println("signature: " + signature + "\n");

        // Combine all five parts together to form a valid JWE.
        StringBuilder stringBuilder = new StringBuilder().append(jweHeaderEncode)
            .append(".")
            .append(encryptedKeyEncode)
            .append(".")
            .append(ivEncode)
            .append(".")
            .append(cipherTextEncode)
            .append(".")
            .append(signature);
        return stringBuilder.toString();
    }

    private static JweHeader getHeader() {
        JweHeader jweHeader = new JweHeader();
        jweHeader.setAlg("RSA-OAEP");
        jweHeader.setEnc("A128GCM");
        jweHeader.setKid("1");
        jweHeader.setZip("gzip");
        return jweHeader;
    }

    private static String getEncodeHeader(JweHeader jweHeader) {
        StringBuffer stringBuffer = new StringBuffer();
        String headerStr = stringBuffer.append("alg=")
            .append(jweHeader.getAlg())
            .append(", enc=")
            .append(jweHeader.getEnc())
            .append(", kid=")
            .append(jweHeader.getKid())
            .append(", zip=")
            .append(jweHeader.getZip())
            .toString();
        System.out.println("Header before encoding: " + headerStr);
        // Do base64 encoding.
        return Base64.encodeBase64URLSafeString(headerStr.getBytes(StandardCharsets.UTF_8));
    }

    private static String getEncryptedKey(String sessionKey, String sessionKeyPublicKey) {
        try {
            String encryptedSessionKey = RSA.encrypt(sessionKey.getBytes(StandardCharsets.UTF_8), sessionKeyPublicKey,
                "RSA/NONE/OAEPwithSHA-256andMGF1Padding");
            return Base64.encodeBase64URLSafeString(encryptedSessionKey.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("Encrypt session key failed.");
        }
        return "";
    }

    private static String getCipherText(String dataJson, String sessionKey, byte[] iv) {
        String payloadEncrypt = AESUtils.encryptByGcm(dataJson, sessionKey, iv);
        System.out.println("Encrypted payload Hex String: " + payloadEncrypt);
        byte[] payLoadEncryptCompressByte = compress(payloadEncrypt.getBytes(StandardCharsets.UTF_8));
        String cipherTextEncode = Base64.encodeBase64URLSafeString(payLoadEncryptCompressByte);
        return cipherTextEncode;
    }

    private static String getSignature(String jweSignPrivateKey, String sessionKey, String payLoadJson,
        String jweHeaderEncode, String ivEncode) {
        StringBuffer stringBuffer = new StringBuffer();
        String signContent = stringBuffer.append(jweHeaderEncode)
            .append(".")
            .append(sessionKey)
            .append(".")
            .append(ivEncode)
            .append(".")
            .append(payLoadJson)
            .toString();
        System.out.println("Content to be signed: " + signContent);
        return RSA.sign(signContent, jweSignPrivateKey);
    }

    /**
     * gzip Compress
     *
     * @param originalBytes Data to be compressed
     * @return Compressed data
     */
    public static byte[] compress(byte[] originalBytes) {
        if (originalBytes == null || originalBytes.length == 0) {
            return null;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(originalBytes);
            gzip.finish();
            return out.toByteArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
