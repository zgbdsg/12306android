package com.zgb.ticket;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.support.v4.app.Fragment;  
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginFragment extends Fragment{
	public static final String TAG = "LoginFragment";
	
	private ImageView verifiPic;
	private EditText verifiCode;
	private TextView loginMessage;
	private	EditText usernameText;
	private EditText passwordText;
	private Bitmap tmppic;
	public static int activeTimes = 0;
	AsyncHttpClient httpclient = TicketHttpClient.httpclient;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_login, container, false);  
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		Button btnlogin = (Button)getActivity().findViewById(R.id.loginbutton);
		btnlogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginon(v);
			}
		});
		
		verifiPic = (ImageView)getActivity().findViewById(R.id.verifipic);
		verifiPic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getVerifiPic(v);
			}
		});
		
		verifiCode = (EditText)getActivity().findViewById(R.id.verificode);
		loginMessage = (TextView)getActivity().findViewById(R.id.loginmessage);
		usernameText = (EditText)getActivity().findViewById(R.id.username);
		passwordText = (EditText)getActivity().findViewById(R.id.password);
		ProgressBar progressBar = (ProgressBar)getActivity().findViewById(R.id.progressBar);

		//progressBar.setVisibility(0);
		//verifiPic.setVisibility(8);
		//verifiPic.performClick();
		
		if(activeTimes == 0 || tmppic == null){
			activeTimes ++;
			progressBar.setVisibility(0);
			verifiPic.setVisibility(8);
			verifiPic.performClick();
		}else{
			verifiPic.setImageBitmap(tmppic);
			progressBar.setVisibility(8);
			verifiPic.setVisibility(0);
			verifiCode.setEnabled(true);
		}
		
		SharedPreferences sharedData = getActivity().getSharedPreferences("12306", 0);
		if(sharedData != null){
			usernameText.setText(sharedData.getString("usernameText",""));
			//passwordText.requestFocus();
		}
		
		usernameText.setSelectAllOnFocus(true);
		passwordText.setSelectAllOnFocus(true);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	/**check params :username password  verificode**/
	public boolean checkLogin() {
		
		if(verifiCode.getText().length() != 4)
		{
			loginMessage.setText(R.string.vericodeerror);
			loginMessage.setVisibility(0);
			return false;
		}else if (usernameText.getText().length() < 4 || passwordText.getText().length() < 4) {
			loginMessage.setText(R.string.userpasserror);
			loginMessage.setVisibility(0);
			return false;
		}
		return true;
	}
	
	/**check verify code**/
	
	public void checkVerifyCode(){
		String checkurl = "/otn/passcodeNew/checkRandCodeAnsyn";
		RequestParams params = new RequestParams();
		params.put("randCode", verifiCode.getText().toString());
		params.put("rand", "sjrand");
		Log.i("checkverificode :", params.toString());
		TicketHttpClient.post(checkurl,params,new AsyncHttpResponseHandler(){
			
			
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("check", response.toString());
				//String result = "";
				//JSONObject result = JSON.parseObject(response);
			}

			
		});
	}
	/** Called when the user clicks the login button */
	public void loginon(View view) {
	    // Do something in response to button
		//Log.i("login", "begin login !");
		if(checkLogin() == false)
			return;
		//checkVerifyCode();

		//String message = usernameText.getText().toString()+" : "+passwordText.getText().toString();
		
		String loginurl = "/otn/login/loginAysnSuggest";
		RequestParams params = new RequestParams();
		params.put("loginUserDTO.user_name", usernameText.getText().toString());
		params.put("userDTO.password", passwordText.getText().toString());
		params.put("randCode", verifiCode.getText().toString());
		
		TicketHttpClient.post(loginurl,params,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String response) {
                // Pull out the first event on the public timeline
              
				//String jsonText = "";
				Log.i("login :", response);

				JSONObject result = JSON.parseObject(response);
				JSONObject logininfo = result.getJSONObject("data");

				if(logininfo == null || logininfo.getString("loginCheck")== null || !logininfo.getString("loginCheck").equals("Y")){
					JSONArray messages = result.getJSONArray("messages");
					loginMessage.setText(messages.get(0).toString());
					loginMessage.setVisibility(0);
					verifiPic.performClick();
					return;
				}
				Log.i("login :", logininfo.getString("loginCheck"));
				
				Editor shareDataEditor = getActivity().getSharedPreferences("12306", 0).edit();
				shareDataEditor.putString("usernameText", usernameText.getText().toString());
				shareDataEditor.commit();
				
				Intent intent = new Intent(getActivity().getApplicationContext(),InquiryActivity.class);
				intent.putExtra("result", loginMessage.getText().toString());
				startActivity(intent);
            }

			
			@Override
			public void onFailure(int arg0, Header[] headers, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, headers, arg2, arg3);
				//for(Header head : headers)
				//	Log.e("login fail:","header :"+head.getName()+" -- "+head.getValue());
			}

			
		});
		//Log.i("login", loginMessage.getText().toString());
		
	}

	/** Called when the user clicks the Send button */
	public void getVerifiPic(View view) {
		System.out.println("get verifipic!");
		String url = "/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand";
		//String url = "https://www.google.com/images/srpr/logo11w.png";
		String[] allowedContentTypes = new String[] { "image/png", "image/jpeg;charset=UTF-8" };
		tmppic = null;
		TicketHttpClient.get(url, null, new BinaryHttpResponseHandler(allowedContentTypes)
		{
			
			@Override
		    public void onSuccess(int statusCode,byte[] fileData) {
		        // Do something with the file
				//Log.i("download:", "download image, file length " + fileData.length);
				BitmapFactory.Options newOpts = new BitmapFactory.Options();
				newOpts.inJustDecodeBounds = true;
				tmppic = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
				tmppic = Bitmap.createScaledBitmap
						(tmppic ,verifiCode.getWidth(), verifiCode.getHeight(), true);
				
				ProgressBar progressBar = (ProgressBar)getActivity().findViewById(R.id.progressBar);
				verifiPic.setImageBitmap(tmppic);
				
				//Log.i("image size:", verifiPic.getWidth()+"   "+verifiPic.getHeight());
				progressBar.setVisibility(8);
				verifiPic.setVisibility(0);
				verifiCode.setEnabled(true);
				progressBar.refreshDrawableState();
				verifiPic.refreshDrawableState();
		    }

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] binaryData, Throwable error) {
				// TODO Auto-generated method stub
				Log.e("download:", "download image, faile: " + error.getMessage());
				Log.e("download:",""+statusCode);
				//for(Header head : headers)
				//	Log.e("download:","header :"+head.getName()+" -- "+head.getValue());
			}
			
			
		});
		
	}
}
