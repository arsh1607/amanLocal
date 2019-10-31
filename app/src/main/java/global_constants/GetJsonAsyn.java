package global_constants;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import  com.example.mobikasa.socialmediaintegration.MainActivity.*;
import com.example.mobikasa.socialmediaintegration.R;

public class GetJsonAsyn extends AsyncTask<String, Void, Integer> {
	// ProgressDialog authDialog;
	Dialog mDialog;
	//JSONObject json;
	String url;
	//GetJsonAsynListener<String> listener;
	boolean isDialogDisplay=false, isSuccess = false;
	Context context;
	int receivedId;
	int errorCode;
	ProgressDialog authDialog;
	String loaderString;
	JSONObject jobj;
	String Response="";
	private String xml;
    boolean Internet;
	InputStream in;
	HttpURLConnection urlConnection = null;
	int result;
	
	
	public GetJsonAsyn(Context context, String url, int receivedId,
					   String loaderString, boolean isDialogDisplay) {
		this.context = context;
		this.url = url;
		this.receivedId = receivedId;
		this.isDialogDisplay = isDialogDisplay;
		this.loaderString = loaderString;

	}



	



	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		try{
		// Internet= Utils.isNetworkConnected(context);

		if (isDialogDisplay) {
			viewProgressVisible("Please wait..");
		}else{
			
		}

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	
	
	@Override
	protected Integer doInBackground(String... params) {

		URL url = null;
		try {
			url = new URL("https://api.twitter.com/1.1/friends/list.json?cursor=-1&screen_name=Android_MobiK&skip_status=true&include_user_entities=false");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		try {
			urlConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

                 /* optional request header */
		urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
		urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
		try {
			urlConnection.setRequestMethod("https://api.twitter.com/1.1/friends/list.json?cursor=-1&screen_name=Android_MobiK&skip_status=true&include_user_entities=false");

		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}
		int statusCode = 0;
		try {
			statusCode = urlConnection.getResponseCode();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

                /* 200 represents HTTP OK */

		//if (statusCode ==  200) {
			try {
				in = new BufferedInputStream(urlConnection.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String response = convertStreamToString(in);
			Log.d("Response is ", "" + response);
		//	parseResult(response);
			result = 1; // Successful
		//}

//		else{
//			result = 0; //"Failed to fetch data!";
//		}

	return result; //"Failed to fetch


	}

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	private void readStream(InputStream in) {

	}

	@Override
	protected void onPostExecute(Integer _result) {
		// TODO Auto-generated method stub
		super.onPostExecute(_result);
        if(Internet) {
            try {
              //  viewProgressGone();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }else{

          //  listener.onGetError(receivedId, 01);
        }

	}

	/** Functions to display progress dialog box **/

	public void viewProgressGone() {
		try{
		if(mDialog!=null)
			mDialog.dismiss();

		}catch(Exception e ){
			e.printStackTrace();
		}

		try{
			if (authDialog != null)
				authDialog.dismiss();
		}catch(Exception e ){
			e.printStackTrace();
		}
		
	}

	public void viewProgressVisible(String paramString) {
	//	authDialog = ProgressDialog.show(context, "", paramString, true, false);
//		mDialog = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar);
//       mDialog.setContentView(R.layout.loader);
//        mDialog.setCancelable(false);
//        mDialog.show();
	}

	
	public static String stripCDATA(String s) {
	    s = s.trim();
	    if(s.contains("<![CDATA[")){
	    s = s.replace("<![CDATA[", "");
	    s = s.replace("]]>", "");
	    }
//	    if (s.c("<![CDATA[")) {
//	      s = s.substring(9);
//	      int i = s.indexOf("]]&gt;");
//	      if (i == -1) {
//	        throw new IllegalStateException(
//	            "argument starts with <![CDATA[ but cannot find pairing ]]&gt;");
//	      }
//	      s = s.substring(0, i);
//	    }
	    return s;
	  }
	public String ReadFromfile(String fileName, Context context) {
	    StringBuilder returnString = new StringBuilder();
	    InputStream fIn = null;
	    InputStreamReader isr = null;
	    BufferedReader input = null;
	    try {
	        fIn = context.getResources().getAssets()
	                .open(fileName, Context.MODE_WORLD_READABLE);
	        isr = new InputStreamReader(fIn);
	        input = new BufferedReader(isr);
	        String line = "";
	        while ((line = input.readLine()) != null) {
	            returnString.append(line);
	        }
	    } catch (Exception e) {
	        e.getMessage();
	    } finally {
	        try {
	            if (isr != null)
	                isr.close();
	            if (fIn != null)
	                fIn.close();
	            if (input != null)
	                input.close();
	        } catch (Exception e2) {
	            e2.getMessage();
	             Log.d(">>>>>>>>>>>>>>", "in the exception");
	        }
	    }
	    return returnString.toString();
	}
	
}
