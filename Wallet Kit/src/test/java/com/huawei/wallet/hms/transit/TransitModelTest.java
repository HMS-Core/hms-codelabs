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

package com.huawei.wallet.hms.transit;

import com.alibaba.fastjson.JSONObject;
import com.huawei.wallet.hms.hmssdk.WalletBuildService;
import com.huawei.wallet.hms.hmssdk.dto.HwWalletObject;
import com.huawei.wallet.hms.hmssdk.impl.WalletBuildServiceImpl;
import com.huawei.wallet.util.CommonUtil;
import com.huawei.wallet.util.HwWalletObjectUtil;
import org.junit.Test;

import java.util.List;

/**
 * Transit model tests.
 *
 * @since 2019-12-12
 */
public class TransitModelTest {
    private WalletBuildService walletBuildService = new WalletBuildServiceImpl();

    /**
     * Create a new transit model.
     * Each transit model indicates a style of transit passes.
     * POST http://XXX/hmspass/v1/transit/model
     */
    @Test
    public void createTransitModel() {
        System.out.println("createTransitModel begin");

        // Read a transit model from a JSON file.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("TransitModel.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidModel = HwWalletObjectUtil.validateModel(model);
        if (!isValidModel) {
            System.out.println("Invalid model parameters.");
            return;
        }

        // Post the new transit model to HMS wallet server.
        String urlSegment = "transit/model";
        HwWalletObject responseModel =
            walletBuildService.postHwWalletObjectToWalletServer(urlSegment, CommonUtil.toJson(model));
        System.out.println("Posted transit model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Get a transit model by its model ID.
     * Run the "createTransitModel" test before running this test.
     * GET http://xxx/hmspass/v1/transit/model/{modelId}
     */
    @Test
    public void getTransitModel() {
        System.out.println("getTransitModel begin.");

        // ID of the transit model you want to get.
        String modelId = "TransitModelTest";

        // Get the transit model.
        String urlSegment = "transit/model/";
        HwWalletObject responseModel = walletBuildService.getHwWalletObjectById(urlSegment, modelId);
        System.out.println("Corresponding transit model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Get transit model belonging to a specific appId.
     * Run the "createTransitModel" test before running this test.
     * GET http://xxx/hmspass/v1/transit/model?session=XXX&pageSize=XXX
     */
    @Test
    public void getTransitModelList() {
        System.out.println("getTransitModelList begin.");

        // Get a list of transit models.
        String urlSegment = "transit/model";
        List<HwWalletObject> responseModels = walletBuildService.getModels(urlSegment, 5);
        System.out.println("Transit models list: " + CommonUtil.toJson(responseModels));
    }

    /**
     * Overwrite a transit model.
     * Run the "createTransitModel" test before running this test.
     * PUT http://xxx/hmspass/v1/transit/model/{modelId}
     */
    @Test
    public void fullUpdateTransitModel() {
        System.out.println("fullUpdateTransitModel begin.");

        // Read a HwWalletObject from a JSON file. This HwWalletObject will overwrite the corresponding model.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("FullUpdateTransitModel.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidModel = HwWalletObjectUtil.validateModel(model);
        if (!isValidModel) {
            System.out.println("Invalid model parameters.");
            return;
        }

        // Update the transit model.
        String urlSegment = "transit/model/";
        HwWalletObject responseModel = walletBuildService.fullUpdateHwWalletObject(urlSegment,
            model.getPassStyleIdentifier(), CommonUtil.toJson(model));
        System.out.println("Updated transit model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Update a transit model.
     * Run the "createTransitModel" test before running this test.
     * PATCH http://xxx/hmspass/v1/transit/model/{modelId}
     */
    @Test
    public void partialUpdateTransitModel() {
        System.out.println("partialUpdateTransitModel begin.");

        // ID of the transit model you want to update.
        String modelId = "transitModelTest";

        // Read a HwWalletObject from a JSON file. This HwWalletObject will merge with the corresponding model.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("PartialUpdateTransitModel.json"), HwWalletObject.class);

        // Update the transit model.
        String urlSegment = "transit/model/";
        HwWalletObject responseModel =
            walletBuildService.partialUpdateHwWalletObject(urlSegment, modelId, CommonUtil.toJson(model));
        System.out.println("Updated transit model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Add messages to a transit model.
     * Run the "createTransitModel" test before running this test.
     * POST http://xxx/hmspass/v1/transit/model/{modelId}/addMessage
     */
    @Test
    public void addMessageToTransitModel() {
        System.out.println("addMessageToTransitModel begin.");

        // ID of the transit model you want to update.
        String modelId = "transitModelTest";

        // Create a list of messages you want to add to a model. Each message contains key, value, and label.
        // The list should not contain multiple messages with the same key. You can update an existing message by adding
        // a new message with the same key. One model contains at most 10 messages. If a model already have 10 messages
        // and you keep adding new messages, the oldest messages will be removed. You should not add more than 10
        // messages at a time.

        // Read messages from a JSON file.
        String messages = CommonUtil.readJSONFile("Messages.json");

        // Add messages to the transit model.
        String urlSegment = "transit/model/addMessage";
        HwWalletObject responseModel = walletBuildService.addMessageToHwWalletObject(urlSegment, modelId, messages);
        System.out.println("Updated transit model: " + CommonUtil.toJson(responseModel));
    }
}
