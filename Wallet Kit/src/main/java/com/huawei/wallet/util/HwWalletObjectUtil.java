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

package com.huawei.wallet.util;

import com.huawei.wallet.hms.hmssdk.dto.Fields;
import com.huawei.wallet.hms.hmssdk.dto.HwWalletObject;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * HwWalletObject utility class.
 *
 * @since 2019-12-4
 */
public class HwWalletObjectUtil {

    private static final String STRING_STATE_ACTIVE = "active";

    private static final String STRING_STATE_INACTIVE = "inactive";

    private static final String STRING_STATE_COMPLETED = "completed";

    private static final String STRING_STATE_EXPIRED = "expired";

    private static final List<String> STATE_TYPE_LIST = new ArrayList<>(
        Arrays.asList(STRING_STATE_ACTIVE, STRING_STATE_INACTIVE, STRING_STATE_COMPLETED, STRING_STATE_EXPIRED));

    /**
     * Validate a model.
     *
     * @param model the pass model.
     * @return if the pass model is valid.
     */
    public static boolean validateModel(HwWalletObject model) {
        boolean checkFlag;
        if (model != null) {
            checkFlag = checkRequiredParams(model.getPassTypeIdentifier(), "passTypeIdentifier", 64);
            if (checkFlag) {
                checkFlag = checkRequiredParams(model.getPassStyleIdentifier(), "passStyleIdentifier", 64);
            }
            if (checkFlag) {
                checkFlag = checkRequiredParams(model.getOrganizationName(), "organizationName", 64);
            }
            if (checkFlag) {
                checkFlag = checkRequiredParams(model.getPassVersion(), "passVersion", 64);
            }
        } else {
            System.out.println("The model is null");
            checkFlag = false;
        }
        return checkFlag;
    }

    /**
     * Validate an instance.
     *
     * @param instance the instance.
     * @return if the instance is valid.
     */
    public static boolean validateInstance(HwWalletObject instance) {
        boolean checkFlag;
        if (instance != null) {
            checkFlag = checkRequiredParams(instance.getPassTypeIdentifier(), "passTypeIdentifier", 64);
            if (checkFlag) {
                checkFlag = checkRequiredParams(instance.getPassStyleIdentifier(), "passStyleIdentifier", 64);
            }
            if (checkFlag) {
                checkFlag = checkRequiredParams(instance.getOrganizationPassId(), "organizationPassId", 64);
            }
            if (checkFlag) {
                checkFlag = checkRequiredParams(instance.getSerialNumber(), "serialNumber", 64);
            }
            if (checkFlag) {
                checkFlag = validateStatusDate(instance);
            }
        } else {
            System.out.println("The instance is null.");
            checkFlag = false;
        }
        return checkFlag;
    }

    /**
     * Check a required attribute.
     *
     * @param hwWalletObjectAttr the attribute's value.
     * @param attrName the attribute's name.
     * @param maxLen the attribute's maximum length.
     * @return if the attribute is defined and valid.
     */
    private static boolean checkRequiredParams(String hwWalletObjectAttr, String attrName, int maxLen) {
        if (StringUtils.isEmpty(hwWalletObjectAttr)) {
            System.out.println(attrName + " is empty.");
            return false;
        }
        if (hwWalletObjectAttr.length() > maxLen) {
            System.out.println(attrName + " exceeds maximum length.");
            return false;
        }
        return true;
    }

    /**
     * Check if the Status of an instance is legal.
     *
     * @param instance the instance.
     * @return if the Status is legal.
     */
    private static boolean validateStatusDate(HwWalletObject instance) {
        Fields fields = instance.getFields();
        if (CommonUtil.isNull(fields) || CommonUtil.isNull(fields.getStatus())) {
            return true;
        }
        String state = fields.getStatus().getState();
        String effectTime = fields.getStatus().getEffectTime();
        String expireTime = fields.getStatus().getExpireTime();
        if (!CommonUtil.isNull(state)) {
            if (!STATE_TYPE_LIST.contains(state.toLowerCase(Locale.getDefault()))) {
                System.out.println("state is invalid.");
                return false;
            }
        }

        if ((CommonUtil.isNull(effectTime) && !CommonUtil.isNull(expireTime))
            || (!CommonUtil.isNull(effectTime) && CommonUtil.isNull(expireTime))) {
            System.out.println("effectTime and expireTime should be both exist or not exist.");
            return false;
        }

        if (CommonUtil.isNull(expireTime)) {
            return true;
        }

        Date statusEffectTime;
        Date statusExpireTime;
        try {
            statusEffectTime = getLocalTimeByUtcString(fields.getStatus().getEffectTime());
            statusExpireTime = getLocalTimeByUtcString(fields.getStatus().getExpireTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        if ((statusExpireTime.before(new Date())) || (statusExpireTime.before(statusEffectTime))) {
            System.out.println("expireTime must be later than effectTime and current time.");
            return false;
        }

        return true;
    }

    /**
     * Convert UTC to local time.
     *
     * @param utcTimeString the UTC string.
     * @return the local time in Date type.
     */
    private static Date getLocalTimeByUtcString(String utcTimeString) {
        Date date = null;

        if (utcTimeString != null) {
            try {
                date = Date.from(Instant.parse(utcTimeString));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid time format: " + utcTimeString);
            }
        }

        return date;
    }
}
