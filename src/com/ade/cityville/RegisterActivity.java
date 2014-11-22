package com.ade.cityville;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements AsyncTaskCompleteListener<String>  {
	private String URL = "http://lamp.cse.fau.edu/~lwill137/cityvilleRegister.php";
	private EditText user;
	private EditText email;
	private EditText pass;
	private EditText pass2;
	private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		email = (EditText) findViewById(R.id.registerEmail);
		user = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.registerPass);
		pass2 = (EditText) findViewById(R.id.confirmPass);
		result = (TextView) findViewById(R.id.RegisterResult);
	}
	
	public void register(View view){
		if(user.getText().length()>0 && email.getText().length()>0 && pass.getText().length()>0 && pass2.getText().length()>0){
			if(pass.getText().toString().equals(pass2.getText().toString())){
				launchTask(URL);
			} else {
				result.setText("Passwords do not match!");
			}
		} else {
			result.setText("All Fields Required!");
		}
	
	}
	public void login(View view){
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	@Override
	public void onTaskComplete(String asyncResult) {
		result.setText(asyncResult);
		if(asyncResult.contains("Welcome")){
			loginSuccessful();
		}
	}
    public void launchTask(String url) {
        A a = new A(getApplicationContext(), this,user.getText().toString(), email.getText().toString(), pass.getText().toString());
        a.execute(url);
    }
	class A extends AsyncTask<String, Void, String> {
	    private AsyncTaskCompleteListener<String> callback;
	    String bodyHtml;
	    String Email;
	    String Pass;
	    String user;
	    public A(Context context, AsyncTaskCompleteListener<String> cb, String User, String email, String pass) {
	        this.callback = cb;
	        Email = email;
	        Pass = pass;
	        user = User;
	    }
		@Override
		protected String doInBackground(String... arg0) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(URL);
			HttpResponse response;
			try {
				// Add your data
			    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			    nameValuePairs.add(new BasicNameValuePair("username", user));
			    nameValuePairs.add(new BasicNameValuePair("email", Email));
			    nameValuePairs.add(new BasicNameValuePair("password", Pass));
			    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				response = httpClient.execute(post);
				bodyHtml = EntityUtils.toString(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bodyHtml;
		}  
	    protected void onPostExecute(String result) {
	       System.out.println("on Post execute called");
	       callback.onTaskComplete(result);
	   }
	}
	private void loginSuccessful(){
		Toast.makeText(getApplicationContext(), "Your Registered and Logged In!", Toast.LENGTH_SHORT).show();

		AppData.loggedIn = true;
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
}
