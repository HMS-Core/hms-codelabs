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

package com.huawei.wallet.hms.hmssdk.impl;

import com.alibaba.fastjson.JSONObject;
import com.huawei.wallet.hms.hmssdk.WalletBuildService;
import com.huawei.wallet.hms.hmssdk.dto.BatchGetHwWalletResult;
import com.huawei.wallet.hms.hmssdk.dto.HwWalletObject;
import com.huawei.wallet.hms.hmssdk.dto.PageInfo;
import com.huawei.wallet.nsp.service.OpenGWClient;
import com.huawei.wallet.util.ConfigHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of invoking wallet api gateway service.
 *
 * @since 2019-11-05
 */
public class WalletBuildServiceImpl implements WalletBuildService {
    /**
     * Parameter for appending tokens.
     */
    private static final String AUTHORIZATION_HEAD = "Bearer ";

    private static final RestTemplate REST_TEMPLATE;

    static {
        REST_TEMPLATE = new RestTemplate();
        // Use HttpComponentsClientHttpRequestFactory for http requests.
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(20000);
        requestFactory.setReadTimeout(20000);
        // Set request factory.
        REST_TEMPLATE.setRequestFactory(requestFactory);
    }

    @Override
    public HwWalletObject postHwWalletObjectToWalletServer(String urlSegment, String body) {
        // Construct the http header.
        HttpHeaders header = constructHttpHeaders();
        // Construct the http URL.
        String baseUrl = ConfigHelper.instants().getValue("walletServerBaseUrl");
        String walletServerUrl = baseUrl + urlSegment;

        // Send the http request and get response.
        HttpEntity<JSONObject> entity = new HttpEntity<>(JSONObject.parseObject(body), header);
        ResponseEntity<HwWalletObject> exchange =
            REST_TEMPLATE.exchange(walletServerUrl, HttpMethod.POST, entity, HwWalletObject.class);

        // Return the posted model or instance.
        return exchange.getBody();
    }

    @Override
    public HwWalletObject getHwWalletObjectById(String urlSegment, String id) {
        // Construct the http header.
        HttpHeaders header = constructHttpHeaders();
        // Construct the http URL.
        String baseUrl = ConfigHelper.instants().getValue("walletServerBaseUrl");
        String walletServerUrl = baseUrl + urlSegment + id;

        // Send the http request and get response.
        ResponseEntity<HwWalletObject> response =
            REST_TEMPLATE.exchange(walletServerUrl, HttpMethod.GET, new HttpEntity(header), HwWalletObject.class);

        // Return the model or instance with the corresponding ID.
        return response.getBody();
    }

    @Override
    public List<HwWalletObject> getModels(String urlSegment, Integer pageSize) {
        // Construct the http header.
        HttpHeaders header = constructHttpHeaders();
        // Construct the http URL.
        String baseUrl = ConfigHelper.instants().getValue("walletServerBaseUrl");
        String walletServerUrl = baseUrl + urlSegment;

        HttpEntity entity = new HttpEntity(header);
        if (pageSize == null) {
            ResponseEntity<BatchGetHwWalletResult> response =
                REST_TEMPLATE.exchange(walletServerUrl, HttpMethod.GET, entity, BatchGetHwWalletResult.class);
            BatchGetHwWalletResult batchGetHwWalletResult = response.getBody();
            if (batchGetHwWalletResult == null) {
                System.out.println("getModels failed. batchGetHwWalletResult is null.");
                return null;
            } else {
                return batchGetHwWalletResult.getData();
            }
        } else {
            List<HwWalletObject> modelList = new LinkedList<>();
            String tempUrl = walletServerUrl + "?pageSize=" + pageSize;
            String session = "";
            while (true) {
                String finalUrl = tempUrl;
                if (!session.isEmpty()) {
                    finalUrl = finalUrl + "&session=" + session;
                }
                ResponseEntity<BatchGetHwWalletResult> response =
                    REST_TEMPLATE.exchange(finalUrl, HttpMethod.GET, entity, BatchGetHwWalletResult.class);
                BatchGetHwWalletResult batchGetHwWalletResult = response.getBody();
                if (batchGetHwWalletResult == null) {
                    System.out.println("getModels failed. batchGetHwWalletResult is null.");
                    return null;
                }
                List<HwWalletObject> data = batchGetHwWalletResult.getData();
                if (data == null || data.isEmpty()) {
                    return modelList;
                }
                modelList.addAll(data);
                PageInfo pageInfo = batchGetHwWalletResult.getPageInfo();
                if (pageInfo == null) {
                    System.out.println("getModels failed. pageInfo is null.");
                    return null;
                }
                String nextSession = pageInfo.getNextSession();
                if (nextSession == null || nextSession.isEmpty()) {
                    return modelList;
                }
                session = nextSession;
            }
        }
    }

