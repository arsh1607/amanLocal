package socialmediawrapper;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.digits.sdk.android.Digits;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import global_constants.Constants;
import global_constants.GetJsonAsyn;
import global_constants.UtilitySingleton;
import model.ModalFbUserProfile;
import com.google.android.gms.common.api.Status;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by Balwinder on 23/12/15.
 */
//
public abstract class SocialMediaIntegration extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ResultCallback<People.LoadPeopleResult>
{
    private LinkedInOAuthService oAuthService;
    private LinkedInApiClientFactory factory;
    private LinkedInRequestToken liToken;
    private LinkedInApiClient client;
    TwitterLoginButton loginButton;
    TwitterSession session;
    SocialMediaInterface facebookIntegrate;
    private CallbackManager callbackManager;
    AccessToken access;
    TwitterLoginButton btn;
    //Google Plus Login
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
    private GoogleApiClient mGoogleApiClient;
    Person currentPerson;
    private static final int SIGN_IN_CODE = 0;
    private int request_code;
    //handle onActivity result
    static int fb_gp;
    ProgressDialog progress_dialog;
     TwitterAuthClient mTwitterAuthClient=null ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //facebook integration

         facebookSDKInitialize();
        //google plus integration

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        //initialize interface
        facebookIntegrate = (SocialMediaInterface) SocialMediaIntegration.this;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
    protected void facebookSDKInitialize()
    {
        FacebookSdk.sdkInitialize(SocialMediaIntegration.this);
        callbackManager = CallbackManager.Factory.create();
    }

