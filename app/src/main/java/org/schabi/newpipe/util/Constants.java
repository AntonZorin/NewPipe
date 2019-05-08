package org.schabi.newpipe.util;

public class Constants {
    public static final String KEY_SERVICE_ID = "key_service_id";
    public static final String KEY_URL = "key_url";
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_LINK_TYPE = "key_link_type";
    public static final String KEY_OPEN_SEARCH = "key_open_search";
    public static final String KEY_SEARCH_STRING = "key_search_string";

    public static final String KEY_THEME_CHANGE = "key_theme_change";
    public static final String KEY_MAIN_PAGE_CHANGE = "key_main_page_change";

    public static final int NO_SERVICE_ID = -1;

    //Google O`Auth constants
    public static final String AUTH_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
    public static final String TOKEN_ENDPOINT = "https://www.googleapis.com/oauth2/v4/token";
    public static final String OAUTH_CLIENT_ID = "376377153534-m65i2ap2is78cp13vv1okdo1ml2bo1dc.apps.googleusercontent.com";
    public static final String REDIRECT = "org.schabi.newpipe:/oauth2callback";
    public static final String[] SCOPES = {
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/youtube.readonly"
    };
    public static final String KEY_HANDLE_AUTH = "HANDLE_AUTHORIZATION_RESPONSE";
    public static final String USED_INTENT_EXTRA_KEY = "this intent is used";

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String TOKEN_EXP_TIME = "token_exp_time";

}
