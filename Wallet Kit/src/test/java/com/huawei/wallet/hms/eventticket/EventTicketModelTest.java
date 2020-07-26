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
import org.junit.Test;

import java.util.List;

/**
 * Event ticket model tests.
 *
 * @since 2019-12-12
 */
public class EventTicketModelTest {
    private WalletBuildService walletBuildService = new WalletBuildServiceImpl();

    /**
     * Create a new event ticket model.
     * Each event ticket model indicates a style of event ticket passes..
     * POST http://XXX/hmspass/v1/eventticket/model
     */
    @Test
    public void createEventTicketModel() {
        System.out.println("createEventTicketModel begin");

        // Read an event ticket model from a JSON file.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("EventTicketModel.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidModel = HwWalletObjectUtil.validateModel(model);
        if (!isValidModel) {
            System.out.println("Invalid model parameters.");
            return;
        }

        // Post the new event ticket model to HMS wallet server.
        String urlSegment = "eventticket/model";
        HwWalletObject responseModel =
            walletBuildService.postHwWalletObjectToWalletServer(urlSegment, CommonUtil.toJson(model));
        System.out.println("Posted event ticket model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Get an event ticket model by its model ID.
     * Run the "createEventTicketModel" test before running this test.
     * GET http://xxx/hmspass/v1/eventticket/model/{modelId}
     */
    @Test
    public void getEventTicketModel() {
        System.out.println("getEventTicketModel begin.");

        // ID of the event ticket model you want to get.
        String modelId = "EventTicketModelTest";

        // Get the event ticket model.
        String urlSegment = "eventticket/model/";
        HwWalletObject responseModel = walletBuildService.getHwWalletObjectById(urlSegment, modelId);
        System.out.println("Corresponding event ticket model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Get event ticket models belonging to a specific appId.
     * Run the "createEventTicketModel" test before running this test.
     * GET http://xxx/hmspass/v1/eventticket/model?session=XXX&pageSize=XXX
     */
    @Test
    public void getEventTicketModelList() {
        System.out.println("getEventTicketModelList begin.");

        // Get a list of event ticket models.
        String urlSegment = "eventticket/model";
        List<HwWalletObject> responseModels = walletBuildService.getModels(urlSegment, 5);
        System.out.println("Event ticket models list: " + CommonUtil.toJson(responseModels));
    }

    /**
     * Overwrite an event ticket model.
     * Run the "createEventTicketModel" test before running this test.
     * PUT http://xxx/hmspass/v1/eventticket/model/{modelId}
     */
    @Test
    public void fullUpdateEventTicketModel() {
        System.out.println("fullUpdateEventTicketModel begin.");

        // Read a HwWalletObject from a JSON file. This HwWalletObject will overwrite the corresponding model.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("FullUpdateEventTicketModel.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidModel = HwWalletObjectUtil.validateModel(model);
        if (!isValidModel) {
            System.out.println("Invalid model parameters.");
            return;
        }

        // Update the event ticket model.
        String urlSegment = "eventticket/model/";
        HwWalletObject responseModel = walletBuildService.fullUpdateHwWalletObject(urlSegment,
            model.getPassStyleIdentifier(), CommonUtil.toJson(model));
        System.out.println("Updated event ticket model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Update an event ticket model.
     * Run the "createEventTicketModel" test before running this test.
     * PATCH http://xxx/hmspass/v1/eventticket/model/{modelId}
     */
    @Test
    public void partialUpdateEventTicketModel() {
        System.out.println("partialUpdateEventTicketModel begin.");

        // ID of the event ticket model you want to update.
        String modelId = "eventTicketModelTest";

        // Read a HwWalletObject from a JSON file. This HwWalletObject will merge with the corresponding model.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("PartialUpdateEventTicketModel.json"), HwWalletObject.class);

        // Update the event ticket model.
        String urlSegment = "eventticket/model/";
        HwWalletObject responseModel =
            walletBuildService.partialUpdateHwWalletObject(urlSegment, modelId, CommonUtil.toJson(model));
        System.out.println("Updated event ticket model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Add messages to an event ticket model.
     * Run the "createEventTicketModel" test before running this test.
     * POST http://xxx/hmspass/v1/eventticket/model/{modelId}/addMessage
     */
    @Test
    public void addMessageToEventTicketModel() {
        System.out.println("addMessageToEventTicketModel begin.");

        // ID of the event ticket model you want to update.
        String modelId = "eventTicketModelTest";

        // Create a list of messages you want to add to a model. Each message contains key, value, and label.
        // The list should not contain multiple messages with the same key. You can update an existing message by adding
        // a new message with the same key. One model contains at most 10 messages. If a model already have 10 messages
        // and you keep adding new messages, the oldest messages will be removed. You should not add more than 10
        // messages at a time.

        // Read messages from a JSON file.
        String messages = CommonUtil.readJSONFile("Messages.json");

        // Add messages to the event ticket model.
        String urlSegment = "eventticket/model/addMessage";
        HwWalletObject responseModel = walletBuildService.addMessageToHwWalletObject(urlSegment, modelId, messages);
        System.out.println("Updated event ticket model: " + CommonUtil.toJson(responseModel));
    }
}