    public void getfriendlist_googlePlus()
    {


        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }
    private void setUpFaceBook()
    {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getLoggedinUserData(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Facebook Cancel");
            }

            @Override
            public void onError(FacebookException exception) {

                UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Facebook Exception" + exception);
            }
        });
    }

    /*    FACEBOOK SIGNIN  */
    public void facebookButtonClicked(int fb)
    {
        fb_gp=fb;
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
        setUpFaceBook();
    }
    private void extractFacebookUserData(ModalFbUserProfile user)
    {
        facebookIntegrate.login(user);
    }
    private void getLoggedinUserData(AccessToken accessToken)
    {
        access=accessToken;
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                ModalFbUserProfile user = new Gson().fromJson(object.toString(), ModalFbUserProfile.class);

                extractFacebookUserData(user);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,picture");
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }


    /*    GETTING FACEBOOK LOGOUT  */
    public void fb_logout()
    {
        LoginManager.getInstance().logOut();
        UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Logout");
    }

    /*    GETTING FACEBOOK FRIENDLIST  */
    public void getfriendlist()
    {
       if(access==null)
       {
           UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Please Login First");
       }
       else
       {
          GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                   access,
                   //AccessToken.getCurrentAccessToken(),
                   "/me/friends",
                   null,
                   HttpMethod.GET,
                   new GraphRequest.Callback()
                   {
                       public void onCompleted(GraphResponse response)
                       {
                           try
                           {
                               JSONObject obj = response.getJSONObject();
                               facebookIntegrate.getfriend_list(obj);
                              // JSONArray rawName = response.getJSONObject().getJSONArray("data");

                           }
                           catch (Exception e)
                           {
                               e.printStackTrace();
                           }
                       }
                   }
           ).executeAsync();
           /////////////////////////////////////////////////////////////////////////////////////////

       }
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
       //  Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
        if (fb_gp == 1) {
            //facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (fb_gp == 2) {
        //google
        if (requestCode == SIGN_IN_CODE)
        {

            request_code = requestCode;
            if (resultCode != RESULT_OK)
            {
                mSignInClicked = false;
                progress_dialog.dismiss();
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnected())
            {
                mGoogleApiClient.connect();
            }
        }
    }
        if(fb_gp==3){
            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
           // onActivityResult(requestCode, resultCode, data);
        }

    }
    ///////////////////////////////////////////////Google Plus Integration//////////////////////////////////////////////////
    @Override
    public void onResult(People.LoadPeopleResult peopleData)
    {

        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {

            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            ArrayList<String> list = new ArrayList<String>();
            ArrayList<String> img_list= new ArrayList<String>();

            try {
                int count = personBuffer.getCount();

                for (int i = 0; i<count; i++)
                {

                    list.add(personBuffer.get(i).getDisplayName());
                    img_list.add(personBuffer.get(i).getImage().getUrl());
                    UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Friend Name="+personBuffer.get(i).getDisplayName());

                }



            } finally {
                personBuffer.release();
            }
        } else {
            Log.e("circle error", "Error requesting visible circles: " + peopleData.getStatus());
        }
    }

    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();

    }
    protected void onStop()
    {
        super.onStop();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }
    protected void onResume()
    {
        super.onResume();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.connect();
        }
    }
    public void onConnectionFailed(ConnectionResult result)
    {
        if (!result.hasResolution())
        {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }
        if (!mIntentInProgress)
        {
            mConnectionResult = result;
            if (mSignInClicked)
            {
                resolveSignInError();
            }
        }
    }
    public void onConnectionSuspended(int arg0)
    {
        mGoogleApiClient.connect();
    }

    /*    GOOGLEPLUS SIGNIN  */
    public void signInWithGplus(int gp)
    {
        fb_gp=gp;
        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Signing in....");
        if (!mGoogleApiClient.isConnecting())
        {
            mSignInClicked = true;
            progress_dialog.show();
            resolveSignInError();
        }
    }
    private void resolveSignInError()
    {
        if (mConnectionResult.hasResolution())
        {
            try
            {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, SIGN_IN_CODE);
            }
            catch (IntentSender.SendIntentException e)
            {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    public void onConnected(Bundle arg0)
    {
        mSignInClicked = false;
        getProfileInformation();
        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient).setResultCallback(new ResultCallback<Status>()
        {
            @Override
            public void onResult(Status arg0)
            {
                mGoogleApiClient.connect();
            }
        });
    }
    private void getProfileInformation()
    {
        try
        {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null)
            {
                currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                progress_dialog.dismiss();
                facebookIntegrate.getProfileInformation_of_google_plus(currentPerson, email);
            }
            else
            {
                UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Your information is not available");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /*    LINKEDIN SIGNUP  */
    public void Initalizelinkedin(){

        oAuthService = LinkedInOAuthServiceFactory.getInstance()
                .createLinkedInOAuthService(Constants.CONSUMER_KEY,
                        Constants.CONSUMER_SECRET);
        System.out.println("oAuthService : " + oAuthService);

        factory = LinkedInApiClientFactory.newInstance(
                Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);

        liToken = oAuthService
                .getOAuthRequestToken(Constants.OAUTH_CALLBACK_URL);

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(liToken
                .getAuthorizationUrl()));
        startActivity(i);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        try {
            linkedInImport(intent);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void linkedInImport(Intent intent) {
        //    Toast.makeText(getApplicationContext(),"linkedInImport",Toast.LENGTH_SHORT).show();;
        String verifier = intent.getData().getQueryParameter("oauth_verifier");
        System.out.println("liToken " + liToken);
        System.out.println("verifier " + verifier);

        LinkedInAccessToken accessToken = oAuthService.getOAuthAccessToken(
                liToken, verifier);
        client = factory.createLinkedInApiClient(accessToken);
        com.google.code.linkedinapi.schema.Person profile = client.getProfileForCurrentUser(EnumSet.of(
                ProfileField.ID, ProfileField.FIRST_NAME,
                ProfileField.LAST_NAME, ProfileField.HEADLINE));
        String Name="FISRT Name is "+ profile.getFirstName()+"\n LAST Name is "+profile.getLastName();
        System.out.println("First Name :: " + profile.getFirstName());
        System.out.println("Last Name :: " + profile.getLastName());
        System.out.println("Head Line :: " + profile.getHeadline());
        System.out.println("getPublicProfileUrl :: " + profile.getId());
        Toast.makeText(getApplicationContext(), "Login Successful \n"+Name, Toast.LENGTH_SHORT).show();
}
    /*    TWITTER SIGNIN  */
    public void twitterintigration(int i){
        fb_gp=i;
        mTwitterAuthClient = new TwitterAuthClient();
        mTwitterAuthClient.authorize(SocialMediaIntegration.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // TwitterSession session = twitterSessionResult.data;
                session = result.data;
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                long userid= session.getUserId();
                getTwitterInfo();

            }

            @Override
            public void failure(TwitterException e) {

            }
        });

    }
    /*    GETTING TWITTERINFO  */
public void getTwitterInfo(){
    if(mTwitterAuthClient==null)
    {
        UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Please Login First");
    }
    else {
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {

                    @Override
                    public void success(Result<User> userResult) {

                        User user = userResult.data;
                        //Here we get image url which can be used to set as image wherever required.
                        System.out.println(user.profileImageUrl + " " + user.email + "" + user.followersCount);
                         String Info = "Login Successful \n" + "Twitter Profile Info \n" + "User Name Is    " +
                                session.getUserName() + "\n" + "User Id Is    "
                                + session.getUserId() + "      " ;
                                ;
                        facebookIntegrate.TwitterInfo(Info);

                    }

                    @Override
                    public void failure(TwitterException e) {

                    }

                });
    }
}

}