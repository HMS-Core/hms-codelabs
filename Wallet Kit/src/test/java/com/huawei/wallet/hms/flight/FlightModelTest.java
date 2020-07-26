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

package com.huawei.wallet.hms.flight;

import com.alibaba.fastjson.JSONObject;
import com.huawei.wallet.hms.hmssdk.WalletBuildService;
import com.huawei.wallet.hms.hmssdk.dto.HwWalletObject;
import com.huawei.wallet.hms.hmssdk.impl.WalletBuildServiceImpl;
import com.huawei.wallet.util.CommonUtil;
import com.huawei.wallet.util.HwWalletObjectUtil;
import org.junit.Test;

import java.util.List;

/**
 * Flight model tests.
 *
 * @since 2019-12-12
 */
public class FlightModelTest {
    private WalletBuildService walletBuildService = new WalletBuildServiceImpl();

    /**
     * Create a new flight model.
     * Each flight model indicates a style of flight passes.
     * POST http://XXX/hmspass/v1/flight/model
     */
    @Test
    public void createFlightModel() {
        System.out.println("createFlightModel begin");

        // Read a flight model from a JSON file.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("FlightModel.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidModel = HwWalletObjectUtil.validateModel(model);
        if (!isValidModel) {
            System.out.println("Invalid model parameters.");
            return;
        }

        // Post the new flight model to HMS wallet server.
        String urlSegment = "flight/model";
        HwWalletObject responseModel =
            walletBuildService.postHwWalletObjectToWalletServer(urlSegment, CommonUtil.toJson(model));
        System.out.println("Posted flight model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Get a flight model by its model ID.
     * Run the "createFlightModel" test before running this test.
     * GET http://xxx/hmspass/v1/flight/model/{modelId}
     */
    @Test
    public void getFlightModel() {
        System.out.println("getFlightModel begin.");

        // ID of the flight model you want to get.
        String modelId = "FlightModelTest";

        // Get the flight model.
        String urlSegment = "flight/model/";
        HwWalletObject responseModel = walletBuildService.getHwWalletObjectById(urlSegment, modelId);
        System.out.println("Corresponding flight model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Get flight models belonging to a specific appId.
     * Run the "createFlightModel" test before running this test.
     * GET http://xxx/hmspass/v1/flight/model?session=XXX&pageSize=XXX
     */
    @Test
    public void getFlightModelList() {
        System.out.println("getFlightModelList begin.");

        // Get a list of flight models.
        String urlSegment = "flight/model";
        List<HwWalletObject> responseModels = walletBuildService.getModels(urlSegment, 5);
        System.out.println("Flight models list: " + CommonUtil.toJson(responseModels));
    }

    /**
     * Overwrite a flight model.
     * Run the "createFlightModel" test before running this test.
     * PUT http://xxx/hmspass/v1/flight/model/{modelId}
     */
    @Test
    public void fullUpdateFlightModel() {
        System.out.println("fullUpdateFlightModel begin.");

        // Read a HwWalletObject from a JSON file. This HwWalletObject will overwrite the corresponding model.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("FullUpdateFlightModel.json"), HwWalletObject.class);

        // Validate parameters.
        boolean isValidModel = HwWalletObjectUtil.validateModel(model);
        if (!isValidModel) {
            System.out.println("Invalid model parameters.");
            return;
        }

        // Update the flight model.
        String urlSegment = "flight/model/";
        HwWalletObject responseModel = walletBuildService.fullUpdateHwWalletObject(urlSegment,
            model.getPassStyleIdentifier(), CommonUtil.toJson(model));
        System.out.println("Updated flight model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Update a flight model.
     * Run the "createFlightModel" test before running this test.
     * PATCH http://xxx/hmspass/v1/flight/model/{modelId}
     */
    @Test
    public void partialUpdateFlightModel() {
        System.out.println("partialUpdateFlightModel begin.");

        // ID of the flight model you want to update.
        String modelId = "flightModelTest";

        // Read a HwWalletObject from a JSON file. This HwWalletObject will merge with the corresponding model.
        HwWalletObject model =
            JSONObject.parseObject(CommonUtil.readJSONFile("PartialUpdateFlightModel.json"), HwWalletObject.class);

        // Update the flight model.
        String urlSegment = "flight/model/";
        HwWalletObject responseModel =
            walletBuildService.partialUpdateHwWalletObject(urlSegment, modelId, CommonUtil.toJson(model));
        System.out.println("Updated flight model: " + CommonUtil.toJson(responseModel));
    }

    /**
     * Add messages to a flight model.
     * Run the "createFlightModel" test before running this test.
     * POST http://xxx/hmspass/v1/flight/model/{modelId}/addMessage
     */
    @Test
    public void addMessageToFlightModel() {
        System.out.println("addMessageToFlightModel begin.");

        // ID of the flight model you want to update.
        String modelId = "flightModelTest";

        // Create a list of messages you want to add to a model. Each message contains key, value, and label.
        // The list should not contain multiple messages with the same key. You can update an existing message by adding
        // a new message with the same key. One model contains at most 10 messages. If a model already have 10 messages
        // and you keep adding new messages, the oldest messages will be removed. You should not add more than 10
        // messages at a time.

        // Read messages from a JSON file.
        String messages = CommonUtil.readJSONFile("Messages.json");

        // Add messages to the flight model.
        String urlSegment = "flight/model/addMessage";
        HwWalletObject responseModel = walletBuildService.addMessageToHwWalletObject(urlSegment, modelId, messages);
        System.out.println("Updated flight model: " + CommonUtil.toJson(responseModel));
    }
}
