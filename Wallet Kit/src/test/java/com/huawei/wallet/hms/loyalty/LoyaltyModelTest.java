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

package com.huawei.wallet.hms.loyalty;

import com.alibaba.fastjson.JSONObject;
import com.huawei.wallet.hms.hmssdk.WalletBuildService;
import com.huawei.wallet.hms.hmssdk.dto.HwWalletObject;
import com.huawei.wallet.hms.hmssdk.impl.WalletBuildServiceImpl;
import com.huawei.wallet.util.CommonUtil;
import com.huawei.wallet.util.HwWalletObjectUtil;
import org.junit.Test;

import java.util.List;

/**
 * Loyalty model tests.
 *
 * @since 2019-12-12
 */
public class LoyaltyModelTest {
    private WalletBuildService walletBuildService = new WalletBuildServiceImpl();

    /**
     * Create a new loyalty model.
     * Each loyalty model indicates a style of loyalty passes.
     * POST http://XXX/hmspass/v1/loyalty/model
     */
    @Test
    public void createLoyaltyModel() {
        System.out.println("createLoyaltyModel begin");

        // Read a loyalty model from a JSON file.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("LoyaltyModel.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidModel = HwWalletObjectUtil.validateModel(model);
        if (!isValidModel) {
            System.out.println("Invalid model parameters.");
            return;
        }

        // Post the new loyalty model to HMS wallet server.
        String urlSegment = "loyalty/model";
        HwWalletObject responseModel =
            walletBuildService.postHwWalletObjectToWalletServer(urlSegment, CommonUtil.toJson(model));
        System.out.println("Posted loyalty model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Get a loyalty model by its model ID.
     * Run the "createLoyaltyModel" test before running this test.
     * GET http://xxx/hmspass/v1/loyalty/model/{modelId}
     */
    @Test
    public void getLoyaltyModel() {
        System.out.println("getLoyaltyModel begin.");

        // ID of the loyalty model you want to get.
        String modelId = "LoyaltyModelTest";

        // Get the loyalty model.
        String urlSegment = "loyalty/model/";
        HwWalletObject responseModel = walletBuildService.getHwWalletObjectById(urlSegment, modelId);
        System.out.println("Corresponding loyalty model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Get loyalty models belonging to a specific appId.
     * Run the "createLoyaltyModel" test before running this test.
     * GET http://xxx/hmspass/v1/loyalty/model?session=XXX&pageSize=XXX
     */
    @Test
    public void getLoyaltyModelList() {
        System.out.println("getLoyaltyModelList begin.");

        // Get a list of loyalty models.
        String urlSegment = "loyalty/model";
        List<HwWalletObject> responseModels = walletBuildService.getModels(urlSegment, 5);
        System.out.println("Loyalty models list: " + CommonUtil.toJson(responseModels));
    }

    /**
     * Overwrite a loyalty model.
     * Run the "createLoyaltyModel" test before running this test.
     * PUT http://xxx/hmspass/v1/loyalty/model/{modelId}
     */
    @Test
    public void fullUpdateLoyaltyModel() {
        System.out.println("fullUpdateLoyaltyModel begin.");

        // Read a HwWalletObject from a JSON file. This HwWalletObject will overwrite the corresponding model.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("FullUpdateLoyaltyModel.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidModel = HwWalletObjectUtil.validateModel(model);
        if (!isValidModel) {
            System.out.println("Invalid model parameters.");
            return;
        }

        // Update the loyalty model.
        String urlSegment = "loyalty/model/";
        HwWalletObject responseModel = walletBuildService.fullUpdateHwWalletObject(urlSegment,
            model.getPassStyleIdentifier(), CommonUtil.toJson(model));
        System.out.println("Updated loyalty model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Update a loyalty model.
     * Run the "createLoyaltyModel" test before running this test.
     * PATCH http://xxx/hmspass/v1/loyalty/model/{modelId}
     */
    @Test
    public void partialUpdateLoyaltyModel() {
        System.out.println("partialUpdateLoyaltyModel begin.");

        // ID of the loyalty model you want to update.
        String modelId = "loyaltyModelTest";

        // Read a HwWalletObject from a JSON file. This HwWalletObject will merge with the corresponding model.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("PartialUpdateLoyaltyModel.json"), HwWalletObject.class);

        // Update the loyalty model.
        String urlSegment = "loyalty/model/";
        HwWalletObject responseModel =
            walletBuildService.partialUpdateHwWalletObject(urlSegment, modelId, CommonUtil.toJson(model));
        System.out.println("Updated loyalty model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Add messages to a loyalty model.
     * Run the "createLoyaltyModel" test before running this test.
     * POST http://xxx/hmspass/v1/loyalty/model/{modelId}/addMessage
     */
    @Test
    public void addMessageToLoyaltyModel() {
        System.out.println("addMessageToLoyaltyModel begin.");

        // ID of the loyalty model you want to update.
        String modelId = "loyaltyModelTest";

        // Create a list of messages you want to add to a model. Each message contains key, value, and label.
        // The list should not contain multiple messages with the same key. You can update an existing message by adding
        // a new message with the same key. One model contains at most 10 messages. If a model already have 10 messages
        // and you keep adding new messages, the oldest messages will be removed. You should not add more than 10
        // messages at a time.

        // Read messages from a JSON file.
        String messages = CommonUtil.readJSONFile("Messages.json");

        // Add messages to the loyalty model.
        String urlSegment = "loyalty/model/addMessage";
        HwWalletObject responseModel = walletBuildService.addMessageToHwWalletObject(urlSegment, modelId, messages);
        System.out.println("Updated loyalty model: " + CommonUtil.toJson(responseModel));
    }
}
