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

package com.huawei.wallet.hms.eventticket;

import com.alibaba.fastjson.JSONObject;
import com.huawei.wallet.hms.hmssdk.WalletBuildService;
import com.huawei.wallet.hms.hmssdk.dto.HwWalletObject;
import com.huawei.wallet.hms.hmssdk.impl.WalletBuildServiceImpl;
import com.huawei.wallet.util.CommonUtil;
import com.huawei.wallet.util.HwWalletObjectUtil;
import com.huawei.wallet.util.JweUtil;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.List;

/**
 * Event ticket instance tests.
 *
 * @since 2019-12-12
 */
public class EventTicketInstanceTest {
    private WalletBuildService walletBuildService = new WalletBuildServiceImpl();

    /**
     * Add an event ticket instance to HMS wallet server.
     * Run the "createEventTicketModel" test before running this test.
     * After using this API, you will use a thin JWE to bind this instance to a user. You can also add an instance by
     * sending a JWE with all instance information, without using this API. See JWE example methods at the bottom.
     * POST http://XXX/hmspass/v1/eventticket/instance
     */
    @Test
    public void addEventTicketInstance() {
        System.out.println("addEventTicketInstance begin");

        // Read an event ticket instance from a JSON file.
        HwWalletObject instance =
            JSONObject.parseObject(CommonUtil.readJSONFile("EventTicketInstance.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidInstance = HwWalletObjectUtil.validateInstance(instance);
        if (!isValidInstance) {
            System.out.println("Invalid instance parameters.");
            return;
        }

        // Post the new event ticket instance to HMS wallet server.
        String urlSegment = "eventticket/instance";
        HwWalletObject responseInstance =
            walletBuildService.postHwWalletObjectToWalletServer(urlSegment, CommonUtil.toJson(instance));
        System.out.println("Posted event ticket instance: " + CommonUtil.toJson(responseInstance));
    }

    /**
     * Get an event ticket instance by its instance ID.
     * Run the "createEventTicketInstance" test before running this test.
     * GET http://xxx/hmspass/v1/eventticket/instance/{instanceId}
     */
    @Test
    public void getEventTicketInstance() {
        System.out.println("getEventTicketInstance begin.");

        // ID of the event ticket instance you want to get.
        String instanceId = "10001";

        // Get the event ticket instance.
        String urlSegment = "eventticket/instance/";
        HwWalletObject responseInstance = walletBuildService.getHwWalletObjectById(urlSegment, instanceId);
        System.out.println("Corresponding event ticket instance: " + CommonUtil.toJson(responseInstance));
    }

    /**
     * Get event ticket instances belonging to a specific event ticket model.
     * Run the "createEventTicketInstance" test before running this test.
     * GET http://xxx/hmspass/v1/eventticket/instance?modelId=XXX&session=XXX&pageSize=XXX
     */
    @Test
    public void getEventTicketInstanceList() {
        System.out.println("getEventTicketInstanceList begin.");

        // Model ID of event ticket instances you want to get.
        String modelId = "eventTicketModelTest";

        // Get a list of the event ticket instances.
        String urlSegment = "eventticket/instance";
        List<HwWalletObject> responseInstances = walletBuildService.getInstances(urlSegment, modelId, 5);
        System.out.println("Event ticket instances list: " + CommonUtil.toJson(responseInstances));
    }

    /**
     * Overwrite an event ticket instance.
     * Run the "createEventTicketInstance" test before running this test.
     * PUT http://xxx/hmspass/v1/eventticket/instance/{instanceId}
     */
    @Test
    public void fullUpdateEventTicketInstance() {
        System.out.println("fullUpdateEventTicketInstance begin.");

        // Read a HwWalletObject from a JSON file. This HwWalletObject will overwrite the corresponding instance.
        HwWalletObject instance =
            JSONObject.parseObject(CommonUtil.readJSONFile("FullUpdateEventTicketInstance.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidInstance = HwWalletObjectUtil.validateInstance(instance);
        if (!isValidInstance) {
            System.out.println("Invalid instance parameters.");
            return;
        }

        // Update the event ticket instance.
        String urlSegment = "eventticket/instance/";
        HwWalletObject responseInstance = walletBuildService.fullUpdateHwWalletObject(urlSegment,
            instance.getSerialNumber(), CommonUtil.toJson(instance));
        System.out.println("Updated event ticket instance: " + CommonUtil.toJson(responseInstance));
    }

    /**
     * Update an event ticket instance.
     * Run the "createEventTicketInstance" test before running this test.
     * PATCH http://xxx/hmspass/v1/eventticket/instance/{instanceId}
     */
    @Test
    public void partialUpdateEventTicketInstance() {
        System.out.println("partialUpdateEventTicketInstance begin.");

        // ID of the event ticket instance you want to update.
        String instanceId = "10001";

        // Read a HwWalletObject from a JSON file. This HwWalletObject will merge with the corresponding instance.
        HwWalletObject instance = JSONObject
            .parseObject(CommonUtil.readJSONFile("PartialUpdateEventTicketInstance.json"), HwWalletObject.class);

        // Update the event ticket instance.
        String urlSegment = "eventticket/instance/";
        HwWalletObject responseInstance =
            walletBuildService.partialUpdateHwWalletObject(urlSegment, instanceId, CommonUtil.toJson(instance));
        System.out.println("Updated event ticket instance: " + CommonUtil.toJson(responseInstance));
    }

    /**
     * Add messages to an event ticket instance.
     * Run the "createEventTicketInstance" test before running this test.
     * POST http://xxx/hmspass/v1/eventticket/instance/{instanceId}/addMessage
     */
    @Test
    public void addMessageToEventTicketInstance() {
        System.out.println("addMessageToEventTicketInstance begin.");

        // ID of the event ticket instance you want to update.
        String instanceId = "10001";

        // Create a list of messages you want to add to an instance. Each message contains key, value, and label.
        // The list should not contain multiple messages with the same key. You can update an existing message by adding
        // a new message with the same key. One instance contains at most 10 messages. If an instance already have 10
        // messages and you keep adding new messages, the oldest messages will be removed. You should not add more than
        // 10 messages at a time.

        // Read messages from a JSON file.
        String messages = CommonUtil.readJSONFile("Messages.json");

        // Add messages to the event ticket instance.
        String urlSegment = "eventticket/instance/addMessage";
        HwWalletObject responseInstance =
            walletBuildService.addMessageToHwWalletObject(urlSegment, instanceId, messages);
        System.out.println("Updated event ticket instance: " + CommonUtil.toJson(responseInstance));
    }

    /**
     * Generate a thin JWE. This JWEs contains only instanceId information. It's used to bind an existing instance
     * to a user. You should generate a thin JWE with an instanceId that has already been added to HMS wallet server.
     */
    @Test
    public void generateThinJWEToBindUser() {
        System.out.println("generateThinJWEToBindUser begin.\n");

        // This is the app ID registered on Huawei AGC website.
        String appId = "Replace with your app ID";
        // Bind existing event ticket instances to users. Construct a list of event ticket-instance IDs to be bound.
        String instanceIdListJson = "{\"instanceIds\": [\"10001\"]}";
        JSONObject instanceIdListJsonObject = JSONObject.parseObject(instanceIdListJson);
        instanceIdListJsonObject.put("iss", appId);
        String payload = instanceIdListJsonObject.toJSONString();

        // You generated a pair of RSA keys while applying for services on AGC. Use that private key here.
        String jweSignPrivateKey = "Replace with your private key.";

        // Generate a thin JWE.
        String jwe = JweUtil.generateJwe(jweSignPrivateKey, payload);
        System.out.println("JWE String: " + jwe + "\n");

        // Replace {walletkit_website_url} with one of the following strings according to your account location.
        // walletpass-drcn.cloud.huawei.com for China
        // walletpass-drru.cloud.huawei.com for Russia
        // walletpass-dra.cloud.huawei.com for Asia, Africa, and Latin America
        // walletpass-dre.cloud.huawei.com for Europe
        System.out.println("JWE link to be viewed in a browser: "
            + "https://{walletkit_website_url}/walletkit/consumer/pass/save?jwt=" + URLEncoder.encode(jwe));
    }

    /**
     * Generate a JWE. This JWE contains full instance information. It's used to add a new instance to HMS wallet serve
     * and bind it to a user. You should generate a JWE with an instance that has not been added to HMS wallet server.
     */
    @Test
    public void generateJWEToAddInstanceAndBindUser() {
        System.out.println("generateJWEToAddPassAndBindUser begin.\n");

        // This is the app ID registered on Huawei AGC website.
        String appId = "Replace with your app ID";
        // Read a new event ticket instance.
        String newInstanceJson = CommonUtil.readJSONFile("EventTicketInstance.json");
        JSONObject newInstanceJsonObject = JSONObject.parseObject(newInstanceJson);
        newInstanceJsonObject.put("iss", appId);
        String payload = newInstanceJsonObject.toJSONString();

        // You generated a pair of RSA keys while applying for services on AGC. Use that private key here.
        String jweSignPrivateKey = "Replace with your private key.";

        // Generate a JWE.
        String jwe = JweUtil.generateJwe(jweSignPrivateKey, payload);
        System.out.println("JWE String: " + jwe + "\n");

        // Replace {walletkit_website_url} with one of the following strings according to your account location.
        // walletpass-drcn.cloud.huawei.com for China
        // walletpass-drru.cloud.huawei.com for Russia
        // walletpass-dra.cloud.huawei.com for Asia, Africa, and Latin America
        // walletpass-dre.cloud.huawei.com for Europe
        System.out.println("JWE link to be viewed in a browser: "
            + "https://{walletkit_website_url}/walletkit/consumer/pass/save?jwt=" + URLEncoder.encode(jwe));
    }
}
