package com.wezom.net.responses;

import com.google.gson.annotations.SerializedName;

public class RefreshedTokenResponse {
    @SerializedName("access_token") public String accessToken;
    @SerializedName("expires_in") public Long expiresIn;
    @SerializedName("scope") public String scope;
    @SerializedName("token_type") public String tokenType;
    @SerializedName("id_token") public String idToken;
}