    @Override
    public List<HwWalletObject> getInstances(String urlSegment, String modelId, Integer pageSize) {
        // Construct the http header.
        HttpHeaders header = constructHttpHeaders();
        // Construct the http URL.
        String baseUrl = ConfigHelper.instants().getValue("walletServerBaseUrl") + urlSegment;

        HttpEntity entity = new HttpEntity(header);
        if (pageSize == null) {
            ResponseEntity<BatchGetHwWalletResult> response = REST_TEMPLATE.exchange(urlSegment + "?modelId=" + modelId,
                HttpMethod.GET, entity, BatchGetHwWalletResult.class);
            BatchGetHwWalletResult batchGetHwWalletResult = response.getBody();
            if (batchGetHwWalletResult == null) {
                System.out.println("getInstances failed. batchGetHwWalletResult is null.");
                return null;
            } else {
                return batchGetHwWalletResult.getData();
            }
        } else {
            List<HwWalletObject> instanceList = new LinkedList<>();
            String tempUrl = baseUrl + "?modelId=" + modelId + "&pageSize=" + pageSize;
            String session = "";
            while (true) {
                String finalUrl = tempUrl;
                if (!session.isEmpty()) {
                    finalUrl = finalUrl + "&session=" + session;
                }
                ResponseEntity<BatchGetHwWalletResult> response =
                    REST_TEMPLATE.exchange(finalUrl, HttpMethod.GET, entity, BatchGetHwWalletResult.class);
                BatchGetHwWalletResult batchGetHwWalletResult = response.getBody();
                if (batchGetHwWalletResult == null) {
                    System.out.println("getInstances failed. batchGetHwWalletResult is null.");
                    return null;
                }
                List<HwWalletObject> data = batchGetHwWalletResult.getData();
                if (data == null || data.isEmpty()) {
                    return instanceList;
                }
                instanceList.addAll(data);
                PageInfo pageInfo = batchGetHwWalletResult.getPageInfo();
                if (pageInfo == null) {
                    System.out.println("getInstances failed. pageInfo is null.");
                    return null;
                }
                String nextSession = pageInfo.getNextSession();
                if (nextSession == null || nextSession.isEmpty()) {
                    return instanceList;
                }
                session = nextSession;
            }
        }
    }

    @Override
    public HwWalletObject fullUpdateHwWalletObject(String urlSegment, String id, String body) {
        // Construct the http header.
        HttpHeaders header = constructHttpHeaders();
        // Construct the http URL.
        String baseUrl = ConfigHelper.instants().getValue("walletServerBaseUrl");
        String walletServerUrl = baseUrl + urlSegment + id;

        // Send the http request and get response.
        HttpEntity<JSONObject> entity = new HttpEntity<>(JSONObject.parseObject(body), header);
        ResponseEntity<HwWalletObject> response =
            REST_TEMPLATE.exchange(walletServerUrl, HttpMethod.PUT, entity, HwWalletObject.class);

        // Return the updated model or instance.
        return response.getBody();
    }

    @Override
    public HwWalletObject partialUpdateHwWalletObject(String urlSegment, String id, String body) {
        // Construct the http header.
        HttpHeaders header = constructHttpHeaders();
        // Construct the http URL.
        String baseUrl = ConfigHelper.instants().getValue("walletServerBaseUrl");
        String walletServerUrl = baseUrl + urlSegment + id;

        // Send the http request and get response.
        HttpEntity<JSONObject> entity = new HttpEntity<>(JSONObject.parseObject(body), header);
        ResponseEntity<HwWalletObject> response =
            REST_TEMPLATE.exchange(walletServerUrl, HttpMethod.PATCH, entity, HwWalletObject.class);

        // Return the updated model or instance.
        return response.getBody();
    }

    @Override
    public HwWalletObject addMessageToHwWalletObject(String urlSegment, String id, String body) {
        // Construct the http header.
        HttpHeaders header = constructHttpHeaders();
        // Construct the http URL.
        String baseUrl = ConfigHelper.instants().getValue("walletServerBaseUrl");
        String walletServerUrl = baseUrl + urlSegment.replace("addMessage", "") + id + "/addMessage";

        // Send the http request and get response.
        HttpEntity<JSONObject> entity = new HttpEntity<>(JSONObject.parseObject(body), header);
        ResponseEntity<HwWalletObject> response =
            REST_TEMPLATE.exchange(walletServerUrl, HttpMethod.POST, entity, HwWalletObject.class);

        // Return the updated model or instance.
        return response.getBody();
    }

    @Override
    public HwWalletObject updateLinkedOffersToLoyaltyInstance(String urlSegment, String instanceId, String body) {
        // Construct the http header.
        HttpHeaders header = constructHttpHeaders();
        // Construct the http URL.
        String baseUrl = ConfigHelper.instants().getValue("walletServerBaseUrl");
        String walletServerUrl = baseUrl + urlSegment.replace("linkedoffers", instanceId) + "/linkedoffers";

        // Send the http request and get response.
        HttpEntity<JSONObject> entity = new HttpEntity<>(JSONObject.parseObject(body), header);
        ResponseEntity<HwWalletObject> response =
            REST_TEMPLATE.exchange(walletServerUrl, HttpMethod.PATCH, entity, HwWalletObject.class);

        // Return the updated instance.
        return response.getBody();
    }

    private HttpHeaders constructHttpHeaders() {
        HttpHeaders header = new HttpHeaders();
        // Get access token.
        try {
            String clientId = ConfigHelper.instants().getValue("gw.appid");
            String clientSecret = ConfigHelper.instants().getValue("gw.appid.secret");
            String accessToken = OpenGWClient.getToken(clientId, clientSecret);
            String authorization = AUTHORIZATION_HEAD.concat(accessToken);
            header.set("Content-Type", "application/json;charset=utf-8");
            header.set("Authorization", authorization);
            header.set("Accept", "application/json;charset=utf-8");
        } catch (Exception e) {
            throw new IllegalStateException("build HttpHeaders Fail", e);
        }
        return header;
    }
}
