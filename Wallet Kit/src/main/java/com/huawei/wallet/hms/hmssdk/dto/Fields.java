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
 * The class of Fields attribute in the HwWalletObject class. It contains additional attributes of an instance.
 *
 * @since 2019-11-14
 */
public class Fields {
    private String srcPassTypeIdentifier;

    private String srcPassIdentifier;

    private String countryCode;

    private String isUserDiy;

    private Status status;

    private List<RelatedPassId> relatedPassIds;

    private List<Location> locationList;

    private BarCode barCode;

    private List<ValueObject> commonFields;

    private List<ValueObject> appendFields;

    private List<ValueObject> messageList;

    private List<ValueObject> timeList;

    private List<ValueObject> imageList;

    private List<ValueObject> textList;

    private List<Localized> localized;

    private List<ValueObject> ticketInfoList;

    public String getSrcPassTypeIdentifier() {
        return srcPassTypeIdentifier;
    }

    public void setSrcPassTypeIdentifier(String srcPassTypeIdentifier) {
        this.srcPassTypeIdentifier = srcPassTypeIdentifier;
    }

    public String getSrcPassIdentifier() {
        return srcPassIdentifier;
    }

    public void setSrcPassIdentifier(String srcPassIdentifier) {
        this.srcPassIdentifier = srcPassIdentifier;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIsUserDiy() {
        return isUserDiy;
    }

    public void setIsUserDiy(String isUserDiy) {
        this.isUserDiy = isUserDiy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<RelatedPassId> getRelatedPassIds() {
        return relatedPassIds;
    }

    public void setRelatedPassIds(List<RelatedPassId> relatedPassIds) {
        this.relatedPassIds = relatedPassIds;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public BarCode getBarCode() {
        return barCode;
    }

    public void setBarCode(BarCode barCode) {
        this.barCode = barCode;
    }

    public List<ValueObject> getCommonFields() {
        return commonFields;
    }

    public void setCommonFields(List<ValueObject> commonFields) {
        this.commonFields = commonFields;
    }

    public List<ValueObject> getAppendFields() {
        return appendFields;
    }

    public void setAppendFields(List<ValueObject> appendFields) {
        this.appendFields = appendFields;
    }

    public List<ValueObject> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<ValueObject> messageList) {
        this.messageList = messageList;
    }

    public List<ValueObject> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<ValueObject> timeList) {
        this.timeList = timeList;
    }

    public List<ValueObject> getImageList() {
        return imageList;
    }

    public void setImageList(List<ValueObject> imageList) {
        this.imageList = imageList;
    }

    public List<ValueObject> getTextList() {
        return textList;
    }

    public void setTextList(List<ValueObject> textList) {
        this.textList = textList;
    }

    public List<Localized> getLocalized() {
        return localized;
    }

    public void setLocalized(List<Localized> localized) {
        this.localized = localized;
    }

    public List<ValueObject> getTicketInfoList() {
        return ticketInfoList;
    }

    public void setTicketInfoList(List<ValueObject> ticketInfoList) {
        this.ticketInfoList = ticketInfoList;
    }
}
