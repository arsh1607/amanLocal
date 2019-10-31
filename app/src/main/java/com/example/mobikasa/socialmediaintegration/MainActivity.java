package com.example.mobikasa.socialmediaintegration;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.gson.Gson;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import io.fabric.sdk.android.Fabric;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;

import global_constants.Constants;
import global_constants.GlobalConstants;
import global_constants.UtilitySingleton;
import model.ModalFbFriendList;
import model.ModalFbUserProfile;
import socialmediawrapper.SocialMediaIntegration;
import socialmediawrapper.SocialMediaInterface;
public class MainActivity extends SocialMediaIntegration implements SocialMediaInterface
{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    Button btn_facebook_login,btn_getfriend_list,btnlogout,btngoogle_plus,btnLinkedIn,mButtontwitter,mButtonTwitterInfo;
    private LinkedInOAuthService oAuthService;
    private LinkedInApiClientFactory factory;
    private LinkedInRequestToken liToken;
    private LinkedInApiClient client;
    TwitterSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        initialize();



        /*    TWITTER SIGNIN  */
        mButtontwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                twitterintigration(3);
            }
        });
            /*    GOOGLEPLUS SIGNIN  */
        btngoogle_plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               signInWithGplus(2);
            }
        });
         /*    FACEBOOK SIGNIN  */
        btn_facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookButtonClicked(1);
            }
        });
        /*    GETTING TWITTERINFO  */
        mButtonTwitterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTwitterInfo();
                getfriend();
            }
        });

        /*    GETTING FACEBOOK FRIENDLIST  */
        btn_getfriend_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getfriendlist();

            }
        });

         /*    GETTING FACEBOOK LOGOUT  */
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb_logout();
            }
        });

         /*    LINKEDIN SIGNUP  */
        btnLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Initalizelinkedin();


            }
        });

    }

    private void initialize() {
 /*    WIDGETS INITIALIZATION  */
        btn_facebook_login=(Button)findViewById(R.id.btnMainUserInfo);
        btn_getfriend_list=(Button)findViewById(R.id.btnMainFriendList);
        btnlogout=(Button)findViewById(R.id.btnfblogout);
        btnLinkedIn=(Button)findViewById(R.id.btn_linkedin);
        btngoogle_plus=(Button)findViewById(R.id.btngoogle_plus);
        mButtontwitter=(Button)findViewById(R.id.btn_twitter);
        mButtonTwitterInfo=(Button)findViewById(R.id.btn_twitterinfo);


    }


    public void getfriend(){
        final long tweetId = 952933941702545408L;
      //  final long tweetId = session.getUserId();
        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
               // Log.v("ffwf","ewre"+result.data.entities.urls.get(0). userMentions.size());
                //myLayout.addView(new TweetView(MainActivity.this, result.data));
            }

            @Override
            public void failure(TwitterException exception) {
                // Toast.makeText(...).show();
            }
        });

    }

    @Override
    public void login(ModalFbUserProfile modelfbuser)
    {
        if (modelfbuser != null)
        {
            String userName = modelfbuser.getName();
            String fbUserID = modelfbuser.getId();
            String email = "";
            try
            {
                email = modelfbuser.getEmail();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            URL image_value = null;
            try
            {
                image_value = new URL(GlobalConstants.ApiConstants.FB_PIC_START_URL + fbUserID + GlobalConstants.ApiConstants.FB_PIC_END_URL);
            }
            catch (MalformedURLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Username" + userName + "email" + email + "userID" + fbUserID + "imageValue" + image_value);
        }
    }
    public void getfriend_list(JSONObject jsonobject)
    {
        ModalFbFriendList user = new Gson().fromJson(jsonobject.toString(), ModalFbFriendList.class);
        UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Friend Name" + user.getData().get(0).getName() + "\n" + "Friend Id=" + user.getData().get(0).getId());
    }
    @Override
    public void getProfileInformation_of_google_plus(Person person,String email)
    {
        final String personName = person.getDisplayName();
        String personPhotoUrl = person.getImage().getUrl();
        String _email = email;
        String social_id = person.getId();
        personPhotoUrl = personPhotoUrl.substring(0, personPhotoUrl.length() - 2) + 200;
        UtilitySingleton.getInstance(getApplicationContext()).ShowToast("Name" + personName + "\n" + "Image="+personPhotoUrl+"Email="+_email);
        getfriendlist_googlePlus();
    }

    @Override
    public void TwitterInfo(String twitterinfo) {
        Toast.makeText(getApplicationContext(), twitterinfo, Toast.LENGTH_SHORT).show();
    }


    /*private void loadTwitterAPI(long userID) {
        new MyTwitterApiClient(session).getCustomService().show(userID)
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        ((TextView) findViewById(R.id.display)).setText(
                                "Name: " + result.data.name
                                        + "\nLocation: " + result.data.location
                                        + "\nFriends: " + result.data.friendsCount
                        );
//                        Picasso.with(getBaseContext()).load(result.data.profileImageUrl).
//                                resize(250,250)
//                                .into((ImageView)findViewById(R.id.imageView));
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.e("Failed", exception.toString());
                    }
                });
    }*/



}
