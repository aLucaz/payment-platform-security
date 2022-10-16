package pe.client.custom.app.util.constant;

public class Oauth {
    // oauth2 url
    public static final String OAUTH_URL = "${payment.platform.security.url}";

    // oauth2 constants
    public static final String OAUTH_TOKEN_TYPE = "access_token";
    public static final String OAUTH_TOKEN_ALGORITH = "{noop}";

    // oauth2 default paths
    public static final String OAUTH_DEFAULT_GET_TOKEN = "/oauth2/token";
    public static final String OAUTH_DEFAULT_INTROSPECT_TOKEN = "/oauth2/introspect";
    public static final String OAUTH_DEFAULT_CHECK_TOKEN = "/oauth2/check_token";
    public static final String OAUTH_DEFAULT_REVOKE_TOKEN = "/oauth2/token/revoke";
    public static final String OAUTH_DEFAULT_TOKEN_KEY = "/oauth2/token_key";
}
