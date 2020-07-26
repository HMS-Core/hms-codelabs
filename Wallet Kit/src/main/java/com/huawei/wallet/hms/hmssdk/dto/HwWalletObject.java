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
 * The class of Huawei's pass objects. All pass models and instances are implemented as instances of this class.
 *
 * @since 2019-11-14
 */
public class HwWalletObject {
    private String passVersion;

    private String passTypeIdentifier;

    private String passStyleIdentifier;

    private String organizationName;

    private String organizationPassId;

    private String serialNumber;

    private Fields fields;

    private LinkDevicePass linkDevicePass;

    public String getPassVersion() {
        return passVersion;
    }

    public void setPassVersion(String passVersion) {
        this.passVersion = passVersion;
    }

    public String getPassTypeIdentifier() {
        return passTypeIdentifier;
    }

    public void setPassTypeIdentifier(String passTypeIdentifier) {
        this.passTypeIdentifier = passTypeIdentifier;
    }

    public String getPassStyleIdentifier() {
        return passStyleIdentifier;
    }

    public void setPassStyleIdentifier(String passStyleIdentifier) {
        this.passStyleIdentifier = passStyleIdentifier;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationPassId() {
        return organizationPassId;
    }

    public void setOrganizationPassId(String organizationPassId) {
        this.organizationPassId = organizationPassId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public LinkDevicePass getLinkDevicePass() {
        return linkDevicePass;
    }

    public void setLinkDevicePass(LinkDevicePass linkDevicePass) {
        this.linkDevicePass = linkDevicePass;
    }
}
