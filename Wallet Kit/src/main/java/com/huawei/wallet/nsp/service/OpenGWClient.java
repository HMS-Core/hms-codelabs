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

package com.huawei.wallet.nsp.service;

import com.huawei.wallet.hms.hmssdk.dto.TokenResponse;
import com.huawei.wallet.util.CommonUtil;
import com.huawei.wallet.util.ConfigHelper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of invoking gateway service.
 *
 * @since 2019-07-26
 */
public class OpenGWClient implements IOpenGWClient {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static String getToken(String clientId, String clientSecret) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);

        String tokenUrl = ConfigHelper.instants().getValue("gw.tokenUrl");

        // Send the http request and get response.
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, header);
        ResponseEntity<TokenResponse> exchange =
            REST_TEMPLATE.exchange(tokenUrl, HttpMethod.POST, entity, TokenResponse.class);

        TokenResponse response = exchange.getBody();

        // Return the token.
        if (CommonUtil.isNull(response)) {
            return null;
        } else {
            return response.getAccess_token();
        }
    }
}
