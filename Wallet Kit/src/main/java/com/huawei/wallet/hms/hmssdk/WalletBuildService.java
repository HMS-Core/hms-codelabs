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

package com.huawei.wallet.hms.hmssdk;

import com.huawei.wallet.hms.hmssdk.dto.HwWalletObject;

import java.util.List;

/**
 * Interface of invoking wallet api gateway service.
 *
 * @since 2019-11-05
 */
public interface WalletBuildService {
    /**
     * Post a HwWalletObject to wallet server.
     *
     * @param urlSegment request URL segment.
     * @param body JSON-type HwWalletObject.
     * @return the posted HwWalletObject.
     */
    HwWalletObject postHwWalletObjectToWalletServer(String urlSegment, String body);

    /**
     * Return the model/instance for a given model/instance ID.
     *
     * @param urlSegment request URL segment.
     * @param id model ID or instance ID.
     * @return the model/instance.
     */
    HwWalletObject getHwWalletObjectById(String urlSegment, String id);

    /**
     * Return a list of models belonging to a specific appId.
     *
     * @param urlSegment request URL segment.
     * @param pageSize maximum number of model in the returned list. All model created by the issuer
     *        will be returned if pageSize is null.
     * @return the models list.
     */
    List<HwWalletObject> getModels(String urlSegment, Integer pageSize);

    /**
     * Return a list of some instance belonging to a specific model.
     *
     * @param urlSegment request URL segment.
     * @param modelId model ID.
     * @param pageSize max number of instance in the returned list. All instance belongs to the wallet
     *        model will be returned if pageSize is null.
     * @return the instances list.
     */
    List<HwWalletObject> getInstances(String urlSegment, String modelId, Integer pageSize);

    /**
     * Overwrite a model/instance.
     *
     * @param urlSegment request URL segment.
     * @param id model/instance ID.
     * @param body JSON-type HwWalletObject.
     * @return the updated model/instance.
     */
    HwWalletObject fullUpdateHwWalletObject(String urlSegment, String id, String body);

    /**
     * Update a model/instance.
     *
     * @param urlSegment request URL segment.
     * @param id model/instance ID.
     * @param body JSON-type HwWalletObject.
     * @return the updated model/instance.
     */
    HwWalletObject partialUpdateHwWalletObject(String urlSegment, String id, String body);

    /**
     * Add messages to a HwWalletObject.
     *
     * @param urlSegment request URL segment.
     * @param id model/instance ID.
     * @param body JSON-type messageList object.
     * @return the updated model/instance.
     */
    HwWalletObject addMessageToHwWalletObject(String urlSegment, String id, String body);

    /**
     * Add/remove linked offer IDs to/from a loyalty instance.
     *
     * @param urlSegment request URL segment.
     * @param instanceId instance ID.
     * @param body JSON-type LinkedOfferInstanceIds object.
     * @return the updated loyalty instance.
     */
    HwWalletObject updateLinkedOffersToLoyaltyInstance(String urlSegment, String instanceId, String body);
}
