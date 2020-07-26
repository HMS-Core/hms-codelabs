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

/**
 * The type PageInfo.
 *
 * @since 2019-12-4
 */
public class PageInfo {
    /**
     * Number of results returned in this page.
     */
    private int pageSize;

    /**
     * Page session to send to fetch the next page.
     */
    private String nextSession;

    /**
     * Gets results per page.
     *
     * @return the results per page
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets results per page.
     *
     * @param pageSize the results per page
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets next page session.
     *
     * @return the next page session
     */
    public String getNextSession() {
        return nextSession;
    }

    /**
     * Sets next page session.
     *
     * @param nextSession the next page session
     */
    public void setNextSession(String nextSession) {
        this.nextSession = nextSession;
    }
}
