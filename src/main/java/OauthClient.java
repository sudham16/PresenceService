import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class OauthClient {

    public static final String PASSWORD = "A1234gyh";
    public static final String USERNAME = "TEST1010";
    public static final String RESOURCE = "JPMC:URI:RS-103146-10145-PreastoSF-UAT";
    private static final String CLIENT_ID = PASSWORD;

    public static void main(String[] args) throws OAuthProblemException, OAuthSystemException {
        OAuthClient client = new OAuthClient(new URLConnectionClient());
        OAuthClientRequest request =
                OAuthClientRequest.tokenLocation("https://idauatg2.jpmorganchase.com/adfs/oauth2/token")
                        .setGrantType(GrantType.PASSWORD)
                        .setClientId(CLIENT_ID)
                        .setUsername(USERNAME)
                        .setPassword(PASSWORD)
                        .setParameter("resource",RESOURCE)
                        .buildBodyMessage();


        String token = client.accessToken(request,
                OAuth.HttpMethod.POST,
                OAuthJSONAccessTokenResponse.class).getAccessToken();
        System.out.println(token);
    }
}
