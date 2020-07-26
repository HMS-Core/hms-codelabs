
package com.huawei.wallet.hms.hmssdk.dto;

/**
 * The response when getting a token.
 *
 * @since 2019-01-14
 */
public class TokenResponse {
    private String access_token;

    private int expires_in;

    private String token_type;

    private int error;

    private int sub_error;

    private String error_description;

    public String getAccess_token() {
        return access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getError() {
        return error;
    }

    public int getSub_error() {
        return sub_error;
    }

    public String getError_description() {
        return error_description;
    }
}
