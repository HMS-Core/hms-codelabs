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

package com.huawei.wallet.hms.hmssdk.dto;

import java.util.List;

/**
 * Stores the results of querying card templates or objects in batches by page.
 *
 * @since 2019-10-26
 */
public class BatchGetHwWalletResult {
    /**
     * data : ["content1","content2"]
     * pageInfo : {"serviceType":"hmspass#pageInfo","pageSize":10,"nextSession":"nextSession"}
     */
    private PageInfo pageInfo;

    private List<HwWalletObject> data;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<HwWalletObject> getData() {
        return data;
    }

    public void setData(List<HwWalletObject> data) {
        this.data = data;
    }
}
